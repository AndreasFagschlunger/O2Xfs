package at.o2xfs.win32;

public final class Bits {

	private Bits() {
		throw new AssertionError();
	}

	public static char getChar(byte[] b) {
		return getChar(b, 0);
	}

	public static char getChar(byte[] b, int off) {
		return (char) ((b[off + 1] << 8) | (b[off] & 0xff));
	}

	public static byte[] toByteArray(char val) {
		byte[] result = new byte[2];
		putChar(result, val);
		return result;
	}

	public static void putChar(byte[] b, char val) {
		putChar(b, 0, val);
	}

	public static void putChar(byte[] b, int off, char val) {
		b[off] = (byte) val;
		b[off + 1] = (byte) (val >> 8);
	}

	public static short getShort(byte[] b) {
		return getShort(b, 0);
	}

	public static short getShort(byte[] b, int off) {
		return (short) ((b[off + 1] << 8) | (b[off] & 0xff));
	}

	public static byte[] toByteArray(short val) {
		byte[] result = new byte[2];
		putShort(result, val);
		return result;
	}

	public static void putShort(byte[] b, short val) {
		putShort(b, 0, val);
	}

	public static void putShort(byte[] b, int off, short val) {
		b[off] = (byte) val;
		b[off + 1] = (byte) (val >> 8);
	}

	public static int getInt(byte[] b) {
		return getInt(b, 0);
	}

	public static int getInt(byte[] b, int off) {
		return b[off + 3] << 24 | ((b[off + 2] & 0xff) << 16) | ((b[off + 1] & 0xff) << 8) | (b[off] & 0xff);
	}

	public static byte[] toByteArray(int val) {
		byte[] result = new byte[4];
		putInt(result, val);
		return result;
	}

	public static void putInt(byte[] b, int val) {
		putInt(b, 0, val);
	}

	public static void putInt(byte[] b, int off, int val) {
		b[off] = (byte) val;
		b[off + 1] = (byte) (val >> 8);
		b[off + 2] = (byte) (val >> 16);
		b[off + 3] = (byte) (val >> 24);
	}

	public static long getLong(byte[] b) {
		return getLong(b, 0);
	}

	public static long getLong(byte[] b, int off) {
		return ((long) b[off + 7] << 56) | ((long) (b[off + 6] & 0xff) << 48) | ((long) (b[off + 5] & 0xff) << 40)
				| ((long) (b[off + 4] & 0xff) << 32) | ((long) (b[off + 3] & 0xff) << 24)
				| ((long) (b[off + 2] & 0xff) << 16) | ((long) (b[off + 1] & 0xff) << 8) | b[off] & 0xff;
	}

	public static byte[] toByteArray(long val) {
		byte[] result = new byte[8];
		putLong(result, val);
		return result;
	}

	public static void putLong(byte[] b, long val) {
		putLong(b, 0, val);
	}

	public static void putLong(byte[] b, int off, long val) {
		b[off] = (byte) val;
		b[off + 1] = (byte) (val >> 8);
		b[off + 2] = (byte) (val >> 16);
		b[off + 3] = (byte) (val >> 24);
		b[off + 4] = (byte) (val >> 32);
		b[off + 5] = (byte) (val >> 40);
		b[off + 6] = (byte) (val >> 48);
		b[off + 7] = (byte) (val >> 56);
	}
}
