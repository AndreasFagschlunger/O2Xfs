package at.o2xfs.xfs.service.idc;

import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.XfsVersion;
import at.o2xfs.xfs.idc.v3_00.Capabilities3;
import at.o2xfs.xfs.idc.v3_00.CardAction3;
import at.o2xfs.xfs.idc.v3_00.CardData3;
import at.o2xfs.xfs.idc.v3_00.ChipIo3;
import at.o2xfs.xfs.idc.v3_00.ChipPowerOut3;
import at.o2xfs.xfs.idc.v3_00.Form3;
import at.o2xfs.xfs.idc.v3_00.IdcStatus3;
import at.o2xfs.xfs.idc.v3_00.ParseData3;
import at.o2xfs.xfs.idc.v3_00.RetainCard3;
import at.o2xfs.xfs.idc.v3_00.Setkey3;
import at.o2xfs.xfs.idc.v3_00.TrackEvent3;
import at.o2xfs.xfs.idc.v3_00.WriteTrack3;

public enum IdcFactory {

	INSTANCE;

	private <T> T doCreate(XfsVersion xfsVersion, Pointer p, Class<T> type) {
		Object result;
		if (Capabilities3.class.equals(type)) {
			result = new Capabilities3(new Capabilities3(p));
		} else if (CardAction3.class.equals(type)) {
			result = new CardAction3(new CardAction3(p));
		} else if (CardData3.class.equals(type)) {
			result = new CardData3(new CardData3(p));
		} else if (ChipIo3.class.equals(type)) {
			result = new ChipIo3(new ChipIo3(p));
		} else if (ChipPowerOut3.class.equals(type)) {
			result = new ChipPowerOut3(new ChipPowerOut3(p));
		} else if (Form3.class.equals(type)) {
			result = new Form3(new Form3(p));
		} else if (IdcStatus3.class.equals(type)) {
			result = new IdcStatus3(new IdcStatus3(p));
		} else if (ParseData3.class.equals(type)) {
			result = new ParseData3(new ParseData3(p));
		} else if (RetainCard3.class.equals(type)) {
			result = new RetainCard3(new RetainCard3(p));
		} else if (Setkey3.class.equals(type)) {
			result = new Setkey3(new Setkey3(p));
		} else if (TrackEvent3.class.equals(type)) {
			result = new TrackEvent3(new TrackEvent3(p));
		} else if (WriteTrack3.class.equals(type)) {
			result = new WriteTrack3(new WriteTrack3(p));
		} else {
			throw new IllegalArgumentException(type.toString());
		}
		return type.cast(result);
	}

	public static <T> T create(XfsVersion xfsVersion, Pointer p, Class<T> type) {
		return INSTANCE.doCreate(xfsVersion, p, type);
	}
}
