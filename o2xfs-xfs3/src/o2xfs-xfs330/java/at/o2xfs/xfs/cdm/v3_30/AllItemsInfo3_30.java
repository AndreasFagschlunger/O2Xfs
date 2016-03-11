package at.o2xfs.xfs.cdm.v3_30;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;

public class AllItemsInfo3_30 extends Struct {

	protected final USHORT count = new USHORT();
	protected final Pointer itemsList = new Pointer();

	protected AllItemsInfo3_30() {
		add(count);
		add(itemsList);
	}

	public AllItemsInfo3_30(Pointer p) {
		this();
		assignBuffer(p);
	}

	public AllItemsInfo3_30(AllItemsInfo3_30 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(AllItemsInfo3_30 copy) {
		count.set(copy.getCount());
		itemsList.pointTo(new ItemInfoAllArray3_30(copy.getItemsList()));
	}

	public int getCount() {
		return count.get();
	}

	public ItemInfoAll3_30[] getItemsList() {
		return new ItemInfoAllArray3_30(itemsList, getCount()).get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getCount()).append(getItemsList()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AllItemsInfo3_30) {
			AllItemsInfo3_30 allItemsInfo = (AllItemsInfo3_30) obj;
			return new EqualsBuilder().append(getCount(), allItemsInfo.getCount()).append(getItemsList(), allItemsInfo.getItemsList()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("count", getCount()).append("itemsList", getItemsList()).toString();
	}
}