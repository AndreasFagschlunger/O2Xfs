package at.o2xfs.win32;

import at.o2xfs.common.Bytes;

import java.nio.charset.StandardCharsets;

public final class WString
		extends BaseType {

	private WString(byte[] bytes) {
		super(bytes.length);
		allocate();
		put(bytes);
	}

	public static final WString valueOf(String s) {
		byte[] bytes = s.getBytes(StandardCharsets.UTF_16LE);
		return new WString(Bytes.rightPad(bytes, bytes.length + 2));
	}
}