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

package at.o2xfs.xfs;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.win32.CHAR;
import at.o2xfs.win32.CharArray;
import at.o2xfs.win32.DWORD;
import at.o2xfs.win32.HWND;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Type;
import at.o2xfs.xfs.type.HAPP;
import at.o2xfs.xfs.type.HSERVICE;
import at.o2xfs.xfs.type.REQUESTID;

/**
 * @author Andreas Fagschlunger
 */
public class XfsAPI {

	private final static Logger LOG = LoggerFactory.getLogger(XfsAPI.class);

	private final static DWORD WFS_INDEFINITE_WAIT = new DWORD(0L);

	private static XfsAPI instance = null;

	private XfsAPI() {
		System.loadLibrary("O2Xfs");
	}

	public final static XfsAPI getInstance() {
		if (instance == null) {
			synchronized (XfsAPI.class) {
				if (instance == null) {
					instance = new XfsAPI();
				}
			}
		}
		return instance;
	}

	public void wfsCancelAsyncRequest(final HSERVICE hService,
			final REQUESTID requestID) throws XfsException {
		final String method = "wfsCancelAsyncRequest(HSERVICE, REQUESTID)";
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "hService=" + hService + ",requestID="
					+ requestID);
		}
		final int errorCode = (int) wfsCancelAsyncRequest0(hService, requestID);
		XfsException.throwFor(errorCode);
	}

	private native long wfsCancelAsyncRequest0(Type hService, Type requestID);

	/**
	 * Establishes a connection between an application and the XFS Manager.
	 * 
	 * @param versionsRequired
	 * @return
	 * @throws IllegalArgumentException
	 * @throws WFSStartUpException
	 */
	public WFSVersion wfsStartUp(final DWORD versionsRequired)
			throws IllegalArgumentException, WFSStartUpException {
		final String method = "wfsStartUp(DWORD)";
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "versionsRequired=" + versionsRequired);
		}
		if (versionsRequired == null) {
			throw new IllegalArgumentException(
					"versionsRequired cannot be null");
		}
		final WFSVersion wfsVersion = new WFSVersion();
		wfsVersion.allocate();
		final int errorCode = wfsStartUp0(versionsRequired, wfsVersion);
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "errorCode=" + errorCode);
		}
		try {
			XfsException.throwFor(errorCode);
		} catch (XfsException e) {
			throw new WFSStartUpException(wfsVersion, e);
		}
		return wfsVersion;
	}

	private native int wfsStartUp0(Type dwVersionsRequired, Type lpWFSVersion);

	/**
	 * The WFSCleanUp call indicates disconnection of a XFS application from the
	 * XFS Manager. This function, for example, frees resources allocated to the
	 * specific application. WFSCleanUp applies to all threads of a
	 * multi-threaded application. If WFSClose has not been issued for one or
	 * more service providers, then the XFS Manager will automatically issue the
	 * close(s). Once the WFSCleanUp has been performed, subsequent attempts to
	 * issue any XFS function other than WFSStartUp will fail.
	 * 
	 * @throws XfsException
	 */
	public void wfsCleanUp() throws XfsException {
		final String method = "wfsCleanUp()";
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "Calling WFSCleanUp ...");
		}
		final int errorCode = wfsCleanUp0();
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "errorCode=" + errorCode);
		}
		XfsException.throwFor(errorCode);
	}

	private native int wfsCleanUp0();

	/**
	 * Requests a new, unique application handle value.
	 * 
	 * @return
	 * @throws XfsException
	 */
	public HAPP wfsCreateAppHandle() throws XfsException {
		final HAPP hApp = new HAPP();
		hApp.allocate();
		final int errorCode = wfsCreateAppHandle0(hApp);
		if (LOG.isDebugEnabled()) {
			LOG.debug("wfsCreateAppHandle()", "errorCode=" + errorCode
					+ ",hApp=" + hApp);
		}
		XfsException.throwFor(errorCode);
		return hApp;
	}

	private native int wfsCreateAppHandle0(Type hApp);

	/**
	 * Discontinues monitoring of the specified message class(es) (or all
	 * classes) from the specified hService, by the specified hWndReg (or all
	 * the calling application's hWnd's). The asynchronous version of
	 * WFSDeregister.
	 * 
	 * @param hService
	 *            Service handle returned by WFSOpen or WFSAsyncOpen. If this
	 *            value is NULL, and dwEventClass is SYSTEM_EVENTS, the XFS
	 *            manager deregisters the application for those system events
	 *            generated by the Manager itself.
	 * @param dwEventClass
	 *            The class(es) of events from which the application is
	 *            deregistering. Specified as a bit mask that can be a logical
	 *            OR of the values for multiple classes. A NULL value requests
	 *            that all event classes be deregistered from the specified
	 *            window for this hService.
	 * @param hWndReg
	 *            The window which has been previously registered to receive
	 *            notification messages, and is now to be deregistered. A NULL
	 *            value requests that all the application's windows be
	 *            deregistered from the specified message class(es) for this
	 *            hService.
	 * @param hWnd
	 *            The window handle which is to receive the completion message
	 *            for this request.
	 * @param lpRequestID
	 *            Pointer to the request identifier for this request (returned
	 *            parameter).
	 * @throws XfsException
	 */
	public void wfsAsyncDeregister(final HSERVICE hService,
			final DWORD dwEventClass, final HWND hWndReg, final HWND hWnd,
			final REQUESTID lpRequestID) throws XfsException {
		final String method = "wfsAsyncDeregister(HSERVICE, DWORD, HWND, HWND, REQUESTID)";
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "hService=" + hService + ",dwEventClass="
					+ dwEventClass + ",hWndReg=" + hWndReg + ",hWnd=" + hWnd
					+ ",lpRequestID=" + lpRequestID);
		}
		final int errorCode = wfsAsyncDeregister0(hService, dwEventClass,
				hWndReg, hWnd, lpRequestID);
		XfsException.throwFor(errorCode);
	}

	private native int wfsAsyncDeregister0(Type hService, Type dwEventClass,
			Type hWndReg, Type hWnd, Type lpRequestID);

	/**
	 * Makes the specified application handle invalid.
	 * 
	 * @param hApp
	 *            The application handle to be made invalid.
	 * @throws XfsException
	 */
	public void wfsDestroyAppHandle(final HAPP hApp) throws XfsException {
		final String method = "wfsDestroyAppHandle(HAPP)";
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "hApp=" + hApp);
		}
		if (hApp == null) {
			throw new IllegalArgumentException("hApp must not be null");
		}
		final int errorCode = wfsDestroyAppHandle0(hApp);
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "errorCode=" + errorCode);
		}
		XfsException.throwFor(errorCode);
	}

	private native int wfsDestroyAppHandle0(Type lphApp);

	public void wfsAsyncExecute(final HSERVICE hService, final DWORD dwCommand,
			final Type lpCmdData, final DWORD dwTimeOut, final HWND hWnd,
			final REQUESTID requestID) throws XfsException {
		final String method = "wfsAsyncExecute(HSERVICE, DWORD, Type, DWORD, HWND, REQUESTID)";
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "hService=" + hService + ",dwCommand="
					+ dwCommand + ",lpCmdData=" + lpCmdData + ",dwTimeOut="
					+ dwTimeOut + ",hWnd=" + hWnd + ",requestID=" + requestID);
		}
		final int errorCode = wfsAsyncExecute0(hService, dwCommand, lpCmdData,
				dwTimeOut, hWnd, requestID);
		XfsException.throwFor(errorCode);
	}

	private native int wfsAsyncExecute0(Type hService, Type dwCommand,
			Type lpCmdData, Type dwTimeOut, Type hWnd, Type requestID);

	/**
	 * 
	 * @param wfsResult
	 * @throws XfsException
	 */
	public void wfsFreeResult(final WFSResult wfsResult) throws XfsException {
		final String method = "wfsFreeResult(WFSResult)";
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "wfsResult=" + wfsResult);
		}
		final int errorCode = wfsFreeResult0(wfsResult);
		XfsException.throwFor(errorCode);
	}

	private native int wfsFreeResult0(Type wfsResult);

	public WFSResult wfsGetInfo(final HSERVICE hService, final DWORD category,
			final Type queryDetails, final DWORD timeOut) throws XfsException {
		final String method = "wfsGetInfo(HSERVICE, DWORD, Type, DWORD)";
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "hService=" + hService + ",category=" + category
					+ ",queryDetails=" + queryDetails + ",timeOut=" + timeOut);
		}
		final Pointer pResult = new Pointer();
		pResult.allocate();
		final long errorCode = wfsGetInfo0(hService, category, queryDetails,
				timeOut, pResult);
		XfsException.throwFor(errorCode);
		final WFSResult wfsResult = new WFSResult(pResult);
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "wfsResult=" + wfsResult);
		}
		return wfsResult;
	}

	private native long wfsGetInfo0(Type hService, Type dwCategory,
			Type lpQueryDetails, Type dwTimeOut, Type lppResult);

	/**
	 * Retrieves information from the specified service provider. The
	 * asynchronous version of WFSGetInfo.
	 * 
	 * @param hService
	 *            Handle to the service provider as returned by WFSOpen or
	 *            WFSAsyncOpen.
	 * @param dwCategory
	 *            See WFSGetInfo.
	 * @param lpQueryDetails
	 *            See WFSGetInfo.
	 * @param dwTimeOut
	 *            Number of milliseconds to wait for completion
	 *            (WFS_INDEFINITE_WAIT to specify a request that will wait until
	 *            completion).
	 * @param hWnd
	 *            The window handle which is to receive the completion message
	 *            for this request.
	 * @param requestID
	 *            The request identifier for this request (returned parameter).
	 */
	public void wfsAsyncGetInfo(final HSERVICE hService,
			final DWORD dwCategory, final Type queryDetails,
			final DWORD dwTimeOut, final HWND hWnd, final REQUESTID requestID)
			throws XfsException {
		final String method = "wfsAsyncGetInfo(HSERVICE, DWORD, Type, DWORD, HWND, REQUESTID)";
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "hService=" + hService + ",dwCategory="
					+ dwCategory + ",queryDetails=" + queryDetails
					+ ",dwTimeOut=" + dwTimeOut + ",hWnd=" + hWnd
					+ ",requestID=" + requestID);
		}
		final int errorCode = wfsAsyncGetInfo0(hService, dwCategory,
				queryDetails, dwTimeOut, hWnd, requestID);
		XfsException.throwFor(errorCode);
	}

	private native int wfsAsyncGetInfo0(Type hService, Type dwCategory,
			Type lpQueryDetails, Type dwTimeOut, Type hWnd, Type lpRequestID);

	public void wfsAsyncOpen(final CharArray logicalName, final HAPP hApp,
			final CHAR lpszAppID, final DWORD dwTraceLevel,
			final DWORD dwTimeOut, final HSERVICE lphService, final HWND hWnd,
			final DWORD dwSrvcVersionsRequired, final WFSVersion lpSrvcVersion,
			final WFSVersion lpSPIVersion, final REQUESTID requestID)
			throws XfsException {
		final String method = "wfsAsyncOpen(CharArray, HAPP, CHAR, DWORD, DWORD, HSERVICE, HWND, DWORD, WFSVersion, WFSVersion, REQUESTID)";
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "logicalName=" + logicalName + ",hApp=" + hApp
					+ ",lpszAppID=" + lpszAppID + ",dwTraceLevel="
					+ dwTraceLevel + ",dwTimeOut=" + dwTimeOut + ",lphService="
					+ lphService + ",hWnd=" + hWnd + ",dwSrvcVersionsRequired="
					+ dwSrvcVersionsRequired + ",lpSrvcVersion="
					+ lpSrvcVersion + ",lpSPIVersion=" + lpSPIVersion
					+ ",requestID=" + requestID);
		}
		final int errorCode = wfsAsyncOpen0(logicalName, hApp, lpszAppID,
				dwTraceLevel, dwTimeOut, lphService, hWnd,
				dwSrvcVersionsRequired, lpSrvcVersion, lpSPIVersion, requestID);
		XfsException.throwFor(errorCode);
	}

	private native int wfsAsyncOpen0(Type lpszLogicalName, Type hApp,
			Type lpszAppID, Type dwTraceLevel, Type dwTimeOut, Type lphService,
			Type hWnd, Type dwSrvcVersionsRequired, Type lpSrvcVersion,
			Type lpSPIVersion, Type lpRequestID);

	/**
	 * Enables event monitoring for the specified service by the specified
	 * window; all messages of the specified class(es) are sent to the window
	 * specified in the hWndReg parameter. The asynchronous version of
	 * WFSRegister.
	 * 
	 * @param hService
	 *            Handle to the service provider as returned by WFSOpen or
	 *            WFSAsyncOpen. If this value is NULL, and dwEventClass is
	 *            SYSTEM_EVENTS, the XFS manager registers the application for
	 *            those system events generated by the Manager itself.
	 * @param dwEventClass
	 *            The class(es) of events for which the application is
	 *            registering. Specified as a set of bit masks that are
	 *            logically ORed together into this parameter.
	 * @param hWndReg
	 *            The window handle which is to be registered to receive the
	 *            specified messages.
	 * @param hWnd
	 *            The window handle which is to receive the completion message
	 *            for this request.
	 * @param lpRequestID
	 *            Pointer to the request identifier for this request (returned
	 *            parameter).
	 */
	public void wfsAsyncRegister(final HSERVICE hService,
			final DWORD dwEventClass, final HWND hWndReg, final HWND hWnd,
			final REQUESTID lpRequestID) throws XfsException {
		final String method = "wfsAsyncRegister(HSERVICE, DWORD, HWND, HWND, REQUESTID)";
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "hService=" + hService + ",dwEventClass="
					+ dwEventClass + ",hWndReg=" + hWndReg + ",hWnd=" + hWnd
					+ ",lpRequestID=" + lpRequestID);
		}
		int errorCode = wfsAsyncRegister0(hService, dwEventClass, hWndReg,
				hWnd, lpRequestID);
		XfsException.throwFor(errorCode);
	}

	private native int wfsAsyncRegister0(Type hService, Type dwEventClass,
			Type hWndReg, Type hWnd, Type lpRequestID);

	public void wfsAsyncClose(final HSERVICE lphService, final HWND hWnd,
			final REQUESTID lpRequestID) throws XfsException {
		String method = "wfsAsyncClose(HSERVICE, HWND, REQUESTID)";
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "lphService=" + lphService + ",hWnd=" + hWnd
					+ ",lpRequestID=" + lpRequestID);
		}
		final int errorCode = wfsAsyncClose0(lphService, hWnd, lpRequestID);
		XfsException.throwFor(errorCode);
	}

	private native int wfsAsyncClose0(Type lphService, Type hWnd,
			Type lpRequestID);
}
