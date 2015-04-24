package at.o2xfs.xfs.cam;

import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.LPWSTR;
import at.o2xfs.win32.Struct;
import at.o2xfs.xfs.XfsWord;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Takepict
		extends Struct {

	protected final XfsWord<Camera> camera = new XfsWord<>(Camera.class);
	protected final LPSTR camData = new LPSTR();
	protected final LPWSTR unicodeCamData = new LPWSTR();

	protected Takepict() {
		add(camera);
		add(camData);
		add(unicodeCamData);
	}

	public Takepict(Camera aCamera, String aCamData, String aUnicodeCamData) {
		this();
		allocate();
		camera.set(aCamera);
		camData.set(aCamData);
		unicodeCamData.set(aUnicodeCamData);
	}

	public Takepict(Takepict copy) {
		this(copy.getCamera(), copy.getCamData(), copy.getUnicodeCamData());
	}

	public Camera getCamera() {
		return camera.get();
	}

	public String getCamData() {
		return camData.toString();
	}

	public String getUnicodeCamData() {
		return unicodeCamData.get();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("camera", getCamera())
										.append("camData", getCamData())
										.append("unicodeCamData", getUnicodeCamData())
										.toString();
	}
}