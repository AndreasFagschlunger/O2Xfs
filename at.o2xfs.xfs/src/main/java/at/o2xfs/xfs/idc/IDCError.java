package at.o2xfs.xfs.idc;

import at.o2xfs.xfs.XfsConstant;

public enum IdcError implements XfsConstant {

	/*
	 * @since v3.00
	 */
	MEDIAJAM(-200L),

	/*
	 * @since v3.00
	 */
	NOMEDIA(-201L),

	/*
	 * @since v3.00
	 */
	MEDIARETAINED(-202L),

	/*
	 * @since v3.00
	 */
	RETAINBINFULL(-203L),

	/*
	 * @since v3.00
	 */
	INVALIDDATA(-204L),

	/*
	 * @since v3.00
	 */
	INVALIDMEDIA(-205L),

	/*
	 * @since v3.00
	 */
	FORMNOTFOUND(-206L),

	/*
	 * @since v3.00
	 */
	FORMINVALID(-207L),

	/*
	 * @since v3.00
	 */
	DATASYNTAX(-208L),

	/*
	 * @since v3.00
	 */
	SHUTTERFAIL(-209L),

	/*
	 * @since v3.00
	 */
	SECURITYFAIL(-210L),

	/*
	 * @since v3.00
	 */
	PROTOCOLNOTSUPP(-211L),

	/*
	 * @since v3.00
	 */
	ATRNOTOBTAINED(-212L),

	/*
	 * @since v3.00
	 */
	INVALIDKEY(-213L),

	/*
	 * @since v3.00
	 */
	WRITE_METHOD(-214L),

	/*
	 * @since v3.00
	 */
	CHIPPOWERNOTSUPP(-215L),

	/*
	 * @since v3.00
	 */
	CARDTOOSHORT(-216L),

	/*
	 * @since v3.00
	 */
	CARDTOOLONG(-217L),

	/*
	 * @since v3.10
	 */
	INVALID_PORT(-218L),

	/*
	 * @since v3.10
	 */
	POWERSAVETOOSHORT(-219L),

	/*
	 * @since v3.10
	 */
	POWERSAVEMEDIAPRESENT(-220L),

	/*
	 * @since v3.20
	 */
	CARDPRESENT(-221L),

	/*
	 * @since v3.20
	 */
	POSITIONINVALID(-222L),

	/*
	 * @since v3.30
	 */
	INVALIDTERMINALDATA(-223L),

	/*
	 * @since v3.30
	 */
	INVALIDAIDDATA(-224L),

	/*
	 * @since v3.30
	 */
	INVALIDKEYDATA(-225L),

	/*
	 * @since v3.30
	 */
	READERNOTCONFIGURED(-226L),

	/*
	 * @since v3.30
	 */
	TRANSACTIONNOTINITIATED(-227L),

	/*
	 * @since v3.30
	 */
	COMMANDUNSUPP(-228L),

	/*
	 * @since v3.30
	 */
	SYNCHRONIZEUNSUPP(-229L);

	private final long value;

	private IdcError(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}