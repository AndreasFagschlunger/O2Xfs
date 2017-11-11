package at.o2xfs.xfs.service.idc;

import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.XfsVersion;
import at.o2xfs.xfs.idc.v3_00.Capabilities3;
import at.o2xfs.xfs.idc.v3_00.CardAction3;
import at.o2xfs.xfs.idc.v3_00.CardData3;
import at.o2xfs.xfs.idc.v3_00.ChipIo3;
import at.o2xfs.xfs.idc.v3_00.ChipPowerOut3;
import at.o2xfs.xfs.idc.v3_00.Form3;
import at.o2xfs.xfs.idc.v3_00.ParseData3;
import at.o2xfs.xfs.idc.v3_00.RetainCard3;
import at.o2xfs.xfs.idc.v3_00.Setkey3;
import at.o2xfs.xfs.idc.v3_00.Status3;
import at.o2xfs.xfs.idc.v3_00.TrackEvent3;
import at.o2xfs.xfs.idc.v3_00.WriteTrack3;
import at.o2xfs.xfs.idc.v3_10.Capabilities3_10;
import at.o2xfs.xfs.idc.v3_10.DevicePosition3_10;
import at.o2xfs.xfs.idc.v3_10.EjectCard3_10;
import at.o2xfs.xfs.idc.v3_10.InterfaceModuleIdentifier3_10;
import at.o2xfs.xfs.idc.v3_10.PowerSaveChange3_10;
import at.o2xfs.xfs.idc.v3_10.PowerSaveControl3_10;
import at.o2xfs.xfs.idc.v3_10.SetGuidlight3_10;
import at.o2xfs.xfs.idc.v3_10.Status3_10;
import at.o2xfs.xfs.idc.v3_20.Form3_20;
import at.o2xfs.xfs.idc.v3_20.ParkCard3_20;
import at.o2xfs.xfs.idc.v3_20.Status3_20;
import at.o2xfs.xfs.idc.v3_20.TrackDetected3_20;
import at.o2xfs.xfs.idc.v3_30.AidData3_30;
import at.o2xfs.xfs.idc.v3_30.AppData3_30;
import at.o2xfs.xfs.idc.v3_30.Capabilities3_30;
import at.o2xfs.xfs.idc.v3_30.EmvClessConfigData3_30;
import at.o2xfs.xfs.idc.v3_30.EmvClessOutcome3_30;
import at.o2xfs.xfs.idc.v3_30.EmvClessReadStatus3_30;
import at.o2xfs.xfs.idc.v3_30.EmvClessTxData3_30;
import at.o2xfs.xfs.idc.v3_30.EmvClessUI3_30;
import at.o2xfs.xfs.idc.v3_30.HexData3_30;
import at.o2xfs.xfs.idc.v3_30.KeyData3_30;

public enum IdcFactory {

	INSTANCE;

	private <T> T doCreate(XfsVersion xfsVersion, Pointer p, Class<T> type) {
		Object result;
		if (AidData3_30.class.equals(type)) {
			result = new AidData3_30(new AidData3_30(p));
		} else if (AppData3_30.class.equals(type)) {
			result = new AppData3_30(new AppData3_30(p));
		} else if (Capabilities3.class.equals(type)) {
			result = createCapabilities(xfsVersion, p);
		} else if (CardAction3.class.equals(type)) {
			result = new CardAction3(new CardAction3(p));
		} else if (CardData3.class.equals(type)) {
			result = new CardData3(new CardData3(p));
		} else if (ChipIo3.class.equals(type)) {
			result = new ChipIo3(new ChipIo3(p));
		} else if (ChipPowerOut3.class.equals(type)) {
			result = new ChipPowerOut3(new ChipPowerOut3(p));
		} else if (DevicePosition3_10.class.equals(type)) {
			result = new DevicePosition3_10(new DevicePosition3_10(p));
		} else if (EjectCard3_10.class.equals(type)) {
			result = new EjectCard3_10(new EjectCard3_10(p));
		} else if (EmvClessConfigData3_30.class.equals(type)) {
			result = new EmvClessConfigData3_30(new EmvClessConfigData3_30(p));
		} else if (EmvClessOutcome3_30.class.equals(type)) {
			result = new EmvClessOutcome3_30(new EmvClessOutcome3_30(p));
		} else if (EmvClessReadStatus3_30.class.equals(type)) {
			result = new EmvClessReadStatus3_30(new EmvClessReadStatus3_30(p));
		} else if (EmvClessTxData3_30.class.equals(type)) {
			result = new EmvClessTxData3_30(new EmvClessTxData3_30(p));
		} else if (EmvClessUI3_30.class.equals(type)) {
			result = new EmvClessUI3_30(new EmvClessUI3_30(p));
		} else if (EmvClessOutcome3_30.class.equals(type)) {
			result = new EmvClessOutcome3_30(new EmvClessOutcome3_30(p));
		} else if (Form3.class.equals(type)) {
			result = createForm(xfsVersion, p);
		} else if (HexData3_30.class.equals(type)) {
			result = new HexData3_30(new HexData3_30(p));
		} else if (KeyData3_30.class.equals(type)) {
			result = new KeyData3_30(new KeyData3_30(p));
		} else if (Status3.class.equals(type)) {
			result = createStatus(xfsVersion, p);
		} else if (InterfaceModuleIdentifier3_10.class.equals(type)) {
			result = new InterfaceModuleIdentifier3_10(new InterfaceModuleIdentifier3_10(p));
		} else if (ParkCard3_20.class.equals(type)) {
			result = new ParkCard3_20(new ParkCard3_20(p));
		} else if (ParseData3.class.equals(type)) {
			result = new ParseData3(new ParseData3(p));
		} else if (PowerSaveChange3_10.class.equals(type)) {
			result = new PowerSaveChange3_10(new PowerSaveChange3_10(p));
		} else if (PowerSaveControl3_10.class.equals(type)) {
			result = new PowerSaveControl3_10(new PowerSaveControl3_10(p));
		} else if (RetainCard3.class.equals(type)) {
			result = new RetainCard3(new RetainCard3(p));
		} else if (SetGuidlight3_10.class.equals(type)) {
			result = new SetGuidlight3_10(new SetGuidlight3_10(p));
		} else if (Setkey3.class.equals(type)) {
			result = new Setkey3(new Setkey3(p));
		} else if (TrackDetected3_20.class.equals(type)) {
			result = new TrackDetected3_20(new TrackDetected3_20(p));
		} else if (TrackEvent3.class.equals(type)) {
			result = new TrackEvent3(new TrackEvent3(p));
		} else if (WriteTrack3.class.equals(type)) {
			result = new WriteTrack3(new WriteTrack3(p));
		} else {
			throw new IllegalArgumentException(type.toString());
		}
		return type.cast(result);
	}

	private Capabilities3 createCapabilities(XfsVersion xfsVersion, Pointer p) {
		Capabilities3 result;
		if (XfsVersion.V3_30.compareTo(xfsVersion) >= 0) {
			result = new Capabilities3_30(new Capabilities3_30(p));
		} else if (XfsVersion.V3_10.compareTo(xfsVersion) >= 0) {
			result = new Capabilities3_10(new Capabilities3_10(p));
		} else {
			result = new Capabilities3(new Capabilities3(p));
		}
		return result;
	}

	private Form3 createForm(XfsVersion xfsVersion, Pointer p) {
		Form3 result;
		if (XfsVersion.V3_20.compareTo(xfsVersion) >= 0) {
			result = new Form3_20(new Form3_20(p));
		} else {
			result = new Form3(new Form3(p));
		}
		return result;
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

	public static <T> T create(XfsVersion xfsVersion, Pointer p, Class<T> type) {
		return INSTANCE.doCreate(xfsVersion, p, type);
	}
}
