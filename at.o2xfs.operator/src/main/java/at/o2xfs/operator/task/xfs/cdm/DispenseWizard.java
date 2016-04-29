package at.o2xfs.operator.task.xfs.cdm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.operator.task.TaskCommand;
import at.o2xfs.operator.task.TaskCommands;
import at.o2xfs.operator.ui.UIContent;
import at.o2xfs.operator.ui.UIMessage;
import at.o2xfs.operator.ui.UIMessage.Severity;
import at.o2xfs.operator.ui.content.text.Label;

public class DispenseWizard {

	private static final Logger LOG = LoggerFactory.getLogger(DispenseWizard.class);

	private class CancelCommand extends TaskCommand {

		@Override
		public Label getLabel() {
			return Label.valueOf("Cancel");
		}

		@Override
		public void execute() {
			close(Status.CANCEL);
		}
	}

	private class BackCommand extends TaskCommand {

		@Override
		public Label getLabel() {
			return Label.valueOf("Back");
		}

		@Override
		public void execute() {
			pageIndex--;
			showPage();
		}
	}

	private class NextCommand extends TaskCommand {

		@Override
		public Label getLabel() {
			return Label.valueOf("Next");
		}

		@Override
		public void execute() {
			pageIndex++;
			showPage();
		}
	}

	private class FinishCommand extends TaskCommand {

		@Override
		public Label getLabel() {
			return Label.valueOf("Finish");
		}

		@Override
		public void execute() {
			close(Status.OK);
		}
	}

	public enum Status {
		OK, CANCEL;
	}

	private final TaskCommands taskCommands;
	private final UIContent uiContent;
	private final List<DispenseWizardPage> pages;
	private int pageIndex = 0;
	private Status status = null;

	private final int maxDispenseItems;

	public DispenseWizard(TaskCommands taskCommands, UIContent uiContent, int maxDispenseItems) {
		this.taskCommands = taskCommands;
		this.uiContent = uiContent;
		pages = new ArrayList<>();
		this.maxDispenseItems = maxDispenseItems;
	}

	public void addPage(DispenseWizardPage page) {
		pages.add(page);
		page.setWizard(this);
	}

	public List<DispenseWizardPage> getPages() {
		return Collections.unmodifiableList(pages);
	}

	public Status open() {
		synchronized (this) {
			status = null;
			showPage();
			while (status == null) {
				try {
					wait();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
			taskCommands.clear();
			uiContent.clear();
		}
		return status;
	}

	private void close(Status status) {
		synchronized (this) {
			this.status = status;
			notifyAll();
		}
	}

	private void showPage() {
		DispenseWizardPage page = pages.get(pageIndex);
		page.createControl(uiContent);
		updateButtons();
	}

	public boolean canFinish() {
		boolean result = true;
		long count = 0L;
		for (DispenseWizardPage page : pages) {
			if (!page.isPageComplete()) {
				result = false;
				break;
			}
			if (page.getValue() != null) {
				count += page.getValue().longValue();
			}
		}
		if (maxDispenseItems > 0 && count > maxDispenseItems) {
			uiContent.setMessage(null, new UIMessage(Severity.ERROR, Label.valueOf(CdmMessages.MAX_DISPENSE_ITEMS_ID)));
			result = false;
		} else {
			uiContent.clearMessage(null);
		}
		return result;
	}

	public void updateButtons() {
		boolean canFinish = canFinish();
		DispenseWizardPage page = pages.get(pageIndex);
		if (pageIndex == 0) {
			taskCommands.setBackCommand(new CancelCommand());
		} else {
			taskCommands.setBackCommand(new BackCommand());
		}
		if (pageIndex == pages.size() - 1) {
			TaskCommand taskCommand = new FinishCommand();
			taskCommand.setEnabled(canFinish);
			taskCommands.setNextCommand(taskCommand);
		} else {
			NextCommand nextCommand = new NextCommand();
			nextCommand.setEnabled(page.isPageComplete());
			taskCommands.setNextCommand(nextCommand);
		}
	}
}
