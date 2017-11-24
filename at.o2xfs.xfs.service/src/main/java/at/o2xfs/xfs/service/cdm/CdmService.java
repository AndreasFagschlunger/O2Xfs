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

package at.o2xfs.xfs.service.cdm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.XfsServiceClass;
import at.o2xfs.xfs.cdm.CdmMessage;
import at.o2xfs.xfs.cdm.Position;
import at.o2xfs.xfs.v3_00.cdm.CashUnit3;
import at.o2xfs.xfs.v3_00.cdm.CdmCaps3;
import at.o2xfs.xfs.v3_00.cdm.CountsChanged3;
import at.o2xfs.xfs.v3_00.cdm.CurrencyExp3;
import at.o2xfs.xfs.v3_00.cdm.ItemPosition3;
import at.o2xfs.xfs.v3_10.cdm.DevicePosition310;
import at.o2xfs.xfs.v3_10.cdm.PowerSaveChange310;
import at.o2xfs.xfs.v3_30.cdm.ShutterStatusChanged330;
import at.o2xfs.xfs.service.ReflectiveFactory;
import at.o2xfs.xfs.service.XfsService;
import at.o2xfs.xfs.service.cdm.xfs3.CdmCapabilitiesCommand;
import at.o2xfs.xfs.service.cdm.xfs3.CurrencyExpCommand;
import at.o2xfs.xfs.win32.XfsWord;

public final class CdmService extends XfsService {

	private static final Logger LOG = LoggerFactory.getLogger(CdmService.class);

	private final List<CdmServiceListener> serviceListeners;

	private CdmCaps3 capabilities = null;
	private Collection<CurrencyExp3> currencyExponents = null;

	public CdmService(String logicalName) {
		super(logicalName, XfsServiceClass.CDM);
		serviceListeners = new ArrayList<>();
	}

	public CdmCaps3 getCapabilities() throws XfsException {
		synchronized (this) {
			if (capabilities == null) {
				capabilities = new CdmCapabilitiesCommand(this).call();
				if (LOG.isInfoEnabled()) {
					final String method = "getCapabilities()";
					LOG.info(method, capabilities);
				}
			}
		}
		return capabilities;
	}

	public Collection<CurrencyExp3> getCurrencyExponents() throws XfsException {
		synchronized (this) {
			if (currencyExponents == null) {
				currencyExponents = new CurrencyExpCommand(this).call();
				if (LOG.isInfoEnabled()) {
					final String method = "getCurrencyExponents()";
					LOG.info(method, currencyExponents);
				}
			}
		}
		return currencyExponents;
	}

	public void addCdmServiceListener(CdmServiceListener listener) {
		serviceListeners.add(listener);
	}

	public void removeCdmServiceListener(CdmServiceListener listener) {
		serviceListeners.remove(listener);
	}

	@Override
	public void fireServiceEvent(WFSResult wfsResult) {
		CdmMessage message = wfsResult.getEventID(CdmMessage.class);
		switch (message) {
			case SRVE_CASHUNITINFOCHANGED:
				fireCashUnitInfoChanged(create(wfsResult.getResults(), CashUnit3.class));
				break;
			case SRVE_COUNTS_CHANGED:
				fireCountsChanged(create(wfsResult.getResults(), CountsChanged3.class));
				break;
			case SRVE_DEVICEPOSITION:
				fireDevicePosition(create(wfsResult.getResults(), DevicePosition310.class));
				break;
			case SRVE_ITEMSPRESENTED:
				fireItemsPresented();
				break;
			case SRVE_ITEMSTAKEN:
				fireItemsTaken(new XfsWord<Position>(Position.class, wfsResult.getResults()).get());
				break;
			case SRVE_MEDIADETECTED:
				Optional<ItemPosition3> itemPosition = Optional.empty();
				if (!Pointer.NULL.equals(wfsResult.getResults())) {
					itemPosition = Optional.of(create(wfsResult.getResults(), ItemPosition3.class));
				}
				fireMediaDetected(itemPosition);
				break;
			case SRVE_POWER_SAVE_CHANGE:
				firePowerSaveChange(create(wfsResult.getResults(), PowerSaveChange310.class));
				break;
			case SRVE_SAFEDOORCLOSED:
				fireSafeDoorClosed();
				break;
			case SRVE_SAFEDOOROPEN:
				fireSafeDoorOpen();
				break;
			case SRVE_SHUTTERSTATUSCHANGED:
				fireShutterStatusChanged(create(wfsResult.getResults(), ShutterStatusChanged330.class));
				break;
			case SRVE_TELLERINFOCHANGED:
				fireTellerInfoChanged(new USHORT(wfsResult.getResults()).intValue());
				break;
			default:
				throw new IllegalArgumentException("CdmMessage: " + message);
		}
	}

	@Override
	public void fireUserEvent(WFSResult wfsResult) {
		CdmMessage message = wfsResult.getEventID(CdmMessage.class);
		switch (message) {
			case USRE_CASHUNITTHRESHOLD:
				fireCashUnitThreshold(create(wfsResult.getResults(), CashUnit3.class));
				break;
			default:
				throw new IllegalArgumentException("CdmMessage: " + message);
		}
	}

	private <R extends Struct> R create(Pointer buffer, Class<R> type) {
		return ReflectiveFactory.create(getXfsVersion(), buffer, type);
	}

	private void fireSafeDoorOpen() {
		if (LOG.isInfoEnabled()) {
			LOG.info("fireSafeDoorOpen()", "");
		}
		for (CdmServiceListener each : serviceListeners) {
			each.onSafeDoorOpen();
		}
	}

	private void fireSafeDoorClosed() {
		if (LOG.isInfoEnabled()) {
			LOG.info("fireSafeDoorClosed()", "");
		}
		for (CdmServiceListener each : serviceListeners) {
			each.onSafeDoorClosed();
		}
	}

	private void fireCashUnitThreshold(CashUnit3 cashUnit) {
		if (LOG.isInfoEnabled()) {
			LOG.info("fireCashUnitThreshold(CashUnit3)", cashUnit);
		}
		for (CdmServiceListener each : serviceListeners) {
			each.onCashUnitThreshold(cashUnit);
		}
	}

	private void fireCashUnitInfoChanged(CashUnit3 cashUnit) {
		if (LOG.isInfoEnabled()) {
			LOG.info("fireCashUnitInfoChanged(CashUnit3)", cashUnit);
		}
		for (CdmServiceListener each : serviceListeners) {
			each.onCashUnitInfoChanged(cashUnit);
		}
	}

	private void fireTellerInfoChanged(int tellerId) {
		if (LOG.isInfoEnabled()) {
			LOG.info("fireTellerInfoChanged(int)", tellerId);
		}
		for (CdmServiceListener each : serviceListeners) {
			each.onTellerInfoChanged(tellerId);
		}
	}

	private void fireItemsTaken(Position position) {
		if (LOG.isInfoEnabled()) {
			LOG.info("fireItemsTaken(Position)", position);
		}
		for (CdmServiceListener each : serviceListeners) {
			each.onItemsTaken(position);
		}
	}

	private void fireCountsChanged(CountsChanged3 countsChanged) {
		if (LOG.isInfoEnabled()) {
			LOG.info("fireCountsChanged(CountsChanged3)", countsChanged);
		}
		for (CdmServiceListener each : serviceListeners) {
			each.onCountsChanged(countsChanged);
		}
	}

	private void fireItemsPresented() {
		if (LOG.isInfoEnabled()) {
			LOG.info("fireItemsPresented()", "");
		}
		for (CdmServiceListener each : serviceListeners) {
			each.onItemsPresented();
		}
	}

	private void fireMediaDetected(Optional<ItemPosition3> itemPosition) {
		if (LOG.isInfoEnabled()) {
			LOG.info("fireMediaDetected(Optional<ItemPosition3>)", itemPosition);
		}
		for (CdmServiceListener each : serviceListeners) {
			each.onMediaDetected(itemPosition);
		}
	}

	private void fireDevicePosition(DevicePosition310 devicePosition) {
		if (LOG.isInfoEnabled()) {
			LOG.info("fireDevicePosition(DevicePosition310)", devicePosition);
		}
		for (CdmServiceListener each : serviceListeners) {
			each.onDevicePosition(devicePosition);
		}
	}

	private void firePowerSaveChange(PowerSaveChange310 powerSaveChange) {
		if (LOG.isInfoEnabled()) {
			LOG.info("firePowerSaveChange(PowerSaveChange310)", powerSaveChange);
		}
		for (CdmServiceListener each : serviceListeners) {
			each.onPowerSaveChange(powerSaveChange);
		}
	}

	private void fireShutterStatusChanged(ShutterStatusChanged330 shutterStatusChanged) {
		if (LOG.isInfoEnabled()) {
			LOG.info("fireShutterStatusChanged(ShutterStatusChanged330)", shutterStatusChanged);
		}
		for (CdmServiceListener each : serviceListeners) {
			each.onShutterStatusChanged(shutterStatusChanged);
		}
	}
}
