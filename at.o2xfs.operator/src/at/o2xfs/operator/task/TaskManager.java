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

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.operator.ui.content.text.ErrorMessage;

public class TaskManager {

	private final static Logger LOG = LoggerFactory
			.getLogger(TaskManager.class);

	private List<TaskCommand> taskCommands = null;

	private TaskCommand backCommand = null;

	private TaskCommand nextCommand = null;

	private List<TaskListener> taskListeners = null;

	private List<Object> contents = null;

	private Task task = null;

	public TaskManager() {
		taskCommands = new ArrayList<TaskCommand>();
		taskListeners = new ArrayList<TaskListener>();
		contents = new ArrayList<Object>();
	}

	private void notifyCommandsChanged() {
		for (final TaskListener taskListener : taskListeners) {
			taskListener.commandsChanged();
		}
	}

	private void notifyContentsChanged() {
		for (final TaskListener taskListener : taskListeners) {
			taskListener.contentsChanged();
		}
	}

	public void clearCommands() {
		clearCommands(true);
	}

	private void clearCommands(final boolean notify) {
		taskCommands.clear();
		backCommand = null;
		nextCommand = null;
		if (notify) {
			notifyCommandsChanged();
		}
	}

	public void clearContents() {
		contents.clear();
		notifyContentsChanged();
	}

	public void addTaskListener(final TaskListener taskListener) {
		taskListeners.add(taskListener);
	}

	public void removeTaskListener(final TaskListener taskListener) {
		taskListeners.remove(taskListener);
	}

	public void addTaskCommand(final TaskCommand taskCommand) {
		taskCommands.add(taskCommand);
		notifyCommandsChanged();
	}

	public void setBackCommand(final TaskCommand taskCommand) {
		backCommand = taskCommand;
		notifyCommandsChanged();
	}

	public void setNextCommand(final TaskCommand taskCommand) {
		nextCommand = taskCommand;
		notifyCommandsChanged();
	}

	public void setContent(final Object content) {
		contents.clear();
		contents.add(content);
		notifyContentsChanged();
	}

	public void execute(final Task newTask) {
		final String method = "execute(Task)";
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "task=" + task + ",newTask=" + newTask);
		}
		task = newTask;
		task.setTaskManager(this);
		clearCommands(false);
		contents.clear();
		notifyTaskChange();
		final Thread taskThread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					task.execute();
				} catch (final Exception e) {
					LOG.error(method, "Executing Task failed", e);
					handleTaskFailed(e);
				}
			}
		}, task.getClass().getSimpleName());
		if (LOG.isInfoEnabled()) {
			LOG.info(method, "Executing Task: " + newTask);
		}
		taskThread.start();
	}

	private void notifyTaskChange() {
		for (final TaskListener l : taskListeners) {
			l.taskChange();
		}
	}

	private void handleTaskFailed(final Exception cause) {
		setContent(new ErrorMessage(task.getClass(), cause));
		if (task.getParent() != null) {
			setNextCommand(new ExecuteTaskCommand(task.getParent(), this));
		}
	}

	public TaskCommand getBackCommand() {
		return backCommand;
	}

	public TaskCommand getNextCommand() {
		return nextCommand;
	}

	public List<TaskCommand> getTaskCommands() {
		return taskCommands;
	}

	public List<Object> getContents() {
		return contents;
	}

}
