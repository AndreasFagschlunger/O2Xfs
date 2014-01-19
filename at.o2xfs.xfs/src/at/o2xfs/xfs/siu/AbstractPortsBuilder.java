package at.o2xfs.xfs.siu;

import java.util.Map;

import at.o2xfs.win32.WORD;
import at.o2xfs.win32.WORDArray;
import at.o2xfs.xfs.XfsConstant;

class AbstractPortsBuilder {

	private final WORDArray ports;

	protected AbstractPortsBuilder(final WORDArray ports) {
		this.ports = ports;
	}

	protected <E extends Enum<E> & XfsConstant> void setPort(
			final SIUPortIndex index, final E state) {
		final WORD port = ports.get(index.intValue());
		port.put(state.getValue());
	}

	protected <E extends Enum<E> & XfsConstant> void setPorts(
			final SIUPortIndex index, final Map<E, Boolean> bitmap) {
		int value = 0;
		for (final Map.Entry<E, Boolean> entry : bitmap.entrySet()) {
			final boolean on = entry.getValue().booleanValue();
			if (on) {
				value |= entry.getKey().getValue();
			}
		}
		final WORD port = ports.get(index.intValue());
		port.put(value);
	}

	protected WORD getPort(final SIUPortIndex index) {
		return ports.get(index.intValue());
	}
}
