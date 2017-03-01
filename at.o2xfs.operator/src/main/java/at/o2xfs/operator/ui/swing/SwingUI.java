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

package at.o2xfs.operator.ui.swing;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.SwingUtilities;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.operator.IController;
import at.o2xfs.operator.task.TaskManager;
import at.o2xfs.operator.task.TaskManagerListener;
import at.o2xfs.operator.ui.UserInterface;
import at.o2xfs.operator.ui.input.InputDevice;

public class SwingUI implements UserInterface, SwingUIConfigKey, TaskManagerListener {

	private static final Logger LOG = LoggerFactory.getLogger(SwingUI.class);

	private SwingUIConfig config = null;

	private UIFrame frame = null;

	private final TaskManager taskManager;

	public SwingUI(final IController controller, final File configFile) {
		taskManager = controller.getTaskManager();
		taskManager.addTaskManagerListener(this);
		try {
			config = new SwingUIConfig(configFile);
		} catch (final IOException e) {
			e.printStackTrace();
		}
		frame = new UIFrame(config);
	}

	@Override
	public List<InputDevice> getInputDevices() {
		return frame.getInputDevices();
	}

	@Override
	public void taskChanged() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				frame.clearContents();
			}
		});
		commandsChanged();
	}

	@Override
	public void commandsChanged() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				frame.setCommands(taskManager.getTaskCommands());
				frame.setBackCommand(taskManager.getBackCommand());
				frame.setNextCommand(taskManager.getNextCommand());
			}
		});
	}

	@Override
	public void contentChanged() {
		if (SwingUtilities.isEventDispatchThread()) {
			frame.setContent(taskManager.getContent());
		} else {
			try {
				SwingUtilities.invokeAndWait(new Runnable() {

					@Override
					public void run() {
						frame.setContent(taskManager.getContent());
					}
				});
			} catch (InvocationTargetException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void close() {
		frame.closeInputDevices();
		frame.dispose();
	}
}