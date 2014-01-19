package at.o2xfs.xfs;

import at.o2xfs.win32.ByteArray;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.ULONG;

public class XfsData extends Struct {

	private final ULONG dataLength = new ULONG();

	private final Pointer data = new Pointer();

	public XfsData() {
		add(dataLength).add(data);
	}

	public int getDataLength() {
		return dataLength.intValue();
	}

	public void set(byte[] value) {
		if (value == null) {
			data.pointTo(Pointer.NULL);
			dataLength.put(0);
		} else {
			dataLength.put(value.length);
			data.pointTo(new ByteArray(value));
		}
	}

	public byte[] getData() {
		byte[] result = null;
		if (!Pointer.NULL.equals(data)) {
			result = data.buffer((int) getDataLength()).get();
		}
		return result;
	}
}