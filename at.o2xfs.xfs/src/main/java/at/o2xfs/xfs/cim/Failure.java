package at.o2xfs.xfs.cim;

import at.o2xfs.xfs.XfsConstant;

public enum Failure implements XfsConstant {

	/*
	 * @since v3.00
	 */
	CASHUNITEMPTY(1L),

	/*
	 * @since v3.00
	 */
	CASHUNITERROR(2L),

	/*
	 * @since v3.00
	 */
	CASHUNITFULL(3L),

	/*
	 * @since v3.00
	 */
	CASHUNITLOCKED(4L),

	/*
	 * @since v3.00
	 */
	CASHUNITNOTCONF(5L),

	/*
	 * @since v3.00
	 */
	CASHUNITINVALID(6L),

	/*
	 * @since v3.00
	 */
	CASHUNITCONFIG(7L),

	/*
	 * @since v3.00
	 */
	FEEDMODULEPROBLEM(8L),

	/*
	 * @since v3.20
	 */
	CASHUNITPHYSICALLOCKED(9L),

	/*
	 * @since v3.20
	 */
	CASHUNITPHYSICALUNLOCKED(10L);

	private final long value;

	private Failure(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}