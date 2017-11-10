package at.o2xfs.xfs.idc;

import at.o2xfs.xfs.XfsConstant;

public enum Data implements XfsConstant {

	/*
	 * @since v3.00
	 */
	READLEVEL1('1'),

	/*
	 * @since v3.00
	 */
	READLEVEL2('2'),

	/*
	 * @since v3.00
	 */
	READLEVEL3('3'),

	/*
	 * @since v3.00
	 */
	READLEVEL4('4'),

	/*
	 * @since v3.00
	 */
	READLEVEL5('5'),

	/*
	 * @since v3.00
	 */
	BADREADLEVEL('6'),

	/*
	 * @since v3.00
	 */
	NODATA('7'),

	/*
	 * @since v3.00
	 */
	DATAINVAL('8'),

	/*
	 * @since v3.00
	 */
	HWERROR('9'),

	/*
	 * @since v3.00
	 */
	NOINIT('A');

	private final long value;

	private Data(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}