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

package at.o2xfs.operator.ui.input.pin;

import java.util.EnumSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.operator.config.Config;
import at.o2xfs.operator.task.xfs.pin.PINKeyUtil;
import at.o2xfs.operator.ui.input.AbstractInputDevice;
import at.o2xfs.operator.ui.input.VirtualKey;
import at.o2xfs.operator.ui.input.XfsInputDevice;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.pin.PINFDK;
import at.o2xfs.xfs.pin.PINFK;
import at.o2xfs.xfs.pin.WFSPINFDK;
import at.o2xfs.xfs.pin.WFSPINFUNCKEYDETAIL;
import at.o2xfs.xfs.pin.WfsPINGetData;
import at.o2xfs.xfs.pin.WfsPINKey;
import at.o2xfs.xfs.service.XfsService;
import at.o2xfs.xfs.service.XfsServiceListener;
import at.o2xfs.xfs.service.XfsServiceManager;
import at.o2xfs.xfs.service.pin.PINService;
import at.o2xfs.xfs.service.pin.cmd.PINDataListener;
import at.o2xfs.xfs.service.pin.cmd.PINFunctionKeysCommand;
import at.o2xfs.xfs.service.pin.cmd.PINGetDataCommand;

public class PINInputDevice extends AbstractInputDevice implements
		XfsInputDevice, XfsServiceListener, PINDataListener {

	private final static Logger LOG = LoggerFactory
			.getLogger(PINInputDevice.class);

	private Config config = null;

	private XfsServiceManager serviceManager = null;

	private PINService pinService = null;

	private WFSPINFUNCKEYDETAIL funcKeyDetail = null;

	private Set<PINFDK> supportedFDKs = null;

	private PINGetDataCommand command = null;

	private Set<VirtualKey> supportedKeys = null;

	private boolean started = false;

	public PINInputDevice() {
		super();
		serviceManager = XfsServiceManager.getInstance();
		supportedFDKs = EnumSet.noneOf(PINFDK.class);
		supportedKeys = EnumSet.noneOf(VirtualKey.class);
	}

	@Override
	public void init(final Properties properties) {
		config = new Config(properties);
	}

	@Override
	public void xfsServiceStarted(final XfsService xfsService) {
		if (!(xfsService instanceof PINService)) {
			return;
		}
		synchronized (this) {
			if (started && pinService == null) {
				start();
			}
		}
	}

	@Override
	public void start() {
		final String method = "";
		synchronized (this) {
			if (command != null) {
				return;
			}
			if (!started) {
				started = true;
				serviceManager.addXfsServiceListener(this);
			}
			if (pinService == null) {
				pinService = determinePINService();
			}
			if (pinService != null) {
				try {
					if (supportedKeys.isEmpty()) {
						initSupportedKeys();
					}
					executePINGetDataCommand();
				} catch (XfsException e) {
					LOG.error(method, "Error starting PINInputDevice", e);
				}
			}
		}
	}

	private PINService determinePINService() {
		final String method = "determinePINService()";
		final String serviceName = config.getValue("ServiceName");
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "serviceName=" + serviceName);
		}
		final List<PINService> pinServices = serviceManager
				.getServices(PINService.class);
		if (serviceName == null) {
			if (!pinServices.isEmpty()) {
				return pinServices.get(0);
			}
		} else {
			for (final PINService pinService : pinServices) {
				if (serviceName.equals(pinService.getLogicalName())) {
					return pinService;
				}
			}
		}
		return null;
	}

	@Override
	public XfsService getXfsService() {
		return pinService;
	}

	@Override
	public Set<VirtualKey> getSupportedKeys() {
		return supportedKeys;
	}

	private void initSupportedKeys() throws XfsException {
		final String method = "initSupportedKeys()";
		funcKeyDetail = new PINFunctionKeysCommand(pinService).call();
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "funcKeyDetail=" + funcKeyDetail);
		}
		addFunctionKeys(funcKeyDetail.getFunctionKeys());
		for (final WFSPINFDK pinFDK : funcKeyDetail.getFDKs()) {
			addFDK(pinFDK.getFDK());
			supportedFDKs.add(pinFDK.getFDK());
		}
		notifySupportedKeysChange();
	}

	private void addFunctionKeys(final Set<PINFK> functionKeys) {
		final String method = "addFunctionKeys(Set<PINFK>)";
		for (final PINFK functionKey : functionKeys) {
			final VirtualKey virtualKey = PINKeyUtil.getVirtualKey(functionKey);
			if (LOG.isDebugEnabled()) {
				LOG.debug(method, "functionKey=" + functionKey + ",virtualKey="
						+ virtualKey);
			}
			if (virtualKey == null) {
				if (LOG.isWarnEnabled()) {
					LOG.warn(method, "Unknown PINFK: " + functionKey);
				}
			} else {
				supportedKeys.add(virtualKey);
			}
		}
	}

	private void addFDK(final PINFDK pinFDK) {
		final String method = "addFDK(PINFDK)";
		final VirtualKey virtualKey = PINKeyUtil.getVirtualKey(pinFDK);
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "pinFDK=" + pinFDK + ",virtualKey=" + virtualKey);
		}
		if (virtualKey == null) {
			if (LOG.isWarnEnabled()) {
				LOG.warn(method, "Unknown PINFDK: " + pinFDK);
			}
		} else {
			supportedKeys.add(virtualKey);
		}

	}

	private void executePINGetDataCommand() {
		final WfsPINGetData pinGetData = new WfsPINGetData();
		pinGetData.allocate();
		pinGetData.setAutoEnd(false);
		pinGetData.setActiveKeys(funcKeyDetail.getFunctionKeys());
		pinGetData.setActiveFDKs(supportedFDKs);
		command = new PINGetDataCommand(pinService, pinGetData);
		command.addCommandListener(this);
		command.execute();
	}

	@Override
	public void stop() {
		final String method = "stop()";
		synchronized (this) {
			if (command != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug(method, "Stopping...");
				}
				command.cancel();
				try {
					while (command != null) {
						if (LOG.isDebugEnabled()) {
							LOG.debug(method, "Waiting ...");
						}
						wait();
					}
					started = false;
					serviceManager.removeXfsServiceListener(this);
				} catch (final InterruptedException e) {
					LOG.error(method, "Interrupted while waiting", e);
				}
			}
		}
	}

	@Override
	public void keyPressed(final WfsPINKey pinKey) {
		final String method = "keyPressed(WFSPINKEY)";
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "pinKey=" + pinKey);
		}
		final VirtualKey virtualKey = PINKeyUtil.getVirtualKey(pinKey,
				pinService.getXfsVersion());
		if (virtualKey == null) {
			if (LOG.isWarnEnabled()) {
				LOG.warn(method, "Unknown WFSPINKEY: " + pinKey);
			}
		} else {
			notifyKeyPressed(virtualKey);
		}
	}

	@Override
	public void commandSuccessful() {
		final String method = "commandSuccessful()";
		if (LOG.isInfoEnabled()) {
			LOG.info(method, "");
		}
		reset();
	}

	@Override
	public void commandCancelled() {
		final String method = "commandCancelled()";
		if (LOG.isInfoEnabled()) {
			LOG.info(method, "");
		}
		reset();
	}

	@Override
	public void commandFailed(final Exception e) {
		if (LOG.isErrorEnabled()) {
			final String method = "commandFailed(Exception)";
			LOG.error(method, "Error executing PINGetDataCommand", e);
		}
		reset();
	}

	private void reset() {
		synchronized (this) {
			command = null;
			notify();
		}
	}
}