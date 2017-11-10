package at.o2xfs.xfs.idc.v3_00;

import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.Struct;
import at.o2xfs.xfs.idc.DataStatus;
import at.o2xfs.xfs.win32.XfsWordBitmask;

public class TrackEvent3 extends Struct {

	protected final XfsWordBitmask<DataStatus> status = new XfsWordBitmask<>(DataStatus.class);
	protected final LPSTR track = new LPSTR();
	protected final LPSTR data = new LPSTR();

	protected TrackEvent3() {
		add(status);
		add(track);
		add(data);
	}

	public TrackEvent3(LPSTR p) {
		this();
		assignBuffer(p);
	}

	public TrackEvent3(TrackEvent3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(TrackEvent3 copy) {
		status.set(copy.getStatus());
		track.set(copy.getTrack());
		data.set(copy.getData());
	}

	public Set<DataStatus> getStatus() {
		return status.get();
	}

	public String getTrack() {
		return track.get();
	}

	public String getData() {
		return data.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getStatus()).append(getTrack()).append(getData()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TrackEvent3) {
			TrackEvent3 trackEvent3 = (TrackEvent3) obj;
			return new EqualsBuilder()
					.append(getStatus(), trackEvent3.getStatus())
					.append(getTrack(), trackEvent3.getTrack())
					.append(getData(), trackEvent3.getData())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("status", getStatus())
				.append("track", getTrack())
				.append("data", getData())
				.toString();
	}
}