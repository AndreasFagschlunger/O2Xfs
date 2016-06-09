package at.o2xfs.xfs.cim.v3_00;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.cim.OutputPosition;
import at.o2xfs.xfs.win32.XfsWord;

public class ItemPosition3 extends Struct {

	protected final USHORT number = new USHORT();
	protected final Pointer retractArea = new Pointer();
	protected final XfsWord<OutputPosition> outputPosition = new XfsWord<>(OutputPosition.class);

	protected ItemPosition3() {
		add(number);
		add(retractArea);
		add(outputPosition);
	}

	public ItemPosition3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public ItemPosition3(ItemPosition3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(ItemPosition3 copy) {
		number.set(copy.getNumber());
		retractArea.pointTo(new Retract3(copy.getRetractArea()));
		outputPosition.set(copy.getOutputPosition());
	}

	public int getNumber() {
		return number.get();
	}

	public Retract3 getRetractArea() {
		return new Retract3(retractArea);
	}

	public OutputPosition getOutputPosition() {
		return outputPosition.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getNumber()).append(getRetractArea()).append(getOutputPosition()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ItemPosition3) {
			ItemPosition3 itemPosition3 = (ItemPosition3) obj;
			return new EqualsBuilder().append(getNumber(), itemPosition3.getNumber()).append(getRetractArea(), itemPosition3.getRetractArea())
					.append(getOutputPosition(), itemPosition3.getOutputPosition()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("number", getNumber()).append("retractArea", getRetractArea()).append("outputPosition", getOutputPosition()).toString();
	}
}