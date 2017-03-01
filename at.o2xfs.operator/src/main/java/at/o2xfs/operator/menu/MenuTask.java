/*
 * Copyright (c) 2017, Andreas Fagschlunger. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package at.o2xfs.operator.menu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.operator.task.Task;
import at.o2xfs.operator.task.TaskCommand;
import at.o2xfs.operator.task.TaskManager;
import at.o2xfs.operator.ui.content.text.Label;
import at.o2xfs.operator.ui.content.text.Text;

public class MenuTask extends Task {

	private static final Logger LOG = LoggerFactory.getLogger(MenuTask.class);

	private List<TaskCommand> commands;

	private final List<MenuCommand> selectedPath;

	public MenuTask() {
		this.selectedPath = new ArrayList<MenuCommand>();
	}

	public TaskManager getTaskManager() {
		return taskManager;
	}

	@Override
	protected boolean setCloseCommandPerDefault() {
		return false;
	}

	@Override
	protected void doExecute() {
		if (commands == null) {
			loadCommands();
		}
		updateMenu();
	}

	private void loadCommands() {
		try {
			commands = new XMLMenuFactory().loadMenu(this, new FileInputStream(
					new File("config/menu.xml")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void updateMenu() {
		getCommands().clear();
		if (selectedPath.isEmpty()) {
			for (TaskCommand command : commands) {
				getCommands().addCommand(command);
			}
		} else {
			MenuCommand menuItem = selectedPath.get(selectedPath.size() - 1);
			getCommands().setBackCommand(new MenuBackCommand(this));
			for (TaskCommand command : menuItem.getChildren()) {
				getCommands().addCommand(command);
			}
		}
		updateTitle();
	}

	private void updateTitle() {
		Text.Builder title = new Text.Builder(new Label(getClass()
				.getSimpleName()));
		for (MenuCommand menuItem : selectedPath) {
			title.append(new Label(">")).append(menuItem.getLabel());
		}
		getContent().setTitle(title.build());
	}

	void select(MenuCommand menuItem) {
		if (LOG.isDebugEnabled()) {
			final String method = "select(MenuCommand)";
			LOG.debug(method, "menuItem=" + menuItem);
		}
		selectedPath.add(menuItem);
		updateMenu();
	}

	void back() {
		selectedPath.remove(selectedPath.size() - 1);
		updateMenu();
	}
}