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

package at.o2xfs.operator.task;

import at.o2xfs.operator.ui.UIContent;
import at.o2xfs.operator.ui.UIMessage;
import at.o2xfs.operator.ui.UIMessage.Severity;
import at.o2xfs.operator.ui.content.text.ExceptionMessage;
import at.o2xfs.operator.ui.content.text.Label;

public abstract class Task {

	private final TaskCommands taskCommands;

	private final UIContent content;

	protected TaskManager taskManager = null;

	public Task() {
		taskCommands = new TaskCommands();
		content = new UIContent(new Label(getClass().getSimpleName()));
	}

	protected TaskCommands getCommands() {
		return taskCommands;
	}

	protected UIContent getContent() {
		return content;
	}

	@Deprecated
	protected void showMessage(String messageId) {
		content.setUIElement(new UIMessage(Severity.INFO, Label.valueOf(messageId)));
	}

	@Deprecated
	protected void showWarning(String messageId) {
		content.setUIElement(new UIMessage(Severity.WARN, Label.valueOf(messageId)));
	}

	@Deprecated
	protected void showError(String messageId) {
		content.setUIElement(new UIMessage(Severity.ERROR, Label.valueOf(messageId)));
	}

	@Deprecated
	protected void showException(Exception cause) {
		content.setUIElement(new ExceptionMessage(getClass(), cause));
	}

	protected boolean setCloseCommandPerDefault() {
		return true;
	}

	protected void setCloseCommand() {
		taskCommands.setBackCommand(new CloseTaskCommand(taskManager));
	}

	final void execute(TaskManager taskManager) {
		this.taskManager = taskManager;
		if (setCloseCommandPerDefault()) {
			setCloseCommand();
		}
		doExecute();
	}

	abstract protected void doExecute();

	protected void close() {

	}
}