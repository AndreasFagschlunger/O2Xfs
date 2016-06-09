package at.o2xfs.xfs.cim.v3_00;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.cim.InputPosition;
import at.o2xfs.xfs.cim.OutputPosition;
import at.o2xfs.xfs.win32.XfsWord;

public class TellerDetails3 extends Struct {

	protected final USHORT tellerID = new USHORT();
	protected final XfsWord<InputPosition> inputPosition = new XfsWord<>(InputPosition.class);
	protected final XfsWord<OutputPosition> outputPosition = new XfsWord<>(OutputPosition.class);
	protected final Pointer tellerTotals = new Pointer();

	protected TellerDetails3() {
		add(tellerID);
		add(inputPosition);
		add(outputPosition);
		add(tellerTotals);
	}

	public TellerDetails3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public TellerDetails3(TellerDetails3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(TellerDetails3 copy) {
		tellerID.set(copy.getTellerID());
		inputPosition.set(copy.getInputPosition());
		outputPosition.set(copy.getOutputPosition());
		tellerTotals.pointTo(new TellerTotals3(copy.getTellerTotals()));
	}

	public int getTellerID() {
		return tellerID.get();
	}

	public InputPosition getInputPosition() {
		return inputPosition.get();
	}

	public OutputPosition getOutputPosition() {
		return outputPosition.get();
	}

	public TellerTotals3 getTellerTotals() {
		return new TellerTotals3(tellerTotals);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getTellerID()).append(getInputPosition()).append(getOutputPosition()).append(getTellerTotals()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TellerDetails3) {
			TellerDetails3 tellerDetails3 = (TellerDetails3) obj;
			return new EqualsBuilder().append(getTellerID(), tellerDetails3.getTellerID()).append(getInputPosition(), tellerDetails3.getInputPosition())
					.append(getOutputPosition(), tellerDetails3.getOutputPosition()).append(getTellerTotals(), tellerDetails3.getTellerTotals()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("tellerID", getTellerID()).append("inputPosition", getInputPosition()).append("outputPosition", getOutputPosition())
				.append("tellerTotals", getTellerTotals()).toString();
	}
}