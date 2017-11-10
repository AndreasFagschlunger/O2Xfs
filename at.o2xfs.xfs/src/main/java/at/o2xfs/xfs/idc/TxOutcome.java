package at.o2xfs.xfs.idc;

import at.o2xfs.xfs.XfsConstant;

public enum TxOutcome implements XfsConstant {

	/*
	 * @since v3.30
	 */
	MULTIPLECARDS(0L),

	/*
	 * @since v3.30
	 */
	APPROVE(1L),

	/*
	 * @since v3.30
	 */
	DECLINE(2L),

	/*
	 * @since v3.30
	 */
	ONLINEREQUEST(3L),

	/*
	 * @since v3.30
	 */
	ONLINEREQUESTCOMPLETIONREQUIRED(4L),

	/*
	 * @since v3.30
	 */
	TRYAGAIN(5L),

	/*
	 * @since v3.30
	 */
	TRYANOTHERINTERFACE(6L),

	/*
	 * @since v3.30
	 */
	ENDAPPLICATION(7L),

	/*
	 * @since v3.30
	 */
	CONFIRMATIONREQUIRED(8L);

	private final long value;

	private TxOutcome(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}