package at.o2xfs.xfs.cdm.v3_30;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.cdm.ItemInfoType;
import at.o2xfs.xfs.cdm.Level;
import at.o2xfs.xfs.win32.XfsDWord;
import at.o2xfs.xfs.win32.XfsWord;

public class GetItemInfo3_30 extends Struct {

	protected final XfsWord<Level> level = new XfsWord<>(Level.class);
	protected final USHORT index = new USHORT();
	protected final XfsDWord<ItemInfoType> itemInfoType = new XfsDWord<>(ItemInfoType.class);

	protected GetItemInfo3_30() {
		add(level);
		add(index);
		add(itemInfoType);
	}

	public GetItemInfo3_30(Level level, int index, ItemInfoType itemInfoType) {
		this();
		allocate();
		this.level.set(level);
		this.index.set(index);
		this.itemInfoType.set(itemInfoType);
	}

	public GetItemInfo3_30(Pointer p) {
		this();
		assignBuffer(p);
	}

	public GetItemInfo3_30(GetItemInfo3_30 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(GetItemInfo3_30 copy) {
		level.set(copy.getLevel());
		index.set(copy.getIndex());
		itemInfoType.set(copy.getItemInfoType());
	}

	public Level getLevel() {
		return level.get();
	}

	public int getIndex() {
		return index.get();
	}

	public ItemInfoType getItemInfoType() {
		return itemInfoType.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getLevel()).append(getIndex()).append(getItemInfoType()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GetItemInfo3_30) {
			GetItemInfo3_30 getItemInfo = (GetItemInfo3_30) obj;
			return new EqualsBuilder().append(getLevel(), getItemInfo.getLevel()).append(getIndex(), getItemInfo.getIndex())
					.append(getItemInfoType(), getItemInfo.getItemInfoType()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("level", getLevel()).append("index", getIndex()).append("itemInfoType", getItemInfoType()).toString();
	}
}