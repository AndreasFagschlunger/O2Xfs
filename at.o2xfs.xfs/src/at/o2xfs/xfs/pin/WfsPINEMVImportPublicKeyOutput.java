package at.o2xfs.xfs.pin;

import org.apache.commons.lang.builder.ToStringBuilder;

import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.ZSTR;

public final class WfsPINEMVImportPublicKeyOutput extends Struct {

	private LPSTR expiryDate = new LPSTR();

	private WfsPINEMVImportPublicKeyOutput() {
		add(expiryDate);
	}

	public WfsPINEMVImportPublicKeyOutput(Pointer p) {
		this();
		assignBuffer(p);
	}

	public WfsPINEMVImportPublicKeyOutput(WfsPINEMVImportPublicKeyOutput copy) {
		this();
		allocate();
		setExpiryDate(copy.getExpiryDate());
	}

	public String getExpiryDate() {
		return expiryDate.toString();
	}

	public void setExpiryDate(String expiryDate) {
		if (expiryDate == null) {
			this.expiryDate.pointTo(Pointer.NULL);
		} else {
			this.expiryDate.pointTo(new ZSTR(expiryDate));
		}
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("expiryDate", getExpiryDate())
				.toString();
	}
}
