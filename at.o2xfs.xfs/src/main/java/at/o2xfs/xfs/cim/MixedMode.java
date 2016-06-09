package at.o2xfs.xfs.cim;

import at.o2xfs.xfs.XfsConstant;

public enum MixedMode implements XfsConstant {

	/*
	 * @since v3.20
	 */
	MIXEDMEDIANOTSUPP(0L),

	/*
	 * @since v3.20
	 */
	IPMMIXEDMEDIA(1L);

	private final long value;

	private MixedMode(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}