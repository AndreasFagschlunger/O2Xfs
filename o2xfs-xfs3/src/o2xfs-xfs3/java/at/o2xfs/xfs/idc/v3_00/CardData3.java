package at.o2xfs.xfs.idc.v3_00;

import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.ByteArray;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.ULONG;
import at.o2xfs.xfs.idc.DataStatus;
import at.o2xfs.xfs.idc.Track;
import at.o2xfs.xfs.idc.WriteMethod;
import at.o2xfs.xfs.win32.XfsWord;
import at.o2xfs.xfs.win32.XfsWordBitmask;

public class CardData3 extends Struct {

	protected final XfsWord<Track> dataSource = new XfsWord<>(Track.class);
	protected final XfsWordBitmask<DataStatus> status = new XfsWordBitmask<>(DataStatus.class);
	protected final ULONG dataLength = new ULONG();
	protected final Pointer data = new Pointer();
	protected final XfsWord<WriteMethod> writeMethod = new XfsWord<>(WriteMethod.class);

	protected CardData3() {
		add(dataSource);
		add(status);
		add(dataLength);
		add(data);
		add(writeMethod);
	}

	public CardData3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public CardData3(CardData3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(CardData3 copy) {
		dataSource.set(copy.getDataSource());
		status.set(copy.getStatus());
		dataLength.set(copy.dataLength);
		data.pointTo(new ByteArray(copy.getData()));
		writeMethod.set(copy.getWriteMethod());
	}

	public Track getDataSource() {
		return dataSource.get();
	}

	public Set<DataStatus> getStatus() {
		return status.get();
	}

	public byte[] getData() {
		return data.buffer(dataLength.intValue()).get();
	}

	public WriteMethod getWriteMethod() {
		return writeMethod.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(getDataSource())
				.append(getStatus())
				.append(getData())
				.append(getWriteMethod())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CardData3) {
			CardData3 cardData3 = (CardData3) obj;
			return new EqualsBuilder()
					.append(getDataSource(), cardData3.getDataSource())
					.append(getStatus(), cardData3.getStatus())
					.append(getData(), cardData3.getData())
					.append(getWriteMethod(), cardData3.getWriteMethod())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("dataSource", getDataSource())
				.append("status", getStatus())
				.append("data", getData())
				.append("writeMethod", getWriteMethod())
				.toString();
	}
}