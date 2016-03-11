package at.o2xfs.xfs.cdm.v3_30;

import java.util.Optional;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.LPWSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.ULONG;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.win32.XfsCharArray;

public class ItemInfo3_30 extends Struct {

	protected final XfsCharArray currencyID = new XfsCharArray(3);
	protected final ULONG value = new ULONG();
	protected final USHORT release = new USHORT();
	protected final LPWSTR serialNumber = new LPWSTR();
	protected final Pointer signature = new Pointer();
	protected final LPSTR imageFileName = new LPSTR();

	protected ItemInfo3_30() {
		add(currencyID);
		add(value);
		add(release);
		add(serialNumber);
		add(signature);
		add(imageFileName);
	}

	public ItemInfo3_30(Pointer p) {
		this();
		assignBuffer(p);
	}

	public ItemInfo3_30(ItemInfo3_30 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(ItemInfo3_30 copy) {
		currencyID.set(copy.getCurrencyID());
		value.set(copy.getValue());
		release.set(copy.getRelease());
		serialNumber.set(copy.getSerialNumber());
		Optional<Signature3_30> signature = copy.getSignature();
		if (signature.isPresent()) {
			this.signature.pointTo(new Signature3_30(signature.get()));
		}
		imageFileName.set(copy.getImageFileName());
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

	public Optional<Signature3_30> getSignature() {
		Optional<Signature3_30> result = Optional.empty();
		if (!Pointer.NULL.equals(signature)) {
			result = Optional.of(new Signature3_30(signature));
		}
		return result;
	}

	public String getImageFileName() {
		return imageFileName.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getCurrencyID()).append(getValue()).append(getRelease()).append(getSerialNumber()).append(getSignature()).append(getImageFileName())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ItemInfo3_30) {
			ItemInfo3_30 itemInfo = (ItemInfo3_30) obj;
			return new EqualsBuilder().append(getCurrencyID(), itemInfo.getCurrencyID()).append(getValue(), itemInfo.getValue()).append(getRelease(), itemInfo.getRelease())
					.append(getSerialNumber(), itemInfo.getSerialNumber()).append(getSignature(), itemInfo.getSignature()).append(getImageFileName(), itemInfo.getImageFileName())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("currencyID", getCurrencyID()).append("value", getValue()).append("release", getRelease()).append("serialNumber", getSerialNumber())
				.append("signature", getSignature()).append("imageFileName", getImageFileName()).toString();
	}
}