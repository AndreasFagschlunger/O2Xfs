package at.o2xfs.xfs.idc;

import at.o2xfs.xfs.XfsConstant;

public enum MemoryChipProtocols implements XfsConstant {

	/*
	 * @since v3.10
	 */
	SIEMENS4442(0x0001),

	/*
	 * @since v3.10
	 */
	GPM896(0x0002);

	private final long value;

	private MemoryChipProtocols(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}