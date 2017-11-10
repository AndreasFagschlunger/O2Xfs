package at.o2xfs.xfs.idc.v3_00;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.xfs.idc.WriteMethod;
import at.o2xfs.xfs.win32.XfsWord;

public class WriteTrack3 extends Struct {

	protected final LPSTR formName = new LPSTR();
	protected final LPSTR trackData = new LPSTR();
	protected final XfsWord<WriteMethod> writeMethod = new XfsWord<>(WriteMethod.class);

	protected WriteTrack3() {
		add(formName);
		add(trackData);
		add(writeMethod);
	}

	public WriteTrack3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public WriteTrack3(WriteTrack3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(WriteTrack3 copy) {
		formName.set(copy.getFormName());
		trackData.set(copy.getTrackData());
		writeMethod.set(copy.getWriteMethod());
	}

	public String getFormName() {
		return formName.get();
	}

	public String getTrackData() {
		return trackData.get();
	}

	public WriteMethod getWriteMethod() {
		return writeMethod.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getFormName()).append(getTrackData()).append(getWriteMethod()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof WriteTrack3) {
			WriteTrack3 writeTrack3 = (WriteTrack3) obj;
			return new EqualsBuilder()
					.append(getFormName(), writeTrack3.getFormName())
					.append(getTrackData(), writeTrack3.getTrackData())
					.append(getWriteMethod(), writeTrack3.getWriteMethod())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("formName", getFormName())
				.append("trackData", getTrackData())
				.append("writeMethod", getWriteMethod())
				.toString();
	}
}