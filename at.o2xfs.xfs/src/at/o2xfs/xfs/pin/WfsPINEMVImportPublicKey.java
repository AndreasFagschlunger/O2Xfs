package at.o2xfs.xfs.pin;

import org.apache.commons.lang.builder.ToStringBuilder;

import at.o2xfs.win32.DWORD;
import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.WORD;
import at.o2xfs.xfs.util.XfsConstants;

public final class WfsPINEMVImportPublicKey extends Struct {

	public static final class WfsPINEMVImportPublicKeyBuilder {

		private String key = null;

		private PINUse use = null;

		private PINEMVImportScheme importScheme = null;

		private byte[] importData = null;

		private String sigKey = null;

		public WfsPINEMVImportPublicKeyBuilder key(String key) {
			this.key = key;
			return this;
		}

		public WfsPINEMVImportPublicKeyBuilder use(PINUse use) {
			this.use = use;
			return this;
		}

		public WfsPINEMVImportPublicKeyBuilder importScheme(
				PINEMVImportScheme importScheme) {
			this.importScheme = importScheme;
			return this;
		}

		public WfsPINEMVImportPublicKeyBuilder importData(byte[] importData) {
			this.importData = importData;
			return this;
		}

		public WfsPINEMVImportPublicKeyBuilder sigKey(String sigKey) {
			this.sigKey = sigKey;
			return this;
		}

		public WfsPINEMVImportPublicKey build() {
			WfsPINEMVImportPublicKey importPublicKey = new WfsPINEMVImportPublicKey();
			importPublicKey.allocate();
			importPublicKey.setKey(key);
			importPublicKey.setUse(use);
			importPublicKey.setImportScheme(importScheme);
			if (importData != null) {
				importPublicKey.setImportData(new WfsXData(importData));
			}
			importPublicKey.setSigKey(sigKey);
			return importPublicKey;
		}
	}

	private LPSTR key = new LPSTR();
	private DWORD use = new DWORD();
	private WORD importScheme = new WORD();
	private Pointer importData = new Pointer();
	private LPSTR sigKey = new LPSTR();

	private WfsPINEMVImportPublicKey() {
		add(key);
		add(use);
		add(importScheme);
		add(importData);
		add(sigKey);
	}

	public WfsPINEMVImportPublicKey(WfsPINEMVImportPublicKey importPublicKey) {
		this();
		allocate();
		setKey(importPublicKey.getKey());
		use.put(importPublicKey.use);
		importScheme.put(importPublicKey.importScheme);
		setImportData(importPublicKey.getImportData());
		setSigKey(importPublicKey.getSigKey());
	}

	public void setKey(String key) {
		this.key.pointTo(key);
	}

	public String getKey() {
		return key.toString();
	}

	public void setUse(PINUse use) {
		this.use.put(use.getValue());
	}

	public PINUse getUse() {
		return XfsConstants.valueOf(use, PINUse.class);
	}

	public void setImportScheme(PINEMVImportScheme importScheme) {
		this.importScheme.put(importScheme.getValue());
	}

	public PINEMVImportScheme getImportScheme() {
		return XfsConstants.valueOf(importScheme, PINEMVImportScheme.class);
	}

	public void setImportData(WfsXData importData) {
		this.importData.pointTo(importData);
	}

	public WfsXData getImportData() {
		if (Pointer.NULL.equals(importData)) {
			return null;
		}
		return new WfsXData(importData);
	}

	public void setSigKey(String sigKey) {
		this.sigKey.pointTo(sigKey);
	}

	public String getSigKey() {
		return sigKey.toString();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("key", getKey())
				.append("use", getUse())
				.append("importScheme", getImportScheme())
				.append("importData", getImportData())
				.append("sigKey", getSigKey()).toString();
	}
}
