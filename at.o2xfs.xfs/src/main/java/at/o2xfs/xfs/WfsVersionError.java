package at.o2xfs.xfs;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.common.Bytes;
import at.o2xfs.win32.ByteArray;
import at.o2xfs.win32.DWORD;
import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;

public class WfsVersionError extends Struct {

	protected final LPSTR logicalName = new LPSTR();
	protected final LPSTR workstationName = new LPSTR();
	protected final LPSTR appId = new LPSTR();
	protected final DWORD length = new DWORD();
	protected final Pointer description = new Pointer();
	protected final Pointer wfsVersion = new Pointer();

	protected WfsVersionError() {
		add(logicalName);
		add(workstationName);
		add(appId);
		add(length);
		add(description);
		add(wfsVersion);
	}

	public WfsVersionError(Pointer p) {
		this();
		assignBuffer(p);
	}

	public WfsVersionError(WfsVersionError copy) {
		this();
		allocate();
		logicalName.set(copy.getLogicalName());
		workstationName.set(copy.getWorkstationName());
		appId.set(copy.getAppId());
		length.set(copy.length);
		description.pointTo(new ByteArray(copy.getDescription()));
		wfsVersion.pointTo(new WFSVersion(copy.getWfsVersion()));
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

	public byte[] getDescription() {
		byte[] result = Bytes.EMPTY;
		if (length.intValue() > 0) {
			result = description.buffer(length.intValue()).get();
		}
		return result;
	}

	public WFSVersion getWfsVersion() {
		return new WFSVersion(wfsVersion);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(getLogicalName())
				.append(getWorkstationName())
				.append(getAppId())
				.append(getDescription())
				.append(getWfsVersion())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof WfsVersionError) {
			WfsVersionError versionError = (WfsVersionError) obj;
			return new EqualsBuilder()
					.append(getLogicalName(), versionError.getLogicalName())
					.append(getWorkstationName(), versionError.getWorkstationName())
					.append(getAppId(), versionError.getAppId())
					.append(getDescription(), versionError.getDescription())
					.append(getWfsVersion(), versionError.getWfsVersion())
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
				.append("description", getDescription())
				.append("wfsVersion", getWfsVersion())
				.toString();
	}
}
