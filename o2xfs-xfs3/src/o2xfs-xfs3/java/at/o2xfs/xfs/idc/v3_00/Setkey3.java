package at.o2xfs.xfs.idc.v3_00;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.ByteArray;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;

public class Setkey3 extends Struct {

	protected final USHORT keyLen = new USHORT();
	protected final Pointer keyValue = new Pointer();

	protected Setkey3() {
		add(keyLen);
		add(keyValue);
	}

	public Setkey3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public Setkey3(Setkey3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(Setkey3 copy) {
		keyLen.set(copy.keyLen);
		keyValue.pointTo(new ByteArray(copy.getKeyValue()));
	}

	public byte[] getKeyValue() {
		return keyValue.buffer(keyLen.intValue()).get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getKeyValue()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Setkey3) {
			Setkey3 setkey3 = (Setkey3) obj;
			return new EqualsBuilder().append(getKeyValue(), setkey3.getKeyValue()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("keyValue", getKeyValue()).toString();
	}
}