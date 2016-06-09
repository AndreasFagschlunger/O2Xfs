package at.o2xfs.xfs.cim;

import at.o2xfs.xfs.XfsConstant;

public enum CimMessage implements XfsConstant {

	/*
	 * @since v3.00
	 */
	SRVE_CIM_SAFEDOOROPEN(1301L),

	/*
	 * @since v3.00
	 */
	SRVE_CIM_SAFEDOORCLOSED(1302L),

	/*
	 * @since v3.00
	 */
	USRE_CIM_CASHUNITTHRESHOLD(1303L),

	/*
	 * @since v3.00
	 */
	SRVE_CIM_CASHUNITINFOCHANGED(1304L),

	/*
	 * @since v3.00
	 */
	SRVE_CIM_TELLERINFOCHANGED(1305L),

	/*
	 * @since v3.00
	 */
	EXEE_CIM_CASHUNITERROR(1306L),

	/*
	 * @since v3.00
	 */
	SRVE_CIM_ITEMSTAKEN(1307L),

	/*
	 * @since v3.00
	 */
	SRVE_CIM_COUNTS_CHANGED(1308L),

	/*
	 * @since v3.00
	 */
	EXEE_CIM_INPUTREFUSE(1309L),

	/*
	 * @since v3.00
	 */
	SRVE_CIM_ITEMSPRESENTED(1310L),

	/*
	 * @since v3.00
	 */
	SRVE_CIM_ITEMSINSERTED(1311L),

	/*
	 * @since v3.00
	 */
	EXEE_CIM_NOTEERROR(1312L),

	/*
	 * @since v3.00
	 */
	EXEE_CIM_SUBCASHIN(1313L),

	/*
	 * @since v3.00
	 */
	SRVE_CIM_MEDIADETECTED(1314L),

	/*
	 * @since v3.00
	 */
	EXEE_CIM_INPUT_P6(1315L),

	/*
	 * @since v3.10
	 */
	EXEE_CIM_INFO_AVAILABLE(1316L),

	/*
	 * @since v3.10
	 */
	EXEE_CIM_INSERTITEMS(1317L),

	/*
	 * @since v3.10
	 */
	SRVE_CIM_DEVICEPOSITION(1318L),

	/*
	 * @since v3.10
	 */
	SRVE_CIM_POWER_SAVE_CHANGE(1319L),

	/*
	 * @since v3.20
	 */
	EXEE_CIM_INCOMPLETEREPLENISH(1320L),

	/*
	 * @since v3.30
	 */
	EXEE_CIM_INCOMPLETEDEPLETE(1321L),

	/*
	 * @since v3.30
	 */
	SRVE_CIM_SHUTTERSTATUSCHANGED(1322L);

	private final long value;

	private CimMessage(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}