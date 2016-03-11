package at.o2xfs.xfs.cdm.v3_30;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.xfs.cdm.Level;
import at.o2xfs.xfs.win32.XfsWord;

public class GetAllItemsInfo3_30 extends Struct {

	protected final XfsWord<Level> level = new XfsWord<>(Level.class);

	protected GetAllItemsInfo3_30() {
		add(level);
	}

	public GetAllItemsInfo3_30(Pointer p) {
		this();
		assignBuffer(p);
	}

	public GetAllItemsInfo3_30(GetAllItemsInfo3_30 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(GetAllItemsInfo3_30 copy) {
		level.set(copy.getLevel());
	}

	public Level getLevel() {
		return level.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getLevel()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GetAllItemsInfo3_30) {
			GetAllItemsInfo3_30 getAllItemsInfo = (GetAllItemsInfo3_30) obj;
			return new EqualsBuilder().append(getLevel(), getAllItemsInfo.getLevel()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("level", getLevel()).toString();
	}
}