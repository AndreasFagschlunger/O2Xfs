package at.o2xfs.xfs.idc;

import at.o2xfs.xfs.XfsConstant;

public enum IdcExecuteCommand implements XfsConstant {

	/*
	 * @since v3.00
	 */
	READ_TRACK(201L),

	/*
	 * @since v3.00
	 */
	WRITE_TRACK(202L),

	/*
	 * @since v3.00
	 */
	EJECT_CARD(203L),

	/*
	 * @since v3.00
	 */
	RETAIN_CARD(204L),

	/*
	 * @since v3.00
	 */
	RESET_COUNT(205L),

	/*
	 * @since v3.00
	 */
	SETKEY(206L),

	/*
	 * @since v3.00
	 */
	READ_RAW_DATA(207L),

	/*
	 * @since v3.00
	 */
	WRITE_RAW_DATA(208L),

	/*
	 * @since v3.00
	 */
	CHIP_IO(209L),

	/*
	 * @since v3.00
	 */
	RESET(210L),

	/*
	 * @since v3.00
	 */
	CHIP_POWER(211L),

	/*
	 * @since v3.00
	 */
	PARSE_DATA(212L),

	/*
	 * @since v3.10
	 */
	SET_GUIDANCE_LIGHT(213L),

	/*
	 * @since v3.10
	 */
	POWER_SAVE_CONTROL(214L),

	/*
	 * @since v3.20
	 */
	PARK_CARD(215L),

	/*
	 * @since v3.30
	 */
	EMVCLESS_CONFIGURE(216L),

	/*
	 * @since v3.30
	 */
	EMVCLESS_PERFORM_TRANSACTION(217L),

	/*
	 * @since v3.30
	 */
	EMVCLESS_ISSUERUPDATE(218L),

	/*
	 * @since v3.30
	 */
	SYNCHRONIZE_COMMAND(219L);

	private final long value;

	private IdcExecuteCommand(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}