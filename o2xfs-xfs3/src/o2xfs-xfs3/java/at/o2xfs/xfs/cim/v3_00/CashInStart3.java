package at.o2xfs.xfs.cim.v3_00;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.BOOL;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.cim.InputPosition;
import at.o2xfs.xfs.cim.OutputPosition;
import at.o2xfs.xfs.win32.XfsWord;

public class CashInStart3 extends Struct {

	protected final USHORT tellerID = new USHORT();
	protected final BOOL useRecycleUnits = new BOOL();
	protected final XfsWord<OutputPosition> outputPosition = new XfsWord<>(OutputPosition.class);
	protected final XfsWord<InputPosition> inputPosition = new XfsWord<>(InputPosition.class);

	protected CashInStart3() {
		add(tellerID);
		add(useRecycleUnits);
		add(outputPosition);
		add(inputPosition);
	}

	public CashInStart3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public CashInStart3(CashInStart3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(CashInStart3 copy) {
		tellerID.set(copy.getTellerID());
		useRecycleUnits.set(copy.isUseRecycleUnits());
		outputPosition.set(copy.getOutputPosition());
		inputPosition.set(copy.getInputPosition());
	}

	public int getTellerID() {
		return tellerID.get();
	}

	public boolean isUseRecycleUnits() {
		return useRecycleUnits.get();
	}

	public OutputPosition getOutputPosition() {
		return outputPosition.get();
	}

	public InputPosition getInputPosition() {
		return inputPosition.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getTellerID()).append(isUseRecycleUnits()).append(getOutputPosition()).append(getInputPosition()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CashInStart3) {
			CashInStart3 cashInStart3 = (CashInStart3) obj;
			return new EqualsBuilder().append(getTellerID(), cashInStart3.getTellerID()).append(isUseRecycleUnits(), cashInStart3.isUseRecycleUnits())
					.append(getOutputPosition(), cashInStart3.getOutputPosition()).append(getInputPosition(), cashInStart3.getInputPosition()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("tellerID", getTellerID()).append("useRecycleUnits", isUseRecycleUnits()).append("outputPosition", getOutputPosition())
				.append("inputPosition", getInputPosition()).toString();
	}
}