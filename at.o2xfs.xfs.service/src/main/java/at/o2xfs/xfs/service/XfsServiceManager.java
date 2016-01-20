/*
 * Copyright (c) 2014, Andreas Fagschlunger. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 * - Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 
 * - Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package at.o2xfs.xfs.service;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.win32.DWORD;
import at.o2xfs.win32.HWND;
import at.o2xfs.win32.ZSTR;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.WFSStartUpException;
import at.o2xfs.xfs.WFSVersion;
import at.o2xfs.xfs.XFSVersionDWORD;
import at.o2xfs.xfs.XfsAPI;
import at.o2xfs.xfs.XfsConstant;
import at.o2xfs.xfs.XfsEventClass;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.XfsMessage;
import at.o2xfs.xfs.XfsVersion;
import at.o2xfs.xfs.service.cmd.XfsCloseCommand;
import at.o2xfs.xfs.service.cmd.XfsCommand;
import at.o2xfs.xfs.service.cmd.XfsDeRegisterCommand;
import at.o2xfs.xfs.service.cmd.XfsExecuteCommand;
import at.o2xfs.xfs.service.cmd.XfsInfoCommand;
import at.o2xfs.xfs.service.cmd.XfsOpenCommand;
import at.o2xfs.xfs.service.cmd.XfsRegisterCommand;
import at.o2xfs.xfs.service.config.XfsServiceConfig;
import at.o2xfs.xfs.service.events.XfsEventNotification;
import at.o2xfs.xfs.type.HAPP;
import at.o2xfs.xfs.type.HSERVICE;
import at.o2xfs.xfs.type.RequestId;
import at.o2xfs.xfs.util.Bitmask;
import at.o2xfs.xfs.util.IXfsCallback;
import at.o2xfs.xfs.util.MessageHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Andreas Fagschlunger
 *
 */
public class XfsServiceManager
		implements IXfsCallback {

	private final static Logger LOG = LoggerFactory.getLogger(XfsServiceManager.class);

	private static XfsServiceManager instance = null;

	private final XfsAPI xfsAPI;

	private final List<XfsService> xfsServices;

	private final List<WFSResult> wfsResults;

	private final List<XfsServiceListener> serviceListeners;

	private final RequestQueue requestQueue;

	private final XfsServiceConfig config = XfsServiceConfig.getInstance();

	private WFSVersion version = null;

	private HAPP hApp = null;

	private HWND hWnd = null;

	private MessageHandler messageHandler = null;

	private EventDispatcher eventDispatcher = null;

	private XfsServiceManager() {
		xfsAPI = XfsAPI.getInstance();
		wfsResults = new ArrayList<WFSResult>();
		xfsServices = new ArrayList<XfsService>();
		requestQueue = new RequestQueue();
		serviceListeners = new CopyOnWriteArrayList<XfsServiceListener>();
		eventDispatcher = new EventDispatcher();
		messageHandler = new MessageHandler(this);
		messageHandler.start();
		hWnd = messageHandler.getHWnd();
	}

	public static XfsServiceManager getInstance() {
		if (instance == null) {
			synchronized (XfsServiceManager.class) {
				if (instance == null) {
					instance = new XfsServiceManager();
				}
			}
		}
		return instance;
	}

	public void addXfsServiceListener(final XfsServiceListener e) {
		serviceListeners.add(e);
	}

	public void removeXfsServiceListener(final XfsServiceListener e) {
		serviceListeners.remove(e);
	}

	public void initialize() throws XfsServiceManagerException {
		final String method = "initialize()";
		try {
			startUpXfs();
		} catch (final WFSStartUpException e) {
			throw new XfsServiceManagerException(e);
		}
		try {
			hApp = xfsAPI.wfsCreateAppHandle();
		} catch (final XfsException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Error creating application handle", e);
			}
			throw new XfsServiceManagerException(e);
		}
		new OpenServiceHandler().openServices();
	}

	public <E extends XfsService> E openAndRegister(final String logicalName, final Class<E> serviceClass) throws XfsException {
		if (hApp == null) {
			throw new IllegalStateException("XfsServiceManager not initalized");
		}
		final E xfsService = XfsServiceFactory.create(logicalName, serviceClass);
		new XfsServiceStartUp(xfsService).startUp();
		xfsServices.add(xfsService);
		notifyXfsServiceStarted(xfsService);
		return xfsService;
	}

	public <E extends XfsService> E getService(final String logicalName, final Class<E> serviceClass) {
		synchronized (xfsServices) {
			for (final XfsService xfsService : xfsServices) {
				if (xfsService.getLogicalName().equals(logicalName)) {
					return serviceClass.cast(xfsService);
				}
			}
		}
		return null;
	}

	private void notifyXfsServiceStarted(final XfsService xfsService) {
		final String method = "notifyXfsServiceStarted(XfsService)";
		if (LOG.isInfoEnabled()) {
			LOG.info(method, "XfsService: " + xfsService);
		}
		synchronized (serviceListeners) {
			for (final XfsServiceListener e : serviceListeners) {
				e.xfsServiceStarted(xfsService);
			}
		}
	}

	public <E extends XfsService> List<E> getServices(final Class<E> serviceType) {
		final List<E> services = new ArrayList<E>();
		for (final XfsService service : xfsServices) {
			if (serviceType.isInstance(service)) {
				services.add(serviceType.cast(service));
			}
		}
		return services;
	}

	public <E extends XfsService> E getService(final Class<E> serviceClass) throws ServiceNotFoundException {
		for (final XfsService xfsService : xfsServices) {
			if (serviceClass.isInstance(xfsService)) {
				return serviceClass.cast(xfsService);
			}
		}
		throw new ServiceNotFoundException(serviceClass);
	}

	public List<XfsService> getServices() {
		return new ArrayList<XfsService>(xfsServices);
	}

	/**
	 * @see IXfsCallback
	 */
	@Override
	public void callback(final XfsMessage msg, final WFSResult wfsResult) {
		final String method = "callback(XFSMessage, WFSResult)";
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "msg=" + msg + ",wfsResult=" + wfsResult);
		}
		synchronized (wfsResults) {
			wfsResults.add(wfsResult);
		}
		if (msg.isOperationComplete() || msg.isIntermediateEvent()) {
			XfsEventNotification eventNotification = requestQueue.getEventNotification(wfsResult.getRequestID());
			if (msg.isOperationComplete()) {
				requestQueue.removeRequest(wfsResult.getRequestID());
			}
			eventDispatcher.dispatch(msg, eventNotification, wfsResult);
		} else {
			final XfsService xfsService = getXfsService(wfsResult.getService());
			if (xfsService == null) {
				if (LOG.isErrorEnabled()) {
					LOG.error(method, "Could not find XfsService for WFSResult: " + wfsResult);
				}
				free(wfsResult);
			} else {
				eventDispatcher.dispatch(msg, xfsService, wfsResult);
			}
		}
	}

	private XfsService getXfsService(final HSERVICE hService) {
		final String method = "getXfsService(HSERVICE)";
		for (XfsService xfsService : xfsServices) {
			if (xfsService.getService().equals(hService)) {
				return xfsService;
			}
		}
		if (LOG.isErrorEnabled()) {
			LOG.error(method, "Couldn't find XfsService: " + hService);
		}
		return null;
	}

	public void cancel(XfsService xfsService, RequestId requestID) throws XfsException {
		xfsAPI.wfsCancelAsyncRequest(xfsService.getService(), requestID);
	}

	public RequestId execute(XfsCommand xfsCommand, XfsEventNotification xfsEventNotification) throws XfsException {
		if (xfsCommand instanceof XfsOpenCommand) {
			return open((XfsOpenCommand) xfsCommand, xfsEventNotification);
		} else if (xfsCommand instanceof XfsCloseCommand) {
			return close((XfsCloseCommand) xfsCommand, xfsEventNotification);
		} else if (xfsCommand instanceof XfsInfoCommand) {
			return execute((XfsInfoCommand<?>) xfsCommand, xfsEventNotification);
		} else if (xfsCommand instanceof XfsExecuteCommand) {
			return execute((XfsExecuteCommand) xfsCommand, xfsEventNotification);
		} else if (xfsCommand instanceof XfsRegisterCommand) {
			return register((XfsRegisterCommand) xfsCommand, xfsEventNotification);
		} else if (xfsCommand instanceof XfsDeRegisterCommand) {
			return deRegister((XfsDeRegisterCommand) xfsCommand, xfsEventNotification);
		}
		throw new RuntimeException("Unhandled XFSCommand: " + xfsCommand);
	}

	private RequestId register(XfsRegisterCommand registerCommand, XfsEventNotification eventNotification) throws XfsException {
		XfsService xfsService = registerCommand.getXFSService();
		RequestId requestId = xfsAPI.wfsAsyncRegister(	xfsService.getService(),
														Bitmask.of(registerCommand.getEventClasses()),
														hWnd,
														hWnd);
		requestQueue.addRequest(requestId, eventNotification, registerCommand, null);
		return requestId;
	}

	private RequestId deRegister(XfsDeRegisterCommand deRegisterCommand, XfsEventNotification eventNotification) throws XfsException {
		XfsService xfsService = deRegisterCommand.getXFSService();
		XfsEventClass[] eventClasses = deRegisterCommand.getEventClasses();
		RequestId requestId = xfsAPI.wfsAsyncDeregister(xfsService.getService(),
														(eventClasses != null ? Bitmask.of(eventClasses) : null),
														hWnd,
														hWnd);
		requestQueue.addRequest(requestId, eventNotification, deRegisterCommand, null);
		return requestId;
	}

	private RequestId open(final XfsOpenCommand openCommand, final XfsEventNotification eventNotification) throws XfsException {
		final String method = "open(XFSOpenCommand, IXfsEventNotification)";
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "openCommand=" + openCommand + ",eventNotification=" + eventNotification);
		}
		final XfsService xfsService = openCommand.getXFSService();
		final DWORD dwTraceLevel = null;
		final DWORD timeOut = new DWORD(0L);
		final RequestId requestId = xfsAPI.wfsAsyncOpen(new ZSTR(xfsService.getLogicalName()),
														hApp,
														null,
														dwTraceLevel,
														timeOut,
														xfsService.getService(),
														hWnd,
														xfsService.getSrvcVersionsRequired(),
														xfsService.getSrvcVersion(),
														xfsService.getSPIVersion());
		requestQueue.addRequest(requestId, eventNotification, openCommand, Long.valueOf(timeOut.longValue()));
		return requestId;
	}

	private RequestId close(final XfsCloseCommand closeCommand, final XfsEventNotification eventNotification) throws XfsException {
		final XfsService xfsService = closeCommand.getXFSService();
		final RequestId requestId = xfsAPI.wfsAsyncClose(xfsService.getService(), hWnd);
		requestQueue.addRequest(requestId, eventNotification, closeCommand, null);
		return requestId;
	}

	private RequestId execute(final XfsExecuteCommand executeCommand, final XfsEventNotification eventNotification) throws XfsException {
		final DWORD command = new DWORD(((XfsConstant) executeCommand.getCommand()).getValue());
		Long timeOut = executeCommand.getTimeOut();
		if (timeOut == null) {
			timeOut = config.getXfsExecuteTimeout(executeCommand);
		}
		final RequestId requestId = xfsAPI.wfsAsyncExecute(	executeCommand.getXFSService().getService(),
															command,
															executeCommand.getCmdData(),
															new DWORD(timeOut.longValue()),
															hWnd);
		requestQueue.addRequest(requestId, eventNotification, executeCommand, timeOut);
		return requestId;
	}

	private RequestId execute(final XfsInfoCommand<?> infoCommand, final XfsEventNotification eventNotification) throws XfsException {
		final DWORD category = new DWORD(((XfsConstant) infoCommand.getCategory()).getValue());
		Long timeOut = infoCommand.getTimeOut();
		if (timeOut == null) {
			timeOut = config.getXfsInfoTimeout(infoCommand);
		}
		RequestId requestId = xfsAPI.wfsAsyncGetInfo(	infoCommand.getXFSService().getService(),
														category,
														infoCommand.getQueryDetails(),
														new DWORD(timeOut.longValue()),
														hWnd);
		requestQueue.addRequest(requestId, eventNotification, infoCommand, timeOut);
		return requestId;
	}

	private void startUpXfs() throws WFSStartUpException {
		final String method = "startUpXfs()";
		final XFSVersionDWORD versionsRequired = new XFSVersionDWORD();
		versionsRequired.allocate();
		versionsRequired.setLowVersion(XfsVersion.V2_00);
		versionsRequired.setHighVersion(XfsVersion.V3_20);
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug(method, "versionsRequired=" + versionsRequired);
			}
			version = xfsAPI.wfsStartUp(versionsRequired);
			if (LOG.isInfoEnabled()) {
				LOG.info(method, "XFS Version: " + version);
			}
		} catch (WFSStartUpException e) {
			LOG.error(method, "Error starting up XFS, versionsRequired=" + versionsRequired, e);
			throw e;
		}
	}

	public void free(final WFSResult wfsResult) {
		String method = "free(WFSResult)";
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "wfsResult=" + wfsResult);
		}
		try {
			xfsAPI.wfsFreeResult(wfsResult);
		} catch (XfsException e) {
			LOG.error(method, "Error freeing WFSResult: " + wfsResult, e);
		}
		synchronized (wfsResults) {
			wfsResults.remove(wfsResult);
		}
	}

	private void closeServices() {
		final String method = "closeServices()";
		synchronized (xfsServices) {
			while (xfsServices.size() >= 1) {
				XfsService xfsService = xfsServices.get(0);
				try {
					WFSResult wfsResult = new XfsCloseCommand(xfsService).call();
					if (LOG.isDebugEnabled()) {
						LOG.debug(method, "wfsResult=" + wfsResult);
					}
					free(wfsResult);
				} catch (final Exception e) {
					if (LOG.isErrorEnabled()) {
						LOG.error(method, "Error closing XfsService: " + xfsService, e);
					}
				}
				xfsServices.remove(0);
			}
		}
	}

	public void shutdown() {
		final String method = "shutdown()";
		closeServices();
		synchronized (wfsResults) {
			if (wfsResults.size() > 0) {
				LOG.warn(method, wfsResults.size() + " unfreed WFSResult's");
				for (int i = wfsResults.size() - 1; i >= 0; i--) {
					free(wfsResults.get(i));
				}
			}
		}
		if (hApp != null) {
			try {
				xfsAPI.wfsDestroyAppHandle(hApp);
			} catch (XfsException e) {
				LOG.error(method, "Error destroying application handle, hApp=" + hApp, e);
			}
		}
		try {
			xfsAPI.wfsCleanUp();
		} catch (XfsException e) {
			LOG.error(method, "Error cleaning up XFS", e);
		}
		messageHandler.close();
		requestQueue.close();
	}

}