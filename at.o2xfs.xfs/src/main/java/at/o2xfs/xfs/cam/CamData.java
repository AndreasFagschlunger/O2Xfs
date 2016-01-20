package at.o2xfs.xfs.cam;

import at.o2xfs.xfs.XfsConstant;

public enum CamData implements XfsConstant {

	/**
	 * @since 3.00
	 */
	NOTADD(0L),

	/**
	 * @since 3.00
	 */
	AUTOADD(1L),

	/**
	 * @since 3.00
	 */
	MANADD(2L);

	private final long value;

	private CamData(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}
