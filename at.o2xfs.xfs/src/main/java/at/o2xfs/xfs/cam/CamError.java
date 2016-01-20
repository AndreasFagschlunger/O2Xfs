package at.o2xfs.xfs.cam;

import at.o2xfs.xfs.XfsConstant;

public enum CamError implements XfsConstant {

	/**
	 * @since v2.00
	 */
	CAMNOTSUPP(-1000L),

	/**
	 * @since v2.00
	 */
	MEDIAFULL(-1001L),

	/**
	 * @since v2.00
	 */
	CAMINOP(-1002L),

	/**
	 * @since v3.00
	 */
	CHARSETNOTSUPP(-1003L);

	private final long value;

	private CamError(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}