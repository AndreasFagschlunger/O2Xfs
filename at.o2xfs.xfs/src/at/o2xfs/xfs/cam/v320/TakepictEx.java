package at.o2xfs.xfs.cam.v320;

import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.cam.Camera;
import at.o2xfs.xfs.cam.Takepict;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class TakepictEx
		extends Takepict {

	private final LPSTR pictureFile = new LPSTR();

	private TakepictEx() {
		add(pictureFile);
	}

	public TakepictEx(Pointer p) {
		this();
		assignBuffer(p);
	}

	public TakepictEx(Camera aCamera, String aCamData, String aUnicodeCamData, String aPictureFile) {
		this();
		allocate();
		camera.set(aCamera);
		camData.set(aCamData);
		unicodeCamData.set(aUnicodeCamData);
		pictureFile.set(aPictureFile);
	}

	public TakepictEx(TakepictEx copy) {
		this(copy.getCamera(), copy.getCamData(), copy.getUnicodeCamData(), copy.getPictureFile());
	}

	public String getPictureFile() {
		return pictureFile.toString();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).appendSuper(super.toString())
										.append("pictureFile", getPictureFile())
										.toString();
	}
}