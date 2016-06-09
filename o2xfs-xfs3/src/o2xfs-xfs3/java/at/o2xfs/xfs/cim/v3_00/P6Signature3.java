package at.o2xfs.xfs.cim.v3_00;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.ByteArray;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.ULONG;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.cim.Orientation;
import at.o2xfs.xfs.win32.XfsDWord;

public class P6Signature3 extends Struct {

	protected final USHORT noteId = new USHORT();
	protected final ULONG length = new ULONG();
	protected final XfsDWord<Orientation> orientation = new XfsDWord<>(Orientation.class);
	protected final Pointer signature = new Pointer();

	protected P6Signature3() {
		add(noteId);
		add(length);
		add(orientation);
		add(signature);
	}

	public P6Signature3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public P6Signature3(P6Signature3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(P6Signature3 copy) {
		noteId.set(copy.getNoteId());
		length.set(copy.getLength());
		orientation.set(copy.getOrientation());
		signature.pointTo(new ByteArray(copy.getSignature()));
	}

	public int getNoteId() {
		return noteId.get();
	}

	public long getLength() {
		return length.get();
	}

	public Orientation getOrientation() {
		return orientation.get();
	}

	public byte[] getSignature() {
		return signature.buffer(length.intValue()).get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getNoteId()).append(getLength()).append(getOrientation()).append(getSignature()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof P6Signature3) {
			P6Signature3 p6Signature3 = (P6Signature3) obj;
			return new EqualsBuilder().append(getNoteId(), p6Signature3.getNoteId()).append(getLength(), p6Signature3.getLength())
					.append(getOrientation(), p6Signature3.getOrientation()).append(getSignature(), p6Signature3.getSignature()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("noteId", getNoteId()).append("length", getLength()).append("orientation", getOrientation()).append("signature", getSignature())
				.toString();
	}
}