package at.o2xfs.xfs;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;

public class WfsAppDisconnect extends Struct {

	protected final LPSTR logicalName = new LPSTR();
	protected final LPSTR workstationName = new LPSTR();
	protected final LPSTR appId = new LPSTR();

	protected WfsAppDisconnect() {
		add(logicalName);
		add(workstationName);
		add(appId);
	}

	public WfsAppDisconnect(Pointer p) {
		this();
		assignBuffer(p);
	}

	public WfsAppDisconnect(WfsAppDisconnect copy) {
		this();
		allocate();
		logicalName.set(copy.getLogicalName());
		workstationName.set(copy.getWorkstationName());
		appId.set(copy.getAppId());
	}

	public String getLogicalName() {
		return logicalName.get();
	}

	public String getWorkstationName() {
		return workstationName.get();
	}

	public String getAppId() {
		return appId.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(getLogicalName())
				.append(getWorkstationName())
				.append(getAppId())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof WfsAppDisconnect) {
			WfsAppDisconnect appDisconnect = (WfsAppDisconnect) obj;
			return new EqualsBuilder()
					.append(getLogicalName(), appDisconnect.getLogicalName())
					.append(getWorkstationName(), appDisconnect.getWorkstationName())
					.append(getAppId(), appDisconnect.getAppId())
					.isEquals();
		}
		return result;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("logicalName", getLogicalName())
				.append("workstationName", getWorkstationName())
				.append("appId", getAppId())
				.toString();
	}
}
