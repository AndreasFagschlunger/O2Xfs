package at.o2xfs.xfs.pin;

import at.o2xfs.xfs.XfsConstant;

public enum PinMode implements XfsConstant {

	ENCRYPT(1L),
	DECRYPT(2L),
	RANDOM(3L);

	private final long value;

	private PinMode(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}
