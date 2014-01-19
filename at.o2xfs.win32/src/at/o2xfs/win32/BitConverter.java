package at.o2xfs.win32;

/**
 * Converts base data types to an array of bytes, and an array of bytes to base
 * data types.
 */
public final class BitConverter {

	private BitConverter() {
		throw new AssertionError();
	}

	public static final byte[] getBytes(NumberType value) {
		return getBytes(value.getSize(), value.longValue());
	}

	public static final byte[] getBytes(int size, long value) {
		byte[] result = new byte[size];
		for (int i = 0; i < result.length; i++) {
			result[i] |= value;
			value >>>= 8;
		}
		return result;
	}

	public static final short toShort(byte[] src) {
		return (short) toLong(src);
	}

	public static final int toInt(byte[] src) {
		return (int) toLong(src);
	}

	public static final long toLong(byte[] src) {
		long result = 0L;
		for (int i = src.length - 1; i >= 0; i--) {
			result = (result << 8) | (src[i] & 0xFF);
		}
		return result;
	}
}
