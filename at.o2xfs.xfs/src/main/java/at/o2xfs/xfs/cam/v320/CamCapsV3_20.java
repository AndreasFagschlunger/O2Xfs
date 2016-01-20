package at.o2xfs.xfs.cam.v320;

import at.o2xfs.win32.BOOL;
import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.cam.CamCaps;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class CamCapsV3_20
		extends CamCaps {

	private final BOOL pictureFile = new BOOL();
	private final BOOL antiFraudModule = new BOOL();

	private CamCapsV3_20() {
		add(pictureFile);
		add(antiFraudModule);
	}

	public CamCapsV3_20(Pointer p) {
		this();
		assignBuffer(p);
	}

	public CamCapsV3_20(CamCapsV3_20 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(CamCapsV3_20 caps) {
		super.set(caps);
		pictureFile.set(caps.isPictureFile());
		antiFraudModule.set(caps.isAntiFraudModule());
	}

	public boolean isPictureFile() {
		return pictureFile.booleanValue();
	}

	public boolean isAntiFraudModule() {
		return antiFraudModule.booleanValue();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).appendSuper(super.toString())
										.append("pictureFile", isPictureFile())
										.append("antiFraudModule", isAntiFraudModule())
										.toString();
	}
}