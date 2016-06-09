package at.o2xfs.xfs.cim;

import at.o2xfs.xfs.XfsConstant;

public enum Reason implements XfsConstant {

	/*
	 * @since v3.00
	 */
	CASHINUNITFULL(1L),

	/*
	 * @since v3.00
	 */
	INVALIDBILL(2L),

	/*
	 * @since v3.00
	 */
	NOBILLSTODEPOSIT(3L),

	/*
	 * @since v3.00
	 */
	DEPOSITFAILURE(4L),

	/*
	 * @since v3.00
	 */
	COMMINPCOMPFAILURE(5L),

	/*
	 * @since v3.00
	 */
	STACKERFULL(6L),

	/*
	 * @since v3.00
	 */
	DOUBLENOTEDETECTED(1L),

	/*
	 * @since v3.00
	 */
	LONGNOTEDETECTED(2L),

	/*
	 * @since v3.00
	 */
	SKEWEDNOTE(3L),

	/*
	 * @since v3.00
	 */
	INCORRECTCOUNT(4L),

	/*
	 * @since v3.00
	 */
	NOTESTOOCLOSE(5L),

	/*
	 * @since v3.10
	 */
	FOREIGN_ITEMS_DETECTED(7L),

	/*
	 * @since v3.10
	 */
	INVALIDBUNCH(8L),

	/*
	 * @since v3.10
	 */
	COUNTERFEIT(9L),

	/*
	 * @since v3.10
	 */
	OTHERNOTEERROR(6L),

	/*
	 * @since v3.10
	 */
	SHORTNOTEDETECTED(7L),

	/*
	 * @since v3.20
	 */
	LIMITOVERTOTALITEMS(10L),

	/*
	 * @since v3.20
	 */
	LIMITOVERAMOUNT(11L);

	private final long value;

	private Reason(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}