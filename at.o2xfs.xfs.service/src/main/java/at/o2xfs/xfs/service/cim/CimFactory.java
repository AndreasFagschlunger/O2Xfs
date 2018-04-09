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
import at.o2xfs.xfs.v3_00.cim.Capabilities3;
import at.o2xfs.xfs.v3_00.cim.CashIn3;
import at.o2xfs.xfs.v3_00.cim.CashInStatus3;
import at.o2xfs.xfs.v3_00.cim.CashInfo3;
import at.o2xfs.xfs.v3_00.cim.CashUnitError3;
import at.o2xfs.xfs.v3_00.cim.CountsChanged3;
import at.o2xfs.xfs.v3_00.cim.CurrencyExp3;
import at.o2xfs.xfs.v3_00.cim.ItemPosition3;
import at.o2xfs.xfs.v3_00.cim.NoteNumberList3;
import at.o2xfs.xfs.v3_00.cim.NoteTypeList3;
import at.o2xfs.xfs.v3_00.cim.P6Info3;
import at.o2xfs.xfs.v3_00.cim.P6Signature3;
import at.o2xfs.xfs.v3_00.cim.Status3;
import at.o2xfs.xfs.v3_00.cim.TellerDetails3;
import at.o2xfs.xfs.v3_10.cim.Capabilities310;
import at.o2xfs.xfs.v3_10.cim.CashIn310;
import at.o2xfs.xfs.v3_10.cim.ConfigureNoteReaderOut310;
import at.o2xfs.xfs.v3_10.cim.DevicePosition310;
import at.o2xfs.xfs.v3_10.cim.ItemInfo310;
import at.o2xfs.xfs.v3_10.cim.ItemInfoSummary310;
import at.o2xfs.xfs.v3_10.cim.P6CompareResult310;
import at.o2xfs.xfs.v3_10.cim.PositionCapabilities310;
import at.o2xfs.xfs.v3_10.cim.PositionInfo310;
import at.o2xfs.xfs.v3_10.cim.PowerSaveChange310;
import at.o2xfs.xfs.v3_10.cim.Status310;
import at.o2xfs.xfs.v3_20.cim.Capabilities320;
import at.o2xfs.xfs.v3_20.cim.CashUnitCapabilities320;
import at.o2xfs.xfs.v3_20.cim.DeviceLockStatus320;
import at.o2xfs.xfs.v3_20.cim.IncompleteReplenish320;
import at.o2xfs.xfs.v3_20.cim.ReplenishInfoResult320;
import at.o2xfs.xfs.v3_20.cim.ReplenishResult320;
import at.o2xfs.xfs.v3_20.cim.Status320;
import at.o2xfs.xfs.v3_30.cim.AllItemsInfo330;
import at.o2xfs.xfs.v3_30.cim.Blacklist330;
import at.o2xfs.xfs.v3_30.cim.Capabilities330;
import at.o2xfs.xfs.v3_30.cim.DepleteInfoResult330;
import at.o2xfs.xfs.v3_30.cim.DepleteResult330;
import at.o2xfs.xfs.v3_30.cim.IncompleteDeplete330;
import at.o2xfs.xfs.v3_30.cim.ItemInfo330;
import at.o2xfs.xfs.v3_30.cim.ShutterStatusChanged330;

public enum CimFactory {

	INSTANCE;

	private <T> T doCreate(XfsVersion xfsVersion, Pointer p, Class<T> type) {
		Object result;
		if (AllItemsInfo330.class.equals(type)) {
			result = createAllItemsInfo(p);
		} else if (Blacklist330.class.equals(type)) {
			result = createBlacklist(p);
		} else if (Capabilities3.class.equals(type)) {
			result = createCapabilities(xfsVersion, p);
		} else if (CashIn3.class.equals(type)) {
			result = createCashIn(xfsVersion, p);
		} else if (CashInfo3.class.equals(type)) {
			result = createCashInfo(p);
		} else if (CashInStatus3.class.equals(type)) {
			result = createCashInStatus(p);
		} else if (CashUnitCapabilities320.class.equals(type)) {
			result = createCashUnitCapabilities(p);
		} else if (CashUnitError3.class.equals(type)) {
			result = createCashUnitError(p);
		} else if (ConfigureNoteReaderOut310.class.equals(type)) {
			result = createConfigureNoteReaderOut(p);
		} else if (CountsChanged3.class.equals(type)) {
			result = createCountsChanged(p);
		} else if (CurrencyExp3.class.equals(type)) {
			result = createCurrencyExp(p);
		} else if (DepleteInfoResult330.class.equals(type)) {
			result = createDepleteInfoResult(p);
		} else if (DepleteResult330.class.equals(type)) {
			result = createDepleteResult(p);
		} else if (DeviceLockStatus320.class.equals(type)) {
			result = createDeviceLockStatus(p);
		} else if (DevicePosition310.class.equals(type)) {
			result = createDevicePosition(p);
		} else if (IncompleteDeplete330.class.equals(type)) {
			result = createIncompleteDeplete(p);
		} else if (IncompleteReplenish320.class.equals(type)) {
			result = createIncompleteReplenish(p);
		} else if (ItemInfo310.class.equals(type)) {
			result = createItemInfo(xfsVersion, p);
		} else if (ItemInfoSummary310.class.equals(type)) {
			result = createItemInfoSummary(p);
		} else if (ItemPosition3.class.equals(type)) {
			result = createItemPosition(p);
		} else if (NoteNumberList3.class.equals(type)) {
			result = createNoteNumberList(p);
		} else if (NoteTypeList3.class.equals(type)) {
			result = createNoteTypeList(p);
		} else if (P6CompareResult310.class.equals(type)) {
			result = createP6CompareResult(p);
		} else if (P6Info3.class.equals(type)) {
			result = createP6Info(p);
		} else if (P6Signature3.class.equals(type)) {
			result = createP6Signature(p);
		} else if (PositionCapabilities310.class.equals(type)) {
			result = createPositionCapabilities(p);
		} else if (PositionInfo310.class.equals(type)) {
			result = createPositionInfo(p);
		} else if (PowerSaveChange310.class.equals(type)) {
			result = createPowerSaveChange(p);
		} else if (ReplenishInfoResult320.class.equals(type)) {
			result = createReplenishInfoResult(p);
		} else if (ReplenishResult320.class.equals(type)) {
			result = createReplenishResult(p);
		} else if (ShutterStatusChanged330.class.equals(type)) {
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

	private AllItemsInfo330 createAllItemsInfo(Pointer p) {
		return new AllItemsInfo330(new AllItemsInfo330(p));
	}

	private Blacklist330 createBlacklist(Pointer p) {
		return new Blacklist330(new Blacklist330(p));
	}

	private Capabilities3 createCapabilities(XfsVersion xfsVersion, Pointer p) {
		Capabilities3 result;
		if (xfsVersion.compareTo(XfsVersion.V3_30) >= 0) {
			result = new Capabilities330(new Capabilities330(p));
		} else if (xfsVersion.compareTo(XfsVersion.V3_20) >= 0) {
			result = new Capabilities320(new Capabilities320(p));
		} else if (xfsVersion.compareTo(XfsVersion.V3_10) >= 0) {
			result = new Capabilities310(new Capabilities310(p));
		} else {
			result = new Capabilities3(new Capabilities3(p));
		}
		return result;
	}

	private CashIn3 createCashIn(XfsVersion xfsVersion, Pointer p) {
		CashIn3 result;
		if (xfsVersion.compareTo(XfsVersion.V3_10) >= 0) {
			result = new CashIn310(new CashIn310(p));
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

	private CashUnitCapabilities320 createCashUnitCapabilities(Pointer p) {
		return new CashUnitCapabilities320(new CashUnitCapabilities320(p));
	}

	private CashUnitError3 createCashUnitError(Pointer p) {
		return new CashUnitError3(new CashUnitError3(p));
	}

	private ConfigureNoteReaderOut310 createConfigureNoteReaderOut(Pointer p) {
		return new ConfigureNoteReaderOut310(new ConfigureNoteReaderOut310(p));
	}

	private CountsChanged3 createCountsChanged(Pointer p) {
		return new CountsChanged3(new CountsChanged3(p));
	}

	private CurrencyExp3 createCurrencyExp(Pointer p) {
		return new CurrencyExp3(new CurrencyExp3(p));
	}

	private DepleteInfoResult330 createDepleteInfoResult(Pointer p) {
		return new DepleteInfoResult330(new DepleteInfoResult330(p));
	}

	private DepleteResult330 createDepleteResult(Pointer p) {
		return new DepleteResult330(new DepleteResult330(p));
	}

	private DeviceLockStatus320 createDeviceLockStatus(Pointer p) {
		return new DeviceLockStatus320(new DeviceLockStatus320(p));
	}

	private DevicePosition310 createDevicePosition(Pointer p) {
		return new DevicePosition310(new DevicePosition310(p));
	}

	private IncompleteDeplete330 createIncompleteDeplete(Pointer p) {
		return new IncompleteDeplete330(new IncompleteDeplete330(p));
	}

	private IncompleteReplenish320 createIncompleteReplenish(Pointer p) {
		return new IncompleteReplenish320(new IncompleteReplenish320(p));
	}

	private ItemInfo310 createItemInfo(XfsVersion xfsVersion, Pointer p) {
		ItemInfo310 result;
		if (xfsVersion.compareTo(XfsVersion.V3_30) >= 0) {
			result = new ItemInfo330(new ItemInfo330(p));
		} else {
			result = new ItemInfo310(new ItemInfo310(p));
		}
		return result;
	}

	private ItemInfoSummary310 createItemInfoSummary(Pointer p) {
		return new ItemInfoSummary310(new ItemInfoSummary310(p));
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

	private P6CompareResult310 createP6CompareResult(Pointer p) {
		return new P6CompareResult310(new P6CompareResult310(p));
	}

	private P6Info3 createP6Info(Pointer p) {
		return new P6Info3(new P6Info3(p));
	}

	private P6Signature3 createP6Signature(Pointer p) {
		return new P6Signature3(new P6Signature3(p));
	}

	private PositionCapabilities310 createPositionCapabilities(Pointer p) {
		return new PositionCapabilities310(new PositionCapabilities310(p));
	}

	private PositionInfo310 createPositionInfo(Pointer p) {
		return new PositionInfo310(new PositionInfo310(p));
	}

	private PowerSaveChange310 createPowerSaveChange(Pointer p) {
		return new PowerSaveChange310(new PowerSaveChange310(p));
	}

	private ReplenishInfoResult320 createReplenishInfoResult(Pointer p) {
		return new ReplenishInfoResult320(new ReplenishInfoResult320(p));
	}

	private ReplenishResult320 createReplenishResult(Pointer p) {
		return new ReplenishResult320(new ReplenishResult320(p));
	}

	private ShutterStatusChanged330 createShutterStatusChanged(Pointer p) {
		return new ShutterStatusChanged330(new ShutterStatusChanged330(p));
	}

	private Status3 createStatus(XfsVersion xfsVersion, Pointer p) {
		Status3 result;
		if (xfsVersion.compareTo(XfsVersion.V3_20) >= 0) {
			result = new Status320(new Status320(p));
		} else if (xfsVersion.compareTo(XfsVersion.V3_10) >= 0) {
			result = new Status310(new Status310(p));
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
