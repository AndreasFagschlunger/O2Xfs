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

package at.o2xfs.operator;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.operator.config.O2XfsOperatorConfig;
import at.o2xfs.operator.menu.MenuTask;
import at.o2xfs.operator.task.TaskManager;
import at.o2xfs.operator.task.xfs.XfsStartUpTask;
import at.o2xfs.operator.ui.UIFactory;
import at.o2xfs.operator.ui.UserInterface;
import at.o2xfs.operator.ui.input.InputDevice;
import at.o2xfs.operator.ui.input.XfsInputDevice;
import at.o2xfs.operator.util.ListUtil;
import at.o2xfs.xfs.service.XfsService;
import at.o2xfs.xfs.service.XfsServiceManager;

public class O2XfsOperator implements IController {

	private final static Logger LOG = LoggerFactory.getLogger(O2XfsOperator.class);

	private static O2XfsOperator instance = null;

	private List<UserInterface> userInterfaces = null;

	private O2XfsOperatorConfig config = null;

	private final TaskManager taskManager;

	private O2XfsOperator() {
		userInterfaces = new ArrayList<UserInterface>();
		config = new O2XfsOperatorConfig();
		try {
			config.load(new FileReader(new File("config/operator.properties")));
		} catch (final Exception e) {
			e.printStackTrace();
		}
		taskManager = new TaskManager(new MenuTask());
		createUIs();
		taskManager.execute(new XfsStartUpTask());
	}

	private void createUIs() {
		final String method = "createUIs()";
		final List<String> uiNames = config.getUINames();
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "uiNames=" + ListUtil.toString(uiNames));
		}
		for (final String uiName : uiNames) {
			try {
				final Class<UIFactory> uiFactoryClass = (Class<UIFactory>) Class.forName(config.getUIFactory(uiName));
				final File uiConfig = config.getUIConfigFile(uiName);
				final UIFactory uiFactory = uiFactoryClass.newInstance();
				final UserInterface ui = uiFactory.newUserInterfaceInstance(this, uiConfig);
				userInterfaces.add(ui);
			} catch (final Exception e) {
				if (LOG.isErrorEnabled()) {
					LOG.error(method, "Error creating UserInterface", e);
				}
			}
		}
	}

	@Override
	public TaskManager getTaskManager() {
		return taskManager;
	}

	public static O2XfsOperator getInstance() {
		if (instance == null) {
			synchronized (O2XfsOperator.class) {
				if (instance == null) {
					instance = new O2XfsOperator();
				}
			}
		}
		return instance;
	}

	public XfsInputDevice getXfsInputDevice(final XfsService xfsService) {
		// FIXME
		final UserInterface ui = userInterfaces.get(0);
		for (final InputDevice inputDevice : ui.getInputDevices()) {
			if (inputDevice instanceof XfsInputDevice) {
				final XfsInputDevice xfsInputDevice = (XfsInputDevice) inputDevice;
				if (xfsInputDevice.getXfsService().equals(xfsService)) {
					return xfsInputDevice;
				}
			}
		}
		return null;
	}

	public void shutdown() {
		for (final UserInterface ui : userInterfaces) {
			ui.close();
		}
		XfsServiceManager.getInstance().shutdown();
	}

	public final static void main(String[] args) {
		O2XfsOperator.getInstance();
	}
}