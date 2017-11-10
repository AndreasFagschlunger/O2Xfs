package at.o2xfs.xfs.idc.v3_00;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.idc.Position;
import at.o2xfs.xfs.win32.XfsWord;

public class RetainCard3 extends Struct {

	protected final USHORT count = new USHORT();
	protected final XfsWord<Position> position = new XfsWord<>(Position.class);

	protected RetainCard3() {
		add(count);
		add(position);
	}

	public RetainCard3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public RetainCard3(RetainCard3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(RetainCard3 copy) {
		count.set(copy.getCount());
		position.set(copy.getPosition());
	}

	public int getCount() {
		return count.get();
	}

	public Position getPosition() {
		return position.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getCount()).append(getPosition()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RetainCard3) {
			RetainCard3 retainCard3 = (RetainCard3) obj;
			return new EqualsBuilder()
					.append(getCount(), retainCard3.getCount())
					.append(getPosition(), retainCard3.getPosition())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("count", getCount()).append("position", getPosition()).toString();
	}
}