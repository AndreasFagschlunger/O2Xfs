package at.o2xfs.xfs.cim;

import at.o2xfs.xfs.XfsConstant;
import at.o2xfs.xfs.XfsDeviceState;

public enum CimDeviceState implements XfsConstant {

	/*
	 * @since v3.00
	 */
	ONLINE(XfsDeviceState.ONLINE.getValue()),

	/*
	 * @since v3.00
	 */
	OFFLINE(XfsDeviceState.OFFLINE.getValue()),

	/*
	 * @since v3.00
	 */
	POWEROFF(XfsDeviceState.POWEROFF.getValue()),

	/*
	 * @since v3.00
	 */
	NODEVICE(XfsDeviceState.NODEVICE.getValue()),

	/*
	 * @since v3.00
	 */
	USERERROR(XfsDeviceState.USERERROR.getValue()),

	/*
	 * @since v3.00
	 */
	HWERROR(XfsDeviceState.HWERROR.getValue()),

	/*
	 * @since v3.00
	 */
	BUSY(XfsDeviceState.BUSY.getValue()),

	/*
	 * @since v3.10
	 */
	FRAUDATTEMPT(XfsDeviceState.FRAUDATTEMPT.getValue()),

	/*
	 * @since v3.20
	 */
	POTENTIALFRAUD(XfsDeviceState.POTENTIALFRAUD.getValue());

	private final long value;

	private CimDeviceState(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}