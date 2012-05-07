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

package at.o2xfs.operator.task.xfs.pin;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.operator.O2XfsOperator;
import at.o2xfs.operator.task.ExecuteTaskCommand;
import at.o2xfs.operator.task.Task;
import at.o2xfs.operator.task.TaskConfig;
import at.o2xfs.operator.task.xfs.CancelCommand;
import at.o2xfs.operator.ui.content.text.TextInput;
import at.o2xfs.operator.ui.input.VirtualKey;
import at.o2xfs.operator.ui.input.XfsInputDevice;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.pin.PINFDK;
import at.o2xfs.xfs.pin.WFSPINFDK;
import at.o2xfs.xfs.pin.WFSPINFUNCKEYDETAIL;
import at.o2xfs.xfs.pin.WFSPINGETDATA;
import at.o2xfs.xfs.pin.WFSPINKEY;
import at.o2xfs.xfs.service.cmd.pin.PINDataListener;
import at.o2xfs.xfs.service.cmd.pin.PINFunctionKeysCommand;
import at.o2xfs.xfs.service.cmd.pin.PINGetDataCommand;
import at.o2xfs.xfs.service.pin.PINService;

public class PINGetDataTask extends Task implements PINTaskConfigKey,
		PINDataListener {

	private final static Logger LOG = LoggerFactory
			.getLogger(PINGetDataTask.class);

	private PINService pinService = null;

	private PINGetDataCommand getDataCommand = null;

	private List<VirtualKey> pressedKeys = null;

	private TextInput textInput = null;

	private Timer cancelTimer = null;

	public PINGetDataTask(final PINService pinService) {
		this.pinService = pinService;
	}

	private void stopUIXfsInputDevice() {
		final O2XfsOperator operator = O2XfsOperator.getInstance();
		final XfsInputDevice inputDevice = operator
				.getXfsInputDevice(pinService);
		if (inputDevice != null) {
			if (LOG.isInfoEnabled()) {
				final String method = "stopUIXfsInputDevice()";
				LOG.info(method, "Stopping XfsInputDevice: " + inputDevice);
			}
			inputDevice.stop();
		}
	}

	private void startUIXfsInputDevice() {
		final O2XfsOperator operator = O2XfsOperator.getInstance();
		final XfsInputDevice inputDevice = operator
				.getXfsInputDevice(pinService);
		if (inputDevice != null) {
			if (LOG.isInfoEnabled()) {
				final String method = "startUIXfsInputDevice()";
				LOG.info(method, "Starting XfsInputDevice: " + inputDevice);
			}
			inputDevice.start();
		}
	}

	@Override
	public void execute() {
		final String method = "execute()";
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug(method, "Executing...");
			}
			stopUIXfsInputDevice();
			pressedKeys = new ArrayList<VirtualKey>();
			textInput = new TextInput();
			final WFSPINFUNCKEYDETAIL funcKeyDetail = new PINFunctionKeysCommand(
					pinService).execute();
			if (LOG.isDebugEnabled()) {
				LOG.debug(method, "funcKeyDetail=" + funcKeyDetail);
			}
			getDataCommand = new PINGetDataCommand(pinService);
			getDataCommand.addCommandListener(this);
			final WFSPINGETDATA pinGetData = getDataCommand.getPinGetData();
			pinGetData.setActiveKeys(funcKeyDetail.getFunctionKeys());
			final Set<PINFDK> activeFDKs = EnumSet.noneOf(PINFDK.class);
			for (final WFSPINFDK pinFDK : funcKeyDetail.getFDKs()) {
				activeFDKs.add(pinFDK.getFDK());
			}
			pinGetData.setActiveFDKs(activeFDKs);
			pinGetData.setMaxLen(0);
			pinGetData.setAutoEnd(false);
			getDataCommand.execute();
			final long timeOut = TaskConfig.getConfig(this).getLong(
					KEY_INPUT_TIMEOUT);
			startCancelTimer(timeOut);
			taskManager.setContent(textInput);
			taskManager.setNextCommand(new CancelCommand(getClass(),
					getDataCommand));
		} catch (final XfsException e) {
			showError(e);
			startFailed();
		}
	}

	private void startCancelTimer(final long timeOut) {
		final String method = "startCancelTimer(long)";
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "timeOut=" + timeOut);
		}
		final TimerTask cancelTask = new TimerTask() {

			@Override
			public void run() {
				if (LOG.isInfoEnabled()) {
					LOG.info(method, "Canceling command ...");
				}
				getDataCommand.cancel();
			}
		};
		cancelTimer = new Timer("TimeOut");
		cancelTimer.schedule(cancelTask, timeOut);
	}

	private void startFailed() {
		if (hasParent()) {
			taskManager.setNextCommand(new ExecuteTaskCommand(getParent(),
					taskManager));
		}
		startUIXfsInputDevice();
	}

	private void finish() {
		if (cancelTimer != null) {
			cancelTimer.cancel();
		}
		taskManager.clearCommands();
		taskManager.clearContents();
		if (hasParent()) {
			taskManager.setNextCommand(new ExecuteTaskCommand(getParent(),
					taskManager));
		}
		startUIXfsInputDevice();
	}

	@Override
	public void keyPressed(final WFSPINKEY pinKey) {
		final String method = "keyPressed(WFSPINKEY)";
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "pinKey=" + pinKey);
		}
		final VirtualKey virtualKey = PINKeyUtil.getVirtualKey(pinKey,
				pinService.getXfsVersion());
		if (virtualKey == null) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Unknown WFSPINKEY: " + pinKey);
			}
		} else {
			pressedKeys.add(virtualKey);
			final StringBuilder text = new StringBuilder();
			final int iMax = pressedKeys.size() - 1;
			for (int i = 0;; i++) {
				text.append(pressedKeys.get(i));
				if (i == iMax) {
					break;
				}
				text.append(',');
			}
			textInput.setText(text.toString());
		}
	}

	@Override
	public void commandSuccessful() {
		finish();
	}

	@Override
	public void commandCancelled() {
		finish();
	}

	@Override
	public void commandFailed(final XfsException e) {
		final String method = "commandFailed(XfsException)";
		if (LOG.isErrorEnabled()) {
			LOG.error(method, "PINGetDataCommand failed", e);
		}
		finish();
		showError(e);
	}

}
