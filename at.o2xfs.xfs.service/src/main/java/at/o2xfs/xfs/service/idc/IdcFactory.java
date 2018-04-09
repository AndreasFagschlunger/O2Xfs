package at.o2xfs.xfs.service.idc;

import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.XfsVersion;
import at.o2xfs.xfs.v3_00.idc.Capabilities3;
import at.o2xfs.xfs.v3_00.idc.CardAction3;
import at.o2xfs.xfs.v3_00.idc.CardData3;
import at.o2xfs.xfs.v3_00.idc.ChipIo3;
import at.o2xfs.xfs.v3_00.idc.ChipPowerOut3;
import at.o2xfs.xfs.v3_00.idc.Form3;
import at.o2xfs.xfs.v3_00.idc.ParseData3;
import at.o2xfs.xfs.v3_00.idc.RetainCard3;
import at.o2xfs.xfs.v3_00.idc.Setkey3;
import at.o2xfs.xfs.v3_00.idc.Status3;
import at.o2xfs.xfs.v3_00.idc.TrackEvent3;
import at.o2xfs.xfs.v3_00.idc.WriteTrack3;
import at.o2xfs.xfs.v3_10.idc.Capabilities310;
import at.o2xfs.xfs.v3_10.idc.DevicePosition310;
import at.o2xfs.xfs.v3_10.idc.EjectCard310;
import at.o2xfs.xfs.v3_10.idc.InterfaceModuleIdentifier310;
import at.o2xfs.xfs.v3_10.idc.PowerSaveChange310;
import at.o2xfs.xfs.v3_10.idc.PowerSaveControl310;
import at.o2xfs.xfs.v3_10.idc.SetGuidlight310;
import at.o2xfs.xfs.v3_10.idc.Status310;
import at.o2xfs.xfs.v3_20.idc.Form320;
import at.o2xfs.xfs.v3_20.idc.ParkCard320;
import at.o2xfs.xfs.v3_20.idc.Status320;
import at.o2xfs.xfs.v3_20.idc.TrackDetected320;
import at.o2xfs.xfs.v3_30.idc.AidData330;
import at.o2xfs.xfs.v3_30.idc.AppData330;
import at.o2xfs.xfs.v3_30.idc.Capabilities330;
import at.o2xfs.xfs.v3_30.idc.EmvClessConfigData330;
import at.o2xfs.xfs.v3_30.idc.EmvClessOutcome330;
import at.o2xfs.xfs.v3_30.idc.EmvClessReadStatus330;
import at.o2xfs.xfs.v3_30.idc.EmvClessTxData330;
import at.o2xfs.xfs.v3_30.idc.EmvClessUI330;
import at.o2xfs.xfs.v3_30.idc.HexData330;
import at.o2xfs.xfs.v3_30.idc.KeyData330;

public enum IdcFactory {

	INSTANCE;

	private <T> T doCreate(XfsVersion xfsVersion, Pointer p, Class<T> type) {
		Object result;
		if (AidData330.class.equals(type)) {
			result = new AidData330(new AidData330(p));
		} else if (AppData330.class.equals(type)) {
			result = new AppData330(new AppData330(p));
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
		} else if (DevicePosition310.class.equals(type)) {
			result = new DevicePosition310(new DevicePosition310(p));
		} else if (EjectCard310.class.equals(type)) {
			result = new EjectCard310(new EjectCard310(p));
		} else if (EmvClessConfigData330.class.equals(type)) {
			result = new EmvClessConfigData330(new EmvClessConfigData330(p));
		} else if (EmvClessOutcome330.class.equals(type)) {
			result = new EmvClessOutcome330(new EmvClessOutcome330(p));
		} else if (EmvClessReadStatus330.class.equals(type)) {
			result = new EmvClessReadStatus330(new EmvClessReadStatus330(p));
		} else if (EmvClessTxData330.class.equals(type)) {
			result = new EmvClessTxData330(new EmvClessTxData330(p));
		} else if (EmvClessUI330.class.equals(type)) {
			result = new EmvClessUI330(new EmvClessUI330(p));
		} else if (EmvClessOutcome330.class.equals(type)) {
			result = new EmvClessOutcome330(new EmvClessOutcome330(p));
		} else if (Form3.class.equals(type)) {
			result = createForm(xfsVersion, p);
		} else if (HexData330.class.equals(type)) {
			result = new HexData330(new HexData330(p));
		} else if (KeyData330.class.equals(type)) {
			result = new KeyData330(new KeyData330(p));
		} else if (Status3.class.equals(type)) {
			result = createStatus(xfsVersion, p);
		} else if (InterfaceModuleIdentifier310.class.equals(type)) {
			result = new InterfaceModuleIdentifier310(new InterfaceModuleIdentifier310(p));
		} else if (ParkCard320.class.equals(type)) {
			result = new ParkCard320(new ParkCard320(p));
		} else if (ParseData3.class.equals(type)) {
			result = new ParseData3(new ParseData3(p));
		} else if (PowerSaveChange310.class.equals(type)) {
			result = new PowerSaveChange310(new PowerSaveChange310(p));
		} else if (PowerSaveControl310.class.equals(type)) {
			result = new PowerSaveControl310(new PowerSaveControl310(p));
		} else if (RetainCard3.class.equals(type)) {
			result = new RetainCard3(new RetainCard3(p));
		} else if (SetGuidlight310.class.equals(type)) {
			result = new SetGuidlight310(new SetGuidlight310(p));
		} else if (Setkey3.class.equals(type)) {
			result = new Setkey3(new Setkey3(p));
		} else if (TrackDetected320.class.equals(type)) {
			result = new TrackDetected320(new TrackDetected320(p));
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
		if (xfsVersion.compareTo(XfsVersion.V3_30) >= 0) {
			result = new Capabilities330(new Capabilities330(p));
		} else if (xfsVersion.compareTo(XfsVersion.V3_10) >= 0) {
			result = new Capabilities310(new Capabilities310(p));
		} else {
			result = new Capabilities3(new Capabilities3(p));
		}
		return result;
	}

	private Form3 createForm(XfsVersion xfsVersion, Pointer p) {
		Form3 result;
		if (xfsVersion.compareTo(XfsVersion.V3_20) >= 0) {
			result = new Form320(new Form320(p));
		} else {
			result = new Form3(new Form3(p));
		}
		return result;
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

	public static <T> T create(XfsVersion xfsVersion, Pointer p, Class<T> type) {
		return INSTANCE.doCreate(xfsVersion, p, type);
	}
}
