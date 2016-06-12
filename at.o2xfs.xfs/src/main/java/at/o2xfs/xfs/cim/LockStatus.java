package at.o2xfs.xfs.cim;

import at.o2xfs.xfs.XfsConstant;

public enum LockStatus implements XfsConstant {

	/*
	 * @since v3.20
	 */
	LOCK(1L),

	/*
	 * @since v3.20
	 */
	UNLOCK(2L),

	/*
	 * @since v3.20
	 */
	LOCKALL(3L),

	/*
	 * @since v3.20
	 */
	UNLOCKALL(4L),

	/*
	 * @since v3.20
	 */
	LOCKINDIVIDUAL(5L),

	/*
	 * @since v3.20
	 */
	NOLOCKACTION(6L),

	/*
	 * @since v3.20
	 */
	LOCKUNKNOWN(7L),

	/*
	 * @since v3.20
	 */
	LOCKNOTSUPPORTED(8L);

	private final long value;

	private LockStatus(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}