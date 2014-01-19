package at.o2xfs.xfs.ptr;

import at.o2xfs.xfs.XfsConstant;

public enum PTRData implements XfsConstant {

	/**
	 * The data is OK.
	 */
	DATAOK(0L),

	/**
	 * The data source to read from is not supported by the Service Provider.
	 */
	DATASRCNOTSUPP(1L),

	/**
	 * The data source to read from is missing, e.g. the Service Provider is
	 * unable to get the code line.
	 */
	DATASRCMISSING(2L);

	private final long value;

	private PTRData(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}
