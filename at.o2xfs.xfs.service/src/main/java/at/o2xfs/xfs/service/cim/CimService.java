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

package at.o2xfs.xfs.service.cim;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.XfsServiceClass;
import at.o2xfs.xfs.cim.CimMessage;
import at.o2xfs.xfs.v3_00.cim.Capabilities3;
import at.o2xfs.xfs.v3_00.cim.CashIn3;
import at.o2xfs.xfs.v3_00.cim.CountsChanged3;
import at.o2xfs.xfs.v3_00.cim.ItemPosition3;
import at.o2xfs.xfs.v3_00.cim.NoteTypeList3;
import at.o2xfs.xfs.v3_10.cim.DevicePosition310;
import at.o2xfs.xfs.v3_10.cim.PositionInfo310;
import at.o2xfs.xfs.v3_10.cim.PowerSaveChange310;
import at.o2xfs.xfs.v3_30.cim.ShutterStatusChanged330;
import at.o2xfs.xfs.service.XfsService;
import at.o2xfs.xfs.service.cim.cmd.info.CimCapabilitiesCommand;

public class CimService extends XfsService {

	private static final Logger LOG = LoggerFactory.getLogger(CimService.class);

	private List<CimServiceListener> serviceListeners;

	private List<CimUserListener> userListeners;

	private Capabilities3 capabilities = null;

	private NoteTypeList3 noteTypeList = null;

	protected CimService(String logicalName) {
		super(logicalName, XfsServiceClass.CIM);
		serviceListeners = new ArrayList<>();
		userListeners = new ArrayList<>();
	}

	public void add(CimServiceListener listener) {
		serviceListeners.add(listener);
	}

	public void remove(CimServiceListener listener) {
		serviceListeners.remove(listener);
	}

	public void add(CimUserListener listener) {
		userListeners.add(listener);
	}

	public void remove(CimUserListener listener) {
		userListeners.remove(listener);
	}

	public Capabilities3 getCapabilities() throws XfsException {
		synchronized (this) {
			if (capabilities == null) {
				capabilities = new CimCapabilitiesCommand(this).call();
			}
		}
		return capabilities;
	}

	public NoteTypeList3 getNoteTypeList() throws XfsException {
		return noteTypeList;
	}

	@Override
	public void fireServiceEvent(WFSResult wfsResult) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("fireServiceEvent(WFSResult)", wfsResult);
		}
		CimMessage cimMessage = wfsResult.getEventID(CimMessage.class);
		switch (cimMessage) {
		case SRVE_SAFEDOOROPEN:
			fireSafeDoorOpen();
			break;
		case SRVE_SAFEDOORCLOSED:
			fireSafeDoorClosed();
			break;
		case SRVE_CASHUNITINFOCHANGED:
			fireCashUnitInfoChanged(CimFactory.create(getXfsVersion(), wfsResult.getResults(), CashIn3.class));
			break;
		case SRVE_TELLERINFOCHANGED:
			fireTellerInfoChanged(new USHORT(wfsResult.getResults()).intValue());
			break;
		case SRVE_ITEMSTAKEN:
			fireItemsTaken(wfsResult.getResults());
			break;
		case SRVE_COUNTS_CHANGED:
			fireCountsChanged(CimFactory.create(getXfsVersion(), wfsResult.getResults(), CountsChanged3.class));
			break;
		case SRVE_ITEMSPRESENTED:
			fireItemsPresented(wfsResult.getResults());
			break;
		case SRVE_ITEMSINSERTED:
			fireItemsInserted(wfsResult.getResults());
			break;
		case SRVE_MEDIADETECTED:
			fireMediaDetected(wfsResult.getResults());
			break;
		case SRVE_DEVICEPOSITION:
			fireDevicePosition(CimFactory.create(getXfsVersion(), wfsResult.getResults(), DevicePosition310.class));
			break;
		case SRVE_POWER_SAVE_CHANGE:
			firePowerSaveChange(CimFactory.create(getXfsVersion(), wfsResult.getResults(), PowerSaveChange310.class));
			break;
		case SRVE_SHUTTERSTATUSCHANGED:
			fireShutterStatusChanged(
					CimFactory.create(getXfsVersion(), wfsResult.getResults(), ShutterStatusChanged330.class));
			break;
		default:
			throw new IllegalArgumentException(cimMessage.toString());
		}
	}

	private void fireSafeDoorOpen() {
		if (LOG.isInfoEnabled()) {
			LOG.info("fireSafeDoorOpen()", "");
		}
		for (CimServiceListener each : serviceListeners) {
			each.onSafeDoorOpen();
		}
	};

	private void fireSafeDoorClosed() {
		if (LOG.isInfoEnabled()) {
			LOG.info("fireSafeDoorClosed()", "");
		}
		for (CimServiceListener each : serviceListeners) {
			each.onSafeDoorClosed();
		}
	};

	private void fireCashUnitInfoChanged(CashIn3 cashUnit) {

	};

	private void fireTellerInfoChanged(int tellerId) {
		if (LOG.isInfoEnabled()) {
			LOG.info("fireTellerInfoChanged(int)", "tellerId=" + tellerId);
		}
		for (CimServiceListener each : serviceListeners) {
			each.onTellerInfoChanged(tellerId);
		}
	};

	private void fireItemsTaken(Pointer p) {
		Optional<PositionInfo310> positionInfo = Optional.empty();
		if (!Pointer.NULL.equals(p)) {
			positionInfo = Optional.of(CimFactory.create(getXfsVersion(), p, PositionInfo310.class));
		}
		if (LOG.isInfoEnabled()) {
			LOG.info("fireItemsTaken(Pointer)", "positionInfo=" + positionInfo);
		}
		for (CimServiceListener each : serviceListeners) {
			each.onItemsTaken(positionInfo);
		}
	};

	private void fireCountsChanged(CountsChanged3 countsChanged) {
		if (LOG.isInfoEnabled()) {
			LOG.info("fireCountsChanged(CountsChanged3)", "countsChanged=" + countsChanged);
		}
		for (CimServiceListener each : serviceListeners) {
			each.onCountsChanged(countsChanged);
		}
	};

	private void fireItemsPresented(Pointer p) {
		Optional<PositionInfo310> positionInfo = Optional.empty();
		if (!Pointer.NULL.equals(p)) {
			positionInfo = Optional.of(CimFactory.create(getXfsVersion(), p, PositionInfo310.class));
		}
		if (LOG.isInfoEnabled()) {
			LOG.info("fireItemsPresented(Pointer)", "positionInfo=" + positionInfo);
		}
		for (CimServiceListener each : serviceListeners) {
			each.onItemsPresented(positionInfo);
		}
	};

	private void fireItemsInserted(Pointer p) {
		Optional<PositionInfo310> positionInfo = Optional.empty();
		if (!Pointer.NULL.equals(p)) {
			positionInfo = Optional.of(CimFactory.create(getXfsVersion(), p, PositionInfo310.class));
		}
		if (LOG.isInfoEnabled()) {
			LOG.info("fireItemsInserted(Pointer)", "positionInfo=" + positionInfo);
		}
		for (CimServiceListener each : serviceListeners) {
			each.onItemsInserted(positionInfo);
		}
	};

	private void fireMediaDetected(Pointer p) {
		Optional<ItemPosition3> itemPosition = Optional.empty();
		if (!Pointer.NULL.equals(p)) {
			itemPosition = Optional.of(CimFactory.create(getXfsVersion(), p, ItemPosition3.class));
		}
		if (LOG.isInfoEnabled()) {
			LOG.info("fireMediaDetected(Pointer)", "itemPosition=" + itemPosition);
		}
		for (CimServiceListener each : serviceListeners) {
			each.onMediaDetected(itemPosition);
		}
	};

	private void fireDevicePosition(DevicePosition310 devicePosition) {
		if (LOG.isInfoEnabled()) {
			LOG.info("fireDevicePosition(DevicePosition310)", "devicePosition=" + devicePosition);
		}
		for (CimServiceListener each : serviceListeners) {
			each.onDevicePosition(devicePosition);
		}
	};

	private void firePowerSaveChange(PowerSaveChange310 powerSaveChange) {
		if (LOG.isInfoEnabled()) {
			LOG.info("firePowerSaveChange(PowerSaveChange310)", "powerSaveChange=" + powerSaveChange);
		}
		for (CimServiceListener each : serviceListeners) {
			each.onPowerSaveChange(powerSaveChange);
		}
	};

	private void fireShutterStatusChanged(ShutterStatusChanged330 shutterStatusChanged) {
		if (LOG.isInfoEnabled()) {
			LOG.info("fireShutterStatusChanged(ShutterStatusChanged330)", shutterStatusChanged);
		}
		for (CimServiceListener each : serviceListeners) {
			each.onShutterStatusChanged(shutterStatusChanged);
		}
	}

	@Override
	public void fireUserEvent(WFSResult wfsResult) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("fireServiceEvent(WFSResult)", wfsResult);
		}
		CimMessage cimMessage = wfsResult.getEventID(CimMessage.class);
		switch (cimMessage) {
		case USRE_CASHUNITTHRESHOLD:
			fireCashUnitThreshold(CimFactory.create(getXfsVersion(), wfsResult.getResults(), CashIn3.class));
			break;
		default:
			throw new IllegalArgumentException(cimMessage.toString());
		}
	}

	private void fireCashUnitThreshold(CashIn3 cashUnit) {
		if (LOG.isInfoEnabled()) {
			LOG.info("fireCashUnitThreshold(CashIn3)", cashUnit);
		}
		for (CimUserListener each : userListeners) {
			each.onCashUnitThreshold(cashUnit);
		}
	}
}
