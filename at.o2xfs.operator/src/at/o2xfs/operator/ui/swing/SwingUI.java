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

package at.o2xfs.operator.ui.swing;

import java.io.File;
import java.io.IOException;
import java.util.List;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.operator.IController;
import at.o2xfs.operator.task.TaskListener;
import at.o2xfs.operator.task.TaskManager;
import at.o2xfs.operator.ui.UserInterface;
import at.o2xfs.operator.ui.input.InputDevice;

public class SwingUI implements UserInterface, TaskListener, SwingUIConfigKey {

	private static final Logger LOG = LoggerFactory.getLogger(SwingUI.class);

	private SwingUIConfig config = null;

	private UIFrame frame = null;

	private TaskManager taskManager = null;

	public SwingUI(final IController controller, final File configFile) {
		taskManager = controller.getTaskManager();
		taskManager.addTaskListener(this);
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
	public void taskChange() {
		frame.clearContents();
	}

	@Override
	public void commandsChanged() {
		frame.setCommands(taskManager.getTaskCommands());
		frame.setBackCommand(taskManager.getBackCommand());
		frame.setNextCommand(taskManager.getNextCommand());
	}

	@Override
	public void contentsChanged() {
		frame.setContents(taskManager.getContents());
	}

	@Override
	public void close() {
		frame.closeInputDevices();
		frame.dispose();
	}

}
