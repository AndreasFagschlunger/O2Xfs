package at.o2xfs.xfs.cdm.v3_00;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;

public class Denominate3 extends Struct {

	protected final USHORT tellerID = new USHORT();
	protected final USHORT mixNumber = new USHORT();
	protected final Pointer denomination = new Pointer();

	protected Denominate3() {
		add(tellerID);
		add(mixNumber);
		add(denomination);
	}

	public Denominate3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public Denominate3(Denominate3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(Denominate3 copy) {
		tellerID.set(copy.getTellerID());
		mixNumber.set(copy.getMixNumber());
		denomination.pointTo(new Denomination3(copy.getDenomination()));
	}

	public int getTellerID() {
		return tellerID.get();
	}

	public int getMixNumber() {
		return mixNumber.get();
	}

	public Denomination3 getDenomination() {
		return new Denomination3(denomination);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getTellerID()).append(getMixNumber()).append(getDenomination()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Denominate3) {
			Denominate3 denominate = (Denominate3) obj;
			return new EqualsBuilder().append(getTellerID(), denominate.getTellerID()).append(getMixNumber(), denominate.getMixNumber())
					.append(getDenomination(), denominate.getDenomination()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("tellerID", getTellerID()).append("mixNumber", getMixNumber()).append("denomination", getDenomination()).toString();
	}
}