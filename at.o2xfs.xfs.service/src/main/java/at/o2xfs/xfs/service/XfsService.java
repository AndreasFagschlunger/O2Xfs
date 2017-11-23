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

package at.o2xfs.xfs.service;

import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.WFSVersion;
import at.o2xfs.xfs.WfsAppDisconnect;
import at.o2xfs.xfs.WfsDevStatus;
import at.o2xfs.xfs.WfsHardwareError2;
import at.o2xfs.xfs.WfsHardwareError3;
import at.o2xfs.xfs.WfsUndeliverableMessage;
import at.o2xfs.xfs.WfsVersionError;
import at.o2xfs.xfs.XFSVersionDWORD;
import at.o2xfs.xfs.XfsServiceClass;
import at.o2xfs.xfs.XfsSystemEvent;
import at.o2xfs.xfs.XfsVersion;
import at.o2xfs.xfs.service.events.XfsStatusNotification;
import at.o2xfs.xfs.service.util.ExceptionUtil;
import at.o2xfs.xfs.type.HSERVICE;

/**
 * @author Andreas Fagschlunger
 *
 */
public abstract class XfsService implements XfsStatusNotification {

	/**
	 *
	 */
	private final static Logger LOG = LoggerFactory.getLogger(XfsService.class);

	/**
	 * The pre-defined logical name of a service. It is a high level name such as
	 * "SYSJOURNAL1," "PASSBOOKPTR3" or "CASHDISP02," that is used by the XFS
	 * Manager and the service provider solely as a key to obtain the specific
	 * configuration information they need.
	 */
	private String logicalName = null;

	/**
	 * Pointer to the service handle that the XFS Manager assigns to the service on
	 * a successful open; the application uses this handle for communication with
	 * the service provider for the remainder of the session (returned parameter).
	 * If a process opens the same service twice, the XFS Manager generates and
	 * returns different hService values.
	 */
	private HSERVICE hService = null;

	/**
	 * Specifies the range of versions of the service-specific interface that the
	 * application can support. (See Comments.) The low-order word indicates the
	 * highest version of the interface the application can support; the high-order
	 * word indicates the lowest version of the interface the application can
	 * support. In each word, the low-order byte specifies the major version number
	 * and the high-order byte specifies the minor version number (i.e., the numbers
	 * before and after the decimal). Note: in order to allow intermediate minor
	 * revisions (e.g., between 1.10 and 1.20), the minor version number should
	 * always be expressed as two decimal digits, i.e., 1.10, 1.11, 1.20, etc.
	 */
	private XFSVersionDWORD srvcVersionsRequired = null;

	/**
	 * Pointer to the data structure that is to receive version support information
	 * and other details about the service-specific interface implementation
	 * (returned parameter).
	 */
	private WFSVersion srvcVersion = null;

	/**
	 * Pointer to the data structure that is to receive version support information
	 * and (optionally) other details about the SPI implementation of the service
	 * provider being opened (returned parameter).
	 */
	private WFSVersion spiVersion = null;

	/**
	 *
	 */
	private XfsServiceClass serviceClass = null;

	protected XfsService(final String logicalName, final XfsServiceClass serviceClass) {
		if (logicalName == null) {
			ExceptionUtil.nullArgument("logicalName");
		} else if (serviceClass == null) {
			ExceptionUtil.nullArgument("serviceClass");
		}
		this.logicalName = logicalName;
		this.serviceClass = serviceClass;
		hService = new HSERVICE();
		hService.allocate();
		srvcVersionsRequired = new XFSVersionDWORD();
		srvcVersionsRequired.allocate();
		srvcVersion = new WFSVersion();
		srvcVersion.allocate();
		spiVersion = new WFSVersion();
		spiVersion.allocate();
	}

	public XfsVersion getXfsVersion() {
		return srvcVersion.getVersion();
	}

	@Override
	public final void fireSystemEvent(final WFSResult wfsResult) {
		final String method = "fireSystemEvent(WFSResult)";
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "wfsResult=" + wfsResult);
		}
		final XfsSystemEvent systemEvent = wfsResult.getEventID(XfsSystemEvent.class);
		switch (systemEvent) {
		case UNDELIVERABLE_MSG:
			WfsUndeliverableMessage undeliverableMessage = new WfsUndeliverableMessage(wfsResult.getResults());
			LOG.info(method, undeliverableMessage);
			break;
		case VERSION_ERROR:
			WfsVersionError versionError = new WfsVersionError(new WfsVersionError(wfsResult.getResults()));
			if (LOG.isInfoEnabled()) {
				LOG.info(method, versionError);
			}
			break;
		case DEVICE_STATUS:
			final WfsDevStatus status = new WfsDevStatus(wfsResult.getResults());
			if (LOG.isInfoEnabled()) {
				LOG.info(method, status);
			}
			break;
		case APP_DISCONNECT:
			WfsAppDisconnect appDisconnect = new WfsAppDisconnect(wfsResult.getResults());
			LOG.info(method, appDisconnect);
			break;
		case HARDWARE_ERROR:
		case SOFTWARE_ERROR:
		case USER_ERROR:
		case FRAUD_ATTEMPT:
			final WfsHardwareError2 hwError = createHwError(wfsResult.getResults());
			if (LOG.isInfoEnabled()) {
				LOG.info(method, hwError);
			}
			break;
		case LOCK_REQUESTED:
			LOG.info(method, wfsResult);
			break;
		}
	}

	private WfsHardwareError2 createHwError(Pointer p) {
		WfsHardwareError2 result;
		if (XfsVersion.V3_00.compareTo(getXfsVersion()) > 0) {
			result = new WfsHardwareError2(new WfsHardwareError2(p));
		} else {
			result = new WfsHardwareError3(new WfsHardwareError3(p));
		}
		return result;
	}

	public XfsServiceClass getServiceClass() {
		return serviceClass;
	}

	/**
	 * @return the logicalName
	 */
	public String getLogicalName() {
		return logicalName;
	}

	/**
	 * @return the hService
	 */
	public HSERVICE getService() {
		return hService;
	}

	/**
	 * @return the srvcVersionsRequired
	 */
	public XFSVersionDWORD getSrvcVersionsRequired() {
		return srvcVersionsRequired;
	}

	/**
	 * @return the srvcVersion
	 */
	public WFSVersion getSrvcVersion() {
		return srvcVersion;
	}

	/**
	 * @return the spiVersion
	 */
	public WFSVersion getSPIVersion() {
		return spiVersion;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("logicalName", logicalName)
				.append("serviceClass", serviceClass)
				.append("hService", hService)
				.append("srvcVersionsRequired", srvcVersionsRequired)
				.append("srvcVersion", srvcVersion)
				.append("spiVersion", spiVersion)
				.toString();
	}
}