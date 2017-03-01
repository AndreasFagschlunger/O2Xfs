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

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.operator.task.TaskCommand;
import at.o2xfs.operator.ui.content.text.Label;

public class MenuCommand extends TaskCommand {

	private final Label label;

	private final List<TaskCommand> children;

	private final MenuTask menuTask;

	public MenuCommand(MenuTask menuTask, String label, List<TaskCommand> children) {
		this.menuTask = menuTask;
		this.label = new Label(label);
		this.children = children;
	}

	public List<TaskCommand> getChildren() {
		return children;
	}

	@Override
	public Label getLabel() {
		return label;
	}

	@Override
	public void execute() {
		menuTask.select(this);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("label", label).append("children", children).toString();
	}
}