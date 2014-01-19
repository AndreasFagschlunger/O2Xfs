package at.o2xfs.xfs.siu;

import at.o2xfs.win32.WORDArray;

public final class AuxiliariesPortsBuilder extends AbstractPortsBuilder {

	protected AuxiliariesPortsBuilder(final WORDArray ports) {
		super(ports);
	}

	public void setVolumeControl(final int volumeLevel) {
		getPort(SIUAuxiliary.VOLUME).put(volumeLevel);
	}

	public void setUPS(final SIUUPSPortState state) {
		setPort(SIUAuxiliary.UPS, state);
	}

	public void setAudibleAlarm(final SIUAudibleAlarmPortState state) {
		setPort(SIUAuxiliary.AUDIBLE_ALARM, state);
	}

	public void setEnhancedAudioController(final SIUEnhancedAudioPortState state) {
		setPort(SIUAuxiliary.ENHANCEDAUDIOCONTROL, state);
	}
}
