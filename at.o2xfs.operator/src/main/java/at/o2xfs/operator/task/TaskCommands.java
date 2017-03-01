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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskCommands {

	private final List<TaskCommand> commands;

	private final List<CommandsChangedListener> changedListeners;

	private TaskCommand backCommand = null;

	private TaskCommand nextCommand = null;

	public TaskCommands() {
		commands = new ArrayList<TaskCommand>();
		changedListeners = new ArrayList<CommandsChangedListener>();
	}

	void addCommandsChangedListener(CommandsChangedListener listener) {
		changedListeners.add(listener);
	}

	void removeCommandsChangedListener(CommandsChangedListener listener) {
		changedListeners.remove(listener);
	}

	private void notifyCommandsChanged() {
		for (CommandsChangedListener listener : changedListeners) {
			listener.commandsChanged();
		}
	}

	public List<TaskCommand> getCommands() {
		return Collections.unmodifiableList(commands);
	}

	public TaskCommand getBackCommand() {
		return backCommand;
	}

	public void setBackCommand(TaskCommand backCommand) {
		this.backCommand = backCommand;
		notifyCommandsChanged();
	}

	public TaskCommand getNextCommand() {
		return nextCommand;
	}

	public void setNextCommand(TaskCommand nextCommand) {
		this.nextCommand = nextCommand;
		notifyCommandsChanged();
	}

	public void addCommand(TaskCommand command) {
		commands.add(command);
		notifyCommandsChanged();
	}

	public void clear() {
		commands.clear();
		backCommand = null;
		nextCommand = null;
		notifyCommandsChanged();
	}
}