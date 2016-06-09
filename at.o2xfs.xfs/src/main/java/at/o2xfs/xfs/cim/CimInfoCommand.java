package at.o2xfs.xfs.cim;

import at.o2xfs.xfs.XfsConstant;

public enum CimInfoCommand implements XfsConstant {

	/*
	 * @since v3.00
	 */
	STATUS(1301L),

	/*
	 * @since v3.00
	 */
	CAPABILITIES(1302L),

	/*
	 * @since v3.00
	 */
	CASH_UNIT_INFO(1303L),

	/*
	 * @since v3.00
	 */
	TELLER_INFO(1304L),

	/*
	 * @since v3.00
	 */
	CURRENCY_EXP(1305L),

	/*
	 * @since v3.00
	 */
	BANKNOTE_TYPES(1306L),

	/*
	 * @since v3.00
	 */
	CASH_IN_STATUS(1307L),

	/*
	 * @since v3.00
	 */
	GET_P6_INFO(1308L),

	/*
	 * @since v3.00
	 */
	GET_P6_SIGNATURE(1309L),

	/*
	 * @since v3.10
	 */
	GET_ITEM_INFO(1310L),

	/*
	 * @since v3.10
	 */
	POSITION_CAPABILITIES(1311L),

	/*
	 * @since v3.20
	 */
	REPLENISH_TARGET(1312L),

	/*
	 * @since v3.20
	 */
	DEVICELOCK_STATUS(1313L),

	/*
	 * @since v3.20
	 */
	CASH_UNIT_CAPABILITIES(1314L),

	/*
	 * @since v3.30
	 */
	DEPLETE_SOURCE(1315L),

	/*
	 * @since v3.30
	 */
	GET_ALL_ITEMS_INFO(1316L),

	/*
	 * @since v3.30
	 */
	GET_BLACKLIST(1317L);

	private final long value;

	private CimInfoCommand(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}