package at.o2xfs.xfs.cdm.v3_30;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.ULONG;
import at.o2xfs.xfs.win32.XfsData;

public class Signature3_30 extends Struct {

	protected final ULONG length = new ULONG();
	protected final Pointer data = new Pointer();

	protected Signature3_30() {
		add(length);
		add(data);
	}

	public Signature3_30(Pointer p) {
		this();
		assignBuffer(p);
	}

	public Signature3_30(Signature3_30 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(Signature3_30 copy) {
		length.set(copy.getLength());
		data.pointTo(new XfsData(copy.getData()));
	}

	public long getLength() {
		return length.get();
	}

	public byte[] getData() {
		return new XfsData(data, (int) getLength()).get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getLength()).append(getData()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Signature3_30) {
			Signature3_30 signature3_30 = (Signature3_30) obj;
			return new EqualsBuilder().append(getLength(), signature3_30.getLength()).append(getData(), signature3_30.getData()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("length", getLength()).append("data", getData()).toString();
	}
}