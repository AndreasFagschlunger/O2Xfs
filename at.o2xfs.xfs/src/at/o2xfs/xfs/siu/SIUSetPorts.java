package at.o2xfs.xfs.siu;

import static at.o2xfs.xfs.siu.SIUConstant.AUXILIARIES_SIZE;
import static at.o2xfs.xfs.siu.SIUConstant.DOORS_SIZE;
import static at.o2xfs.xfs.siu.SIUConstant.GUIDLIGHTS_SIZE;
import static at.o2xfs.xfs.siu.SIUConstant.INDICATORS_SIZE;
import at.o2xfs.win32.LPZZSTR;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.WORDArray;

public class SIUSetPorts extends Struct {

	private final WORDArray doors = new WORDArray(DOORS_SIZE);
	private final WORDArray indicators = new WORDArray(INDICATORS_SIZE);
	private final WORDArray auxiliaries = new WORDArray(AUXILIARIES_SIZE);
	private final WORDArray guidLights = new WORDArray(GUIDLIGHTS_SIZE);
	private final LPZZSTR extra = new LPZZSTR();

	private SIUSetPorts() {
		add(doors);
		add(indicators);
		add(auxiliaries);
		add(guidLights);
		add(extra);
	}

	public DoorPortsBuilder setDoors() {
		return new DoorPortsBuilder(doors);
	}

	public IndicatorPortsBuilder setIndicators() {
		return new IndicatorPortsBuilder(indicators);
	}

	public AuxiliariesPortsBuilder setAuxiliaries() {
		return new AuxiliariesPortsBuilder(auxiliaries);
	}

	public GuidLightsPortsBuilder setGuidLights() {
		return new GuidLightsPortsBuilder(guidLights);
	}
}
