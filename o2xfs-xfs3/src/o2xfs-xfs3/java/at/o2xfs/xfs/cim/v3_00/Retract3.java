package at.o2xfs.xfs.cim.v3_00;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.cim.OutputPosition;
import at.o2xfs.xfs.cim.RetractArea;
import at.o2xfs.xfs.win32.XfsWord;

public class Retract3 extends Struct {

	protected final XfsWord<OutputPosition> outputPosition = new XfsWord<>(OutputPosition.class);
	protected final XfsWord<RetractArea> retractArea = new XfsWord<>(RetractArea.class);
	protected final USHORT index = new USHORT();

	protected Retract3() {
		add(outputPosition);
		add(retractArea);
		add(index);
	}

	public Retract3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public Retract3(Retract3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(Retract3 copy) {
		outputPosition.set(copy.getOutputPosition());
		retractArea.set(copy.getRetractArea());
		index.set(copy.getIndex());
	}

	public OutputPosition getOutputPosition() {
		return outputPosition.get();
	}

	public RetractArea getRetractArea() {
		return retractArea.get();
	}

	public int getIndex() {
		return index.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getOutputPosition()).append(getRetractArea()).append(getIndex()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Retract3) {
			Retract3 retract3 = (Retract3) obj;
			return new EqualsBuilder().append(getOutputPosition(), retract3.getOutputPosition()).append(getRetractArea(), retract3.getRetractArea())
					.append(getIndex(), retract3.getIndex()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("outputPosition", getOutputPosition()).append("retractArea", getRetractArea()).append("index", getIndex()).toString();
	}
}