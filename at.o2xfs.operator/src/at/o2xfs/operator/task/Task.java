/*
 * Copyright (c) 2012, Andreas Fagschlunger. All rights reserved.
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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import at.o2xfs.operator.ui.content.text.ErrorMessage;
import at.o2xfs.operator.ui.content.text.Label;
import at.o2xfs.operator.ui.content.text.WarningLabel;

public abstract class Task {

	private Task parent = null;

	private List<Task> children = null;

	protected TaskManager taskManager = null;

	public Task() {
		children = new ArrayList<Task>();
	}

	protected void setTaskManager(TaskManager taskManager) {
		this.taskManager = taskManager;
	}

	abstract protected void execute();

	protected Task getParent() {
		return parent;
	}

	public void setParent(Task parent) {
		this.parent = parent;
	}

	public boolean hasParent() {
		return parent != null;
	}

	public void appendChild(final Task newChild) {
		newChild.parent = this;
		children.add(newChild);
	}

	protected boolean hasChildNodes() {
		return !children.isEmpty();
	}

	protected Task getChildAt(final int childIndex) {
		return children.get(childIndex);
	}

	protected List<Task> getChildren() {
		return children;
	}

	protected void showMessage(final String label) {
		taskManager.setContent(new Label(getClass()).append(label));
	}

	protected void showWarning(final String label) {
		taskManager.setContent(new WarningLabel(getClass(), label));
	}

	protected void showError(final Throwable cause) {
		taskManager.setContent(new ErrorMessage(getClass(), cause));
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("parent", parent)
				.append("children", children).toString();
	}
}
