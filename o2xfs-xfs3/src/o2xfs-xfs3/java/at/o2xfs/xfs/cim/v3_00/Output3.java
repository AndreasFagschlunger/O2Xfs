package at.o2xfs.xfs.cim.v3_00;

import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.cim.OutputPosition;
import at.o2xfs.xfs.win32.XfsWordBitmask;

public class Output3 extends Struct {

	protected final USHORT logicalNumber = new USHORT();
	protected final XfsWordBitmask<OutputPosition> position = new XfsWordBitmask<>(OutputPosition.class);
	protected final USHORT number = new USHORT();

	protected Output3() {
		add(logicalNumber);
		add(position);
		add(number);
	}

	public Output3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public Output3(Output3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(Output3 copy) {
		logicalNumber.set(copy.getLogicalNumber());
		position.set(copy.getPosition());
		number.set(copy.getNumber());
	}

	public int getLogicalNumber() {
		return logicalNumber.get();
	}

	public Set<OutputPosition> getPosition() {
		return position.get();
	}

	public int getNumber() {
		return number.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getLogicalNumber()).append(getPosition()).append(getNumber()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Output3) {
			Output3 output3 = (Output3) obj;
			return new EqualsBuilder().append(getLogicalNumber(), output3.getLogicalNumber()).append(getPosition(), output3.getPosition()).append(getNumber(), output3.getNumber())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("logicalNumber", getLogicalNumber()).append("position", getPosition()).append("number", getNumber()).toString();
	}
}