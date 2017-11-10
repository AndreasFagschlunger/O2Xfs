package at.o2xfs.xfs.idc.v3_00;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.ByteArray;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.ULONG;

public class ChipPowerOut3 extends Struct {

	protected final ULONG chipDataLength = new ULONG();
	protected final Pointer chipData = new Pointer();

	protected ChipPowerOut3() {
		add(chipDataLength);
		add(chipData);
	}

	public ChipPowerOut3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public ChipPowerOut3(ChipPowerOut3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(ChipPowerOut3 copy) {
		chipDataLength.set(copy.chipDataLength);
		chipData.pointTo(new ByteArray(copy.getChipData()));
	}

	public byte[] getChipData() {
		return chipData.buffer(chipDataLength.intValue()).get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getChipData()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ChipPowerOut3) {
			ChipPowerOut3 chipPowerOut3 = (ChipPowerOut3) obj;
			return new EqualsBuilder().append(getChipData(), chipPowerOut3.getChipData()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("chipData", getChipData()).toString();
	}
}