package at.o2xfs.xfs;

public class XfsVersionRange {

	private final XfsVersion minVersion;

	private final XfsVersion maxVersion;

	public XfsVersionRange(XfsVersion minVersion, XfsVersion maxVersion) {
		if (minVersion != null && maxVersion != null) {
			if (minVersion.isGE(maxVersion)) {
				throw new IllegalArgumentException(minVersion
						+ " must be lower than " + maxVersion);
			}
		}
		this.minVersion = minVersion;
		this.maxVersion = maxVersion;
	}

	public XfsVersion getMinVersion() {
		return minVersion;
	}

	public XfsVersion getMaxVersion() {
		return maxVersion;
	}

	public boolean contains(XfsVersion version) {
		if (minVersion != null && minVersion.compareTo(version) > 0) {
			return false;
		} else if (maxVersion != null && maxVersion.compareTo(version) < 0) {
			return false;
		}
		return true;
	}
}