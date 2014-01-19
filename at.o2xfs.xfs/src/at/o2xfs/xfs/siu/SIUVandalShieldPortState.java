package at.o2xfs.xfs.siu;

import at.o2xfs.xfs.XfsConstant;

public enum SIUVandalShieldPortState implements XfsConstant {

	/**
	 * Do not change the current position of the Vandal Shield.
	 */
	NO_CHANGE(0x0000L),

	/**
	 * The Vandal Shield is closed.
	 */
	CLOSED(0x0001L),

	/**
	 * The Vandal Shield is opened.
	 */
	OPEN(0x0002L),

	/**
	 * The Vandal Shield is set in service position.
	 */
	SERVICE(0x0010L),

	/**
	 * The Vandal Shield is set in position that permits access to the keyboard.
	 */
	KEYBOARD(0x0020L);

	private final long value;

	private SIUVandalShieldPortState(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}
