package at.o2xfs.xfs;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.ByteArray;
import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.win32.XfsDWord;

public class WfsHardwareError3 extends WfsHardwareError2 {

	protected final LPSTR physicalName = new LPSTR();
	protected final XfsDWord<XfsErrorAction> action = new XfsDWord<>(XfsErrorAction.class);

	private WfsHardwareError3() {
		add(logicalName);
		add(physicalName);
		add(workstationName);
		add(appId);
		add(action);
		add(length);
		add(description);
	}

	public WfsHardwareError3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public WfsHardwareError3(WfsHardwareError3 copy) {
		this();
		allocate();
		logicalName.set(copy.getLogicalName());
		physicalName.set(copy.getPhysicalName());
		workstationName.set(copy.getWorkstationName());
		appId.set(copy.getAppId());
		action.set(copy.getAction());
		length.set(copy.length);
		description.pointTo(new ByteArray(copy.getDescription()));
	}

	public String getPhysicalName() {
		return physicalName.get();
	}

	public XfsErrorAction getAction() {
		return action.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(getLogicalName())
				.append(getPhysicalName())
				.append(getWorkstationName())
				.append(getAppId())
				.append(getAction())
				.append(getSize())
				.append(getDescription())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof WfsHardwareError3) {
			WfsHardwareError3 hwError = (WfsHardwareError3) obj;
			return new EqualsBuilder()
					.append(getLogicalName(), hwError.getLogicalName())
					.append(getPhysicalName(), hwError.getPhysicalName())
					.append(getWorkstationName(), hwError.getWorkstationName())
					.append(getAppId(), hwError.getAppId())
					.append(getAction(), hwError.getAction())
					.append(getSize(), hwError.getSize())
					.append(getDescription(), hwError.getDescription())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("logicalName", getLogicalName())
				.append("physicalName", getPhysicalName())
				.append("workstationName", getWorkstationName())
				.append("appId", getAppId())
				.append("action", getAction())
				.append("length", length.intValue())
				.append("description", getDescription())
				.toString();
	}
}
