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
import java.util.Collections;
import java.util.List;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.ZList;
import at.o2xfs.xfs.XfsVersion;
import at.o2xfs.xfs.cim.v3_00.Capabilities3;
import at.o2xfs.xfs.cim.v3_00.CashIn3;
import at.o2xfs.xfs.cim.v3_00.CashInStatus3;
import at.o2xfs.xfs.cim.v3_00.CashInfo3;
import at.o2xfs.xfs.cim.v3_00.CashUnitError3;
import at.o2xfs.xfs.cim.v3_00.CountsChanged3;
import at.o2xfs.xfs.cim.v3_00.CurrencyExp3;
import at.o2xfs.xfs.cim.v3_00.ItemPosition3;
import at.o2xfs.xfs.cim.v3_00.NoteNumberList3;
import at.o2xfs.xfs.cim.v3_00.NoteTypeList3;
import at.o2xfs.xfs.cim.v3_00.P6Info3;
import at.o2xfs.xfs.cim.v3_00.P6Signature3;
import at.o2xfs.xfs.cim.v3_00.Status3;
import at.o2xfs.xfs.cim.v3_00.TellerDetails3;
import at.o2xfs.xfs.cim.v3_10.Capabilities3_10;
import at.o2xfs.xfs.cim.v3_10.CashIn3_10;
import at.o2xfs.xfs.cim.v3_10.ConfigureNoteReaderOut3_10;
import at.o2xfs.xfs.cim.v3_10.DevicePosition3_10;
import at.o2xfs.xfs.cim.v3_10.ItemInfo3_10;
import at.o2xfs.xfs.cim.v3_10.ItemInfoSummary3_10;
import at.o2xfs.xfs.cim.v3_10.P6CompareResult3_10;
import at.o2xfs.xfs.cim.v3_10.PositionCapabilities3_10;
import at.o2xfs.xfs.cim.v3_10.PositionInfo3_10;
import at.o2xfs.xfs.cim.v3_10.PowerSaveChange3_10;
import at.o2xfs.xfs.cim.v3_10.Status3_10;
import at.o2xfs.xfs.cim.v3_20.Capabilities3_20;
import at.o2xfs.xfs.cim.v3_20.CashUnitCapabilities3_20;
import at.o2xfs.xfs.cim.v3_20.DeviceLockStatus3_20;
import at.o2xfs.xfs.cim.v3_20.IncompleteReplenish3_20;
import at.o2xfs.xfs.cim.v3_20.ReplenishInfoResult3_20;
import at.o2xfs.xfs.cim.v3_20.ReplenishResult3_20;
import at.o2xfs.xfs.cim.v3_20.Status3_20;
import at.o2xfs.xfs.cim.v3_30.AllItemsInfo3_30;
import at.o2xfs.xfs.cim.v3_30.Blacklist3_30;
import at.o2xfs.xfs.cim.v3_30.Capabilities3_30;
import at.o2xfs.xfs.cim.v3_30.DepleteInfoResult3_30;
import at.o2xfs.xfs.cim.v3_30.DepleteResult3_30;
import at.o2xfs.xfs.cim.v3_30.IncompleteDeplete3_30;
import at.o2xfs.xfs.cim.v3_30.ItemInfo3_30;
import at.o2xfs.xfs.cim.v3_30.ShutterStatusChanged3_30;

public enum CimFactory {

	INSTANCE;

	private <T> T doCreate(XfsVersion xfsVersion, Pointer p, Class<T> type) {
		Object result;
		if (AllItemsInfo3_30.class.equals(type)) {
			result = createAllItemsInfo(p);
		} else if (Blacklist3_30.class.equals(type)) {
			result = createBlacklist(p);
		} else if (Capabilities3.class.equals(type)) {
			result = createCapabilities(xfsVersion, p);
		} else if (CashIn3.class.equals(type)) {
			result = createCashIn(xfsVersion, p);
		} else if (CashInfo3.class.equals(type)) {
			result = createCashInfo(p);
		} else if (CashInStatus3.class.equals(type)) {
			result = createCashInStatus(p);
		} else if (CashUnitCapabilities3_20.class.equals(type)) {
			result = createCashUnitCapabilities(p);
		} else if (CashUnitError3.class.equals(type)) {
			result = createCashUnitError(p);
		} else if (ConfigureNoteReaderOut3_10.class.equals(type)) {
			result = createConfigureNoteReaderOut(p);
		} else if (CountsChanged3.class.equals(type)) {
			result = createCountsChanged(p);
		} else if (CurrencyExp3.class.equals(type)) {
			result = createCurrencyExp(p);
		} else if (DepleteInfoResult3_30.class.equals(type)) {
			result = createDepleteInfoResult(p);
		} else if (DepleteResult3_30.class.equals(type)) {
			result = createDepleteResult(p);
		} else if (DeviceLockStatus3_20.class.equals(type)) {
			result = createDeviceLockStatus(p);
		} else if (DevicePosition3_10.class.equals(type)) {
			result = createDevicePosition(p);
		} else if (IncompleteDeplete3_30.class.equals(type)) {
			result = createIncompleteDeplete(p);
		} else if (IncompleteReplenish3_20.class.equals(type)) {
			result = createIncompleteReplenish(p);
		} else if (ItemInfo3_10.class.equals(type)) {
			result = createItemInfo(xfsVersion, p);
		} else if (ItemInfoSummary3_10.class.equals(type)) {
			result = createItemInfoSummary(p);
		} else if (ItemPosition3.class.equals(type)) {
			result = createItemPosition(p);
		} else if (NoteNumberList3.class.equals(type)) {
			result = createNoteNumberList(p);
		} else if (NoteTypeList3.class.equals(type)) {
			result = createNoteTypeList(p);
		} else if (P6CompareResult3_10.class.equals(type)) {
			result = createP6CompareResult(p);
		} else if (P6Info3.class.equals(type)) {
			result = createP6Info(p);
		} else if (P6Signature3.class.equals(type)) {
			result = createP6Signature(p);
		} else if (PositionCapabilities3_10.class.equals(type)) {
			result = createPositionCapabilities(p);
		} else if (PositionInfo3_10.class.equals(type)) {
			result = createPositionInfo(p);
		} else if (PowerSaveChange3_10.class.equals(type)) {
			result = createPowerSaveChange(p);
		} else if (ReplenishInfoResult3_20.class.equals(type)) {
			result = createReplenishInfoResult(p);
		} else if (ReplenishResult3_20.class.equals(type)) {
			result = createReplenishResult(p);
		} else if (ShutterStatusChanged3_30.class.equals(type)) {
			result = createShutterStatusChanged(p);
		} else if (Status3.class.equals(type)) {
			result = createStatus(xfsVersion, p);
		} else if (TellerDetails3.class.equals(type)) {
			result = createTellerDetails(p);
		} else {
			throw new IllegalArgumentException(type.toString());
		}
		return type.cast(result);
	}

	private AllItemsInfo3_30 createAllItemsInfo(Pointer p) {
		return new AllItemsInfo3_30(new AllItemsInfo3_30(p));
	}

	private Blacklist3_30 createBlacklist(Pointer p) {
		return new Blacklist3_30(new Blacklist3_30(p));
	}

	private Capabilities3 createCapabilities(XfsVersion xfsVersion, Pointer p) {
		Capabilities3 result;
		if (XfsVersion.V3_30.compareTo(xfsVersion) >= 0) {
			result = new Capabilities3_30(new Capabilities3_30(p));
		} else if (XfsVersion.V3_20.compareTo(xfsVersion) >= 0) {
			result = new Capabilities3_20(new Capabilities3_20(p));
		} else if (XfsVersion.V3_10.compareTo(xfsVersion) >= 0) {
			result = new Capabilities3_10(new Capabilities3_10(p));
		} else {
			result = new Capabilities3(new Capabilities3(p));
		}
		return result;
	}

	private CashIn3 createCashIn(XfsVersion xfsVersion, Pointer p) {
		CashIn3 result;
		if (XfsVersion.V3_10.compareTo(xfsVersion) >= 0) {
			result = new CashIn3_10(new CashIn3_10(p));
		} else {
			result = new CashIn3(new CashIn3(p));
		}
		return result;
	}

	private CashInfo3 createCashInfo(Pointer p) {
		return new CashInfo3(new CashInfo3(p));
	}

	private CashInStatus3 createCashInStatus(Pointer p) {
		return new CashInStatus3(new CashInStatus3(p));
	}

	private CashUnitCapabilities3_20 createCashUnitCapabilities(Pointer p) {
		return new CashUnitCapabilities3_20(new CashUnitCapabilities3_20(p));
	}

	private CashUnitError3 createCashUnitError(Pointer p) {
		return new CashUnitError3(new CashUnitError3(p));
	}

	private ConfigureNoteReaderOut3_10 createConfigureNoteReaderOut(Pointer p) {
		return new ConfigureNoteReaderOut3_10(new ConfigureNoteReaderOut3_10(p));
	}

	private CountsChanged3 createCountsChanged(Pointer p) {
		return new CountsChanged3(new CountsChanged3(p));
	}

	private CurrencyExp3 createCurrencyExp(Pointer p) {
		return new CurrencyExp3(new CurrencyExp3(p));
	}

	private DepleteInfoResult3_30 createDepleteInfoResult(Pointer p) {
		return new DepleteInfoResult3_30(new DepleteInfoResult3_30(p));
	}

	private DepleteResult3_30 createDepleteResult(Pointer p) {
		return new DepleteResult3_30(new DepleteResult3_30(p));
	}

	private DeviceLockStatus3_20 createDeviceLockStatus(Pointer p) {
		return new DeviceLockStatus3_20(new DeviceLockStatus3_20(p));
	}

	private DevicePosition3_10 createDevicePosition(Pointer p) {
		return new DevicePosition3_10(new DevicePosition3_10(p));
	}

	private IncompleteDeplete3_30 createIncompleteDeplete(Pointer p) {
		return new IncompleteDeplete3_30(new IncompleteDeplete3_30(p));
	}

	private IncompleteReplenish3_20 createIncompleteReplenish(Pointer p) {
		return new IncompleteReplenish3_20(new IncompleteReplenish3_20(p));
	}

	private ItemInfo3_10 createItemInfo(XfsVersion xfsVersion, Pointer p) {
		ItemInfo3_10 result;
		if (XfsVersion.V3_30.compareTo(xfsVersion) >= 0) {
			result = new ItemInfo3_30(new ItemInfo3_30(p));
		} else {
			result = new ItemInfo3_10(new ItemInfo3_10(p));
		}
		return result;
	}

	private ItemInfoSummary3_10 createItemInfoSummary(Pointer p) {
		return new ItemInfoSummary3_10(new ItemInfoSummary3_10(p));
	}

	private ItemPosition3 createItemPosition(Pointer p) {
		return new ItemPosition3(new ItemPosition3(p));
	}

	private NoteNumberList3 createNoteNumberList(Pointer p) {
		return new NoteNumberList3(new NoteNumberList3(p));
	}

	private NoteTypeList3 createNoteTypeList(Pointer p) {
		return new NoteTypeList3(new NoteTypeList3(p));
	}

	private P6CompareResult3_10 createP6CompareResult(Pointer p) {
		return new P6CompareResult3_10(new P6CompareResult3_10(p));
	}

	private P6Info3 createP6Info(Pointer p) {
		return new P6Info3(new P6Info3(p));
	}

	private P6Signature3 createP6Signature(Pointer p) {
		return new P6Signature3(new P6Signature3(p));
	}

	private PositionCapabilities3_10 createPositionCapabilities(Pointer p) {
		return new PositionCapabilities3_10(new PositionCapabilities3_10(p));
	}

	private PositionInfo3_10 createPositionInfo(Pointer p) {
		return new PositionInfo3_10(new PositionInfo3_10(p));
	}

	private PowerSaveChange3_10 createPowerSaveChange(Pointer p) {
		return new PowerSaveChange3_10(new PowerSaveChange3_10(p));
	}

	private ReplenishInfoResult3_20 createReplenishInfoResult(Pointer p) {
		return new ReplenishInfoResult3_20(new ReplenishInfoResult3_20(p));
	}

	private ReplenishResult3_20 createReplenishResult(Pointer p) {
		return new ReplenishResult3_20(new ReplenishResult3_20(p));
	}

	private ShutterStatusChanged3_30 createShutterStatusChanged(Pointer p) {
		return new ShutterStatusChanged3_30(new ShutterStatusChanged3_30(p));
	}

	private Status3 createStatus(XfsVersion xfsVersion, Pointer p) {
		Status3 result;
		if (XfsVersion.V3_20.compareTo(xfsVersion) >= 0) {
			result = new Status3_20(new Status3_20(p));
		} else if (XfsVersion.V3_10.compareTo(xfsVersion) >= 0) {
			result = new Status3_10(new Status3_10(p));
		} else {
			result = new Status3(new Status3(p));
		}
		return result;
	}

	private TellerDetails3 createTellerDetails(Pointer p) {
		return new TellerDetails3(new TellerDetails3(p));
	}

	public static <T> T create(XfsVersion xfsVersion, Pointer p, Class<T> type) {
		return INSTANCE.doCreate(xfsVersion, p, type);
	}

	public static <T> List<T> fromNullTerminatedArray(XfsVersion xfsVersion, Pointer p, Class<T> type) {
		List<T> result = new ArrayList<>();
		for (Pointer each : new ZList(p)) {
			result.add(create(xfsVersion, each, type));
		}
		return Collections.unmodifiableList(result);
	}
}