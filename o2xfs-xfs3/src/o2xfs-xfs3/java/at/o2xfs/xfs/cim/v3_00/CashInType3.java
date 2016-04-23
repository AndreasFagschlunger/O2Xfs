package at.o2xfs.xfs.cim.v3_00;

import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.cim.CashInType;
import at.o2xfs.xfs.win32.XfsDWordBitmask;

public class CashInType3 extends Struct {

	protected final USHORT number = new USHORT();
	protected final XfsDWordBitmask<CashInType> type = new XfsDWordBitmask<>(CashInType.class);
	protected final Pointer noteIDs = new Pointer();

	protected CashInType3() {
		add(number);
		add(type);
		add(noteIDs);
	}

	public CashInType3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public CashInType3(CashInType3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(CashInType3 copy) {
		number.set(copy.getNumber());
		type.set(copy.getType());
		noteIDs.pointTo(new NoteIDs(copy.getNoteIDs()));
	}

	public int getNumber() {
		return number.get();
	}

	public Set<CashInType> getType() {
		return type.get();
	}

	public int[] getNoteIDs() {
		return new NoteIDs(noteIDs).get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getNumber()).append(getType()).append(getNoteIDs()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CashInType3) {
			CashInType3 cashInType3 = (CashInType3) obj;
			return new EqualsBuilder().append(getNumber(), cashInType3.getNumber()).append(getType(), cashInType3.getType()).append(getNoteIDs(), cashInType3.getNoteIDs())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("number", getNumber()).append("type", getType()).append("noteIDs", getNoteIDs()).toString();
	}
}