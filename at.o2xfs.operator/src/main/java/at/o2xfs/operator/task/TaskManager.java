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
import java.util.List;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.operator.ui.UIContent;
import at.o2xfs.operator.ui.UIContentListener;
import at.o2xfs.operator.ui.content.text.ExceptionMessage;

public class TaskManager implements CommandsChangedListener, UIContentListener {

	private static final Logger LOG = LoggerFactory.getLogger(TaskManager.class);

	private final List<TaskManagerListener> listeners;

	private Task loginTask = null;

	private Task defaultTask = null;

	private final List<Task> taskPath;

	private Thread thread = null;

	public TaskManager(Task defaultTask) {
		this.defaultTask = defaultTask;
		this.listeners = new ArrayList<TaskManagerListener>();
		this.taskPath = new ArrayList<Task>();
	}

	public void addTaskManagerListener(TaskManagerListener listener) {
		listeners.add(listener);
	}

	public void removeTaskManagerListener(TaskManagerListener listener) {
		listeners.remove(listener);
	}

	private void notifyTaskChanged() {
		for (TaskManagerListener listener : listeners) {
			listener.taskChanged();
		}
	}

	@Override
	public void commandsChanged() {
		for (TaskManagerListener listener : listeners) {
			listener.commandsChanged();
		}
	}

	@Override
	public void contentChanged() {
		for (TaskManagerListener listener : listeners) {
			listener.contentChanged();
		}
	}

	public List<TaskCommand> getTaskCommands() {
		return getCurrentTask().getCommands().getCommands();
	}

	public TaskCommand getBackCommand() {
		return getCurrentTask().getCommands().getBackCommand();
	}

	public TaskCommand getNextCommand() {
		return getCurrentTask().getCommands().getNextCommand();
	}

	public UIContent getContent() {
		return getCurrentTask().getContent();
	}

	void executeDefaultTask() {
		execute(defaultTask);
	}

	private Task getCurrentTask() {
		return taskPath.get(taskPath.size() - 1);
	}

	public void execute(final Task task) {
		final String method = "execute(Task)";
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "task=" + task + ",taskPath=" + taskPath);
		}
		for (int i = taskPath.size() - 1; i >= 0; i--) {
			Task activeTask = taskPath.remove(i);
			activeTask.getCommands().removeCommandsChangedListener(this);
			activeTask.getContent().removeUIContentListener(this);
		}
		internalExecute(task);
	}

	public void executeSubTask(Task task) {
		internalExecute(task);
	}

	private void internalExecute(final Task task) {
		taskPath.add(task);
		thread = new Thread(new Runnable() {

			@Override
			public void run() {
				notifyTaskChanged();
				executeTask();
			}
		}, task.getClass().getSimpleName());
		thread.start();
	}

	private void executeTask() {
		final String method = "executeTask()";
		Task task = getCurrentTask();
		try {
			task.getCommands().addCommandsChangedListener(this);
			task.getContent().addUIContentListener(this);
			getCurrentTask().execute(this);
		} catch (Exception e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Exception in Task: " + task, e);
			}
			task.getContent().setUIElement(new ExceptionMessage(task.getClass(), e));
			task.getCommands().clear();
			task.getCommands().setBackCommand(new CloseTaskCommand(this));
		}
	}

	public void closeTask() {
		final String method = "closeTask()";
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "taskPath=" + taskPath);
		}
		Task activeTask = taskPath.remove(taskPath.size() - 1);
		activeTask.close();
		if (!Thread.currentThread().equals(thread)) {
			try {
				LOG.info(method, "Waiting for Task: " + activeTask);
				thread.join();
			} catch (InterruptedException e) {
				LOG.error(method, "Interrupted", e);
			}
		}
		activeTask.getCommands().removeCommandsChangedListener(this);
		activeTask.getContent().removeUIContentListener(this);
		if (taskPath.isEmpty()) {
			executeDefaultTask();
		} else {
			execute(taskPath.remove(taskPath.size() - 1));
		}
	}
}