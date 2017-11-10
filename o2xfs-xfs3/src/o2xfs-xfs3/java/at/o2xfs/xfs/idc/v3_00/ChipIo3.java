package at.o2xfs.xfs.idc.v3_00;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.ByteArray;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.ULONG;
import at.o2xfs.win32.WORD;

public class ChipIo3 extends Struct {

	protected final WORD chipProtocol = new WORD();
	protected final ULONG chipDataLength = new ULONG();
	protected final Pointer chipData = new Pointer();

	protected ChipIo3() {
		add(chipProtocol);
		add(chipDataLength);
		add(chipData);
	}

	public ChipIo3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public ChipIo3(ChipIo3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(ChipIo3 copy) {
		chipProtocol.set(copy.getChipProtocol());
		chipDataLength.set(copy.chipDataLength);
		chipData.pointTo(new ByteArray(copy.getChipData()));
	}

	public int getChipProtocol() {
		return chipProtocol.get();
	}

	public byte[] getChipData() {
		return chipData.buffer(chipDataLength.intValue()).get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getChipProtocol()).append(getChipData()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ChipIo3) {
			ChipIo3 chipIo3 = (ChipIo3) obj;
			return new EqualsBuilder()
					.append(getChipProtocol(), chipIo3.getChipProtocol())
					.append(getChipData(), chipIo3.getChipData())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("chipProtocol", getChipProtocol())
				.append("chipData", getChipData())
				.toString();
	}
}