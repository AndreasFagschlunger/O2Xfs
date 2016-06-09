package at.o2xfs.xfs.cim;

import at.o2xfs.xfs.XfsConstant;

public enum ItemInfoType implements XfsConstant {

	/*
	 * @since v3.10
	 */
	SERIALNUMBER(0x00000001),

	/*
	 * @since v3.10
	 */
	SIGNATURE(0x00000002),

	/*
	 * @since v3.30
	 */
	IMAGEFILE(0x00000004);

	private final long value;

	private ItemInfoType(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}