package at.o2xfs.xfs.service.cdm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.ZList;
import at.o2xfs.xfs.XfsVersion;
import at.o2xfs.xfs.v3_00.cdm.Calibrate3;
import at.o2xfs.xfs.v3_00.cdm.CashUnit3;
import at.o2xfs.xfs.v3_00.cdm.CashUnitError3;
import at.o2xfs.xfs.v3_00.cdm.CashUnitInfo3;
import at.o2xfs.xfs.v3_00.cdm.CdmCaps3;
import at.o2xfs.xfs.v3_00.cdm.CdmStatus3;
import at.o2xfs.xfs.v3_00.cdm.Count3;
import at.o2xfs.xfs.v3_00.cdm.CountsChanged3;
import at.o2xfs.xfs.v3_00.cdm.CurrencyExp3;
import at.o2xfs.xfs.v3_00.cdm.Denomination3;
import at.o2xfs.xfs.v3_00.cdm.ItemPosition3;
import at.o2xfs.xfs.v3_00.cdm.MixTable3;
import at.o2xfs.xfs.v3_00.cdm.MixType3;
import at.o2xfs.xfs.v3_00.cdm.PresentStatus3;
import at.o2xfs.xfs.v3_00.cdm.TellerDetails3;
import at.o2xfs.xfs.v3_00.cdm.TellerInfo3;
import at.o2xfs.xfs.v3_10.cdm.CashUnit310;
import at.o2xfs.xfs.v3_10.cdm.CashUnitError310;
import at.o2xfs.xfs.v3_10.cdm.CashUnitInfo310;
import at.o2xfs.xfs.v3_10.cdm.CdmCaps310;
import at.o2xfs.xfs.v3_10.cdm.CdmStatus310;
import at.o2xfs.xfs.v3_10.cdm.DevicePosition310;
import at.o2xfs.xfs.v3_10.cdm.PowerSaveChange310;
import at.o2xfs.xfs.v3_20.cdm.CdmCaps320;
import at.o2xfs.xfs.v3_20.cdm.CdmStatus320;
import at.o2xfs.xfs.v3_20.cdm.ItemNumberList320;
import at.o2xfs.xfs.v3_30.cdm.AllItemsInfo330;
import at.o2xfs.xfs.v3_30.cdm.Blacklist330;
import at.o2xfs.xfs.v3_30.cdm.CdmCaps330;
import at.o2xfs.xfs.v3_30.cdm.IncompleteRetract330;
import at.o2xfs.xfs.v3_30.cdm.ItemInfo330;
import at.o2xfs.xfs.v3_30.cdm.ItemInfoSummary330;
import at.o2xfs.xfs.v3_30.cdm.ShutterStatusChanged330;

public enum CdmFactory {

	INSTANCE;

	private <T> T doCreate(XfsVersion xfsVersion, Pointer p, Class<T> type) {
		Object result;
		if (AllItemsInfo330.class.equals(type)) {
			result = createAllItemsInfo(p);
		} else if (Blacklist330.class.equals(type)) {
			result = createBlacklist(p);
		} else if (Calibrate3.class.equals(type)) {
			result = createCalibrate(p);
		} else if (CashUnit3.class.equals(type)) {
			result = createCashUnit(xfsVersion, p);
		} else if (CashUnitError3.class.equals(type)) {
			result = createCashUnitError(xfsVersion, p);
		} else if (CashUnitInfo3.class.equals(type)) {
			result = createCashUnitInfo(xfsVersion, p);
		} else if (CdmCaps3.class.equals(type)) {
			result = createCapabilities(xfsVersion, p);
		} else if (Count3.class.equals(type)) {
			result = createCount(p);
		} else if (CountsChanged3.class.equals(type)) {
			result = createCountsChanged(p);
		} else if (CurrencyExp3.class.equals(type)) {
			result = createCurrencyExp(p);
		} else if (Denomination3.class.equals(type)) {
			result = createDenomination(p);
		} else if (DevicePosition310.class.equals(type)) {
			result = createDevicePosition(p);
		} else if (IncompleteRetract330.class.equals(type)) {
			result = createIncompleteRetract(p);
		} else if (ItemInfo330.class.equals(type)) {
			result = createItemInfo(p);
		} else if (ItemInfoSummary330.class.equals(type)) {
			result = createItemInfoSummary(p);
		} else if (ItemNumberList320.class.equals(type)) {
			result = createItemNumberList(p);
		} else if (ItemPosition3.class.equals(type)) {
			result = createItemPosition(p);
		} else if (MixTable3.class.equals(type)) {
			result = createMixTable(p);
		} else if (MixType3.class.equals(type)) {
			result = createMixType(p);
		} else if (PowerSaveChange310.class.equals(type)) {
			result = createPowerSaveChange(p);
		} else if (PresentStatus3.class.equals(type)) {
			result = createPresentStatus(p);
		} else if (ShutterStatusChanged330.class.equals(type)) {
			result = createShutterStatusChanged(p);
		} else if (CdmStatus3.class.equals(type)) {
			result = createStatus(xfsVersion, p);
		} else if (TellerDetails3.class.equals(type)) {
			result = createTellerDetails(p);
		} else if (TellerInfo3.class.equals(type)) {
			result = createTellerInfo(p);
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

	private Calibrate3 createCalibrate(Pointer p) {
		return new Calibrate3(new Calibrate3(p));
	}

	private CashUnit3 createCashUnit(XfsVersion xfsVersion, Pointer p) {
		CashUnit3 result;
		if (xfsVersion.compareTo(XfsVersion.V3_10) >= 0) {
			result = new CashUnit310(new CashUnit310(p));
		} else {
			result = new CashUnit3(new CashUnit3(p));
		}
		return result;
	}

	private CashUnitError3 createCashUnitError(XfsVersion xfsVersion, Pointer p) {
		CashUnitError3 result;
		if (xfsVersion.compareTo(XfsVersion.V3_10) >= 0) {
			result = new CashUnitError310(new CashUnitError310(p));
		} else {
			result = new CashUnitError3(new CashUnitError3(p));
		}
		return result;
	}

	private CashUnitInfo3 createCashUnitInfo(XfsVersion xfsVersion, Pointer p) {
		CashUnitInfo3 result;
		if (xfsVersion.compareTo(XfsVersion.V3_10) >= 0) {
			result = new CashUnitInfo310(new CashUnitInfo310(p));
		} else {
			result = new CashUnitInfo3(new CashUnitInfo3(p));
		}
		return result;
	}

	private CdmCaps3 createCapabilities(XfsVersion xfsVersion, Pointer p) {
		CdmCaps3 result;
		if (xfsVersion.compareTo(XfsVersion.V3_30) >= 0) {
			result = new CdmCaps330(new CdmCaps330(p));
		} else if (xfsVersion.compareTo(XfsVersion.V3_20) >= 0) {
			result = new CdmCaps320(new CdmCaps320(p));
		} else if (xfsVersion.compareTo(XfsVersion.V3_10) >= 0) {
			result = new CdmCaps310(new CdmCaps310(p));
		} else {
			result = new CdmCaps3(new CdmCaps3(p));
		}
		return result;
	}

	private Count3 createCount(Pointer p) {
		return new Count3(new Count3(p));
	}

	private CountsChanged3 createCountsChanged(Pointer p) {
		return new CountsChanged3(new CountsChanged3(p));
	}

	private CurrencyExp3 createCurrencyExp(Pointer p) {
		return new CurrencyExp3(new CurrencyExp3(p));
	}

	private Denomination3 createDenomination(Pointer p) {
		return new Denomination3(new Denomination3(p));
	}

	private DevicePosition310 createDevicePosition(Pointer p) {
		return new DevicePosition310(new DevicePosition310(p));
	}

	private IncompleteRetract330 createIncompleteRetract(Pointer p) {
		return new IncompleteRetract330(new IncompleteRetract330(p));
	}

	private ItemInfo330 createItemInfo(Pointer p) {
		return new ItemInfo330(new ItemInfo330(p));
	}

	private ItemInfoSummary330 createItemInfoSummary(Pointer p) {
		return new ItemInfoSummary330(new ItemInfoSummary330(p));
	}

	private ItemNumberList320 createItemNumberList(Pointer p) {
		return new ItemNumberList320(new ItemNumberList320(p));
	}

	private ItemPosition3 createItemPosition(Pointer p) {
		return new ItemPosition3(new ItemPosition3(p));
	}

	private MixType3 createMixType(Pointer p) {
		return new MixType3(new MixType3(p));
	}

	private MixTable3 createMixTable(Pointer p) {
		return new MixTable3(new MixTable3(p));
	}

	private PowerSaveChange310 createPowerSaveChange(Pointer p) {
		return new PowerSaveChange310(new PowerSaveChange310(p));
	}

	private PresentStatus3 createPresentStatus(Pointer p) {
		return new PresentStatus3(new PresentStatus3(p));
	}

	private ShutterStatusChanged330 createShutterStatusChanged(Pointer p) {
		return new ShutterStatusChanged330(new ShutterStatusChanged330(p));
	}

	private CdmStatus3 createStatus(XfsVersion xfsVersion, Pointer p) {
		CdmStatus3 result;
		if (xfsVersion.compareTo(XfsVersion.V3_20) >= 0) {
			result = new CdmStatus320(new CdmStatus320(p));
		} else if (xfsVersion.compareTo(XfsVersion.V3_10) >= 0) {
			result = new CdmStatus310(new CdmStatus310(p));
		} else {
			result = new CdmStatus3(new CdmStatus3(p));
		}
		return result;
	}

	private TellerDetails3 createTellerDetails(Pointer p) {
		return new TellerDetails3(new TellerDetails3(p));
	}

	private TellerInfo3 createTellerInfo(Pointer p) {
		return new TellerInfo3(new TellerInfo3(p));
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
