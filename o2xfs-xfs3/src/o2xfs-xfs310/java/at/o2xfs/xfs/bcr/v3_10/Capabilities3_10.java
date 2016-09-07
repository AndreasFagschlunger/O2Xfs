package at.o2xfs.xfs.bcr.v3_10;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.BOOL;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.WORD;
import at.o2xfs.xfs.XfsExtra;
import at.o2xfs.xfs.bcr.GuidLight;
import at.o2xfs.xfs.bcr.Symbologies;
import at.o2xfs.xfs.bcr.Symbology;
import at.o2xfs.xfs.win32.XfsBitmaskArray;

public class Capabilities3_10 extends Struct {

	private static final int GUIDLIGHTS_SIZE = 32;

	protected final WORD serviceClass = new WORD();
	protected final BOOL compound = new BOOL();
	protected final BOOL canFilterSymbologies = new BOOL();
	protected final Pointer symbologies = new Pointer();
	protected final XfsBitmaskArray<GuidLight> guidLights = new XfsBitmaskArray<>(GUIDLIGHTS_SIZE, GuidLight.class);
	protected final XfsExtra extra = new XfsExtra();
	protected final BOOL powerSaveControl = new BOOL();

	protected Capabilities3_10() {
		add(serviceClass);
		add(compound);
		add(canFilterSymbologies);
		add(symbologies);
		add(guidLights);
		add(extra);
		add(powerSaveControl);
	}

	public Capabilities3_10(Pointer p) {
		this();
		assignBuffer(p);
	}

	public Capabilities3_10(Capabilities3_10 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(Capabilities3_10 copy) {
		serviceClass.set(copy.getServiceClass());
		compound.set(copy.isCompound());
		canFilterSymbologies.set(copy.isCanFilterSymbologies());
		symbologies.pointTo(new Symbologies(copy.getSymbologies()));
		guidLights.set(copy.getGuidLights());
		extra.set(copy.getExtra());
		powerSaveControl.set(copy.isPowerSaveControl());
	}

	public int getServiceClass() {
		return serviceClass.get();
	}

	public boolean isCompound() {
		return compound.get();
	}

	public boolean isCanFilterSymbologies() {
		return canFilterSymbologies.get();
	}

	public Symbology[] getSymbologies() {
		return new Symbologies(symbologies).get();
	}

	public List<Set<GuidLight>> getGuidLights()

	{
		return guidLights.get();
	}

	public Map<String, String> getExtra() {
		return extra.get();
	}

	public boolean isPowerSaveControl() {
		return powerSaveControl.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getServiceClass()).append(isCompound()).append(isCanFilterSymbologies()).append(getSymbologies()).append(getGuidLights())
				.append(getExtra()).append(isPowerSaveControl()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Capabilities3_10) {
			Capabilities3_10 capabilities3_10 = (Capabilities3_10) obj;
			return new EqualsBuilder().append(getServiceClass(), capabilities3_10.getServiceClass()).append(isCompound(), capabilities3_10.isCompound())
					.append(isCanFilterSymbologies(), capabilities3_10.isCanFilterSymbologies()).append(getSymbologies(), capabilities3_10.getSymbologies())
					.append(getGuidLights(), capabilities3_10.getGuidLights()).append(getExtra(), capabilities3_10.getExtra())
					.append(isPowerSaveControl(), capabilities3_10.isPowerSaveControl()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("serviceClass", getServiceClass()).append("compound", isCompound()).append("canFilterSymbologies", isCanFilterSymbologies())
				.append("symbologies", getSymbologies()).append("guidLights", getGuidLights()).append("extra", getExtra()).append("powerSaveControl", isPowerSaveControl())
				.toString();
	}
}