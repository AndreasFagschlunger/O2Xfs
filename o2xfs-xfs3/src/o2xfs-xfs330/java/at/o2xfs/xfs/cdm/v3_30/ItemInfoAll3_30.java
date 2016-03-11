package at.o2xfs.xfs.cdm.v3_30;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.LPWSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.ULONG;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.cdm.ItemLocation;
import at.o2xfs.xfs.cdm.OnBlacklist;
import at.o2xfs.xfs.win32.XfsCharArray;
import at.o2xfs.xfs.win32.XfsWord;

public class ItemInfoAll3_30 extends Struct {

	protected final USHORT level = new USHORT();
	protected final XfsCharArray currencyID = new XfsCharArray(3);
	protected final ULONG value = new ULONG();
	protected final USHORT release = new USHORT();
	protected final LPWSTR serialNumber = new LPWSTR();
	protected final LPSTR imageFileName = new LPSTR();
	protected final XfsWord<OnBlacklist> onBlacklist = new XfsWord<>(OnBlacklist.class);
	protected final XfsWord<ItemLocation> itemLocation = new XfsWord<>(ItemLocation.class);
	protected final USHORT number = new USHORT();

	protected ItemInfoAll3_30() {
		add(level);
		add(currencyID);
		add(value);
		add(release);
		add(serialNumber);
		add(imageFileName);
		add(onBlacklist);
		add(itemLocation);
		add(number);
	}

	public ItemInfoAll3_30(Pointer p) {
		this();
		assignBuffer(p);
	}

	public ItemInfoAll3_30(ItemInfoAll3_30 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(ItemInfoAll3_30 copy) {
		level.set(copy.getLevel());
		currencyID.set(copy.getCurrencyID());
		value.set(copy.getValue());
		release.set(copy.getRelease());
		serialNumber.set(copy.getSerialNumber());
		imageFileName.set(copy.getImageFileName());
		onBlacklist.set(copy.getOnBlacklist());
		itemLocation.set(copy.getItemLocation());
		number.set(copy.getNumber());
	}

	public int getLevel() {
		return level.get();
	}

	public char[] getCurrencyID() {
		return currencyID.get();
	}

	public long getValue() {
		return value.get();
	}

	public int getRelease() {
		return release.get();
	}

	public String getSerialNumber() {
		return serialNumber.get();
	}

	public String getImageFileName() {
		return imageFileName.get();
	}

	public OnBlacklist getOnBlacklist() {
		return onBlacklist.get();
	}

	public ItemLocation getItemLocation() {
		return itemLocation.get();
	}

	public int getNumber() {
		return number.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getLevel()).append(getCurrencyID()).append(getValue()).append(getRelease()).append(getSerialNumber()).append(getImageFileName())
				.append(getOnBlacklist()).append(getItemLocation()).append(getNumber()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ItemInfoAll3_30) {
			ItemInfoAll3_30 itemInfoAll = (ItemInfoAll3_30) obj;
			return new EqualsBuilder().append(getLevel(), itemInfoAll.getLevel()).append(getCurrencyID(), itemInfoAll.getCurrencyID()).append(getValue(), itemInfoAll.getValue())
					.append(getRelease(), itemInfoAll.getRelease()).append(getSerialNumber(), itemInfoAll.getSerialNumber())
					.append(getImageFileName(), itemInfoAll.getImageFileName()).append(getOnBlacklist(), itemInfoAll.getOnBlacklist())
					.append(getItemLocation(), itemInfoAll.getItemLocation()).append(getNumber(), itemInfoAll.getNumber()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("level", getLevel()).append("currencyID", getCurrencyID()).append("value", getValue()).append("release", getRelease())
				.append("serialNumber", getSerialNumber()).append("imageFileName", getImageFileName()).append("onBlacklist", getOnBlacklist())
				.append("itemLocation", getItemLocation()).append("number", getNumber()).toString();
	}
}