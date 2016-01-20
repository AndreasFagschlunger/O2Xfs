package at.o2xfs.xfs.cam;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.USHORTArray;
import at.o2xfs.xfs.XfsExtra;
import at.o2xfs.xfs.XfsStruct;
import at.o2xfs.xfs.XfsWord;
import at.o2xfs.xfs.XfsWordArray;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class CamStatus
		extends XfsStruct {

	protected final XfsWord<CamDeviceState> device = new XfsWord<CamDeviceState>(CamDeviceState.class);
	protected final XfsWordArray<Media> mediaStates = new XfsWordArray<Media>(Media.class, CamConstant.CAMERAS_SIZE);
	protected final XfsWordArray<CameraState> cameraStates = new XfsWordArray<CameraState>(	CameraState.class,
																							CamConstant.CAMERAS_SIZE);
	protected final USHORTArray pictures = new USHORTArray(CamConstant.CAMERAS_SIZE);
	protected final XfsExtra extra = new XfsExtra();

	protected CamStatus() {
		add(device);
		add(mediaStates);
		add(cameraStates);
		add(pictures);
		add(extra);
	}

	public CamStatus(Pointer p) {
		assignBuffer(p);
	}

	public CamStatus(CamStatus copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(CamStatus status) {
		device.set(status.device);
		mediaStates.set(status.mediaStates);
		cameraStates.set(status.cameraStates);
		pictures.set(status.pictures);
		extra.set(status.extra);
	}

	public CamDeviceState getDevice() {
		return device.get();
	}

	public Media[] getMediaStates() {
		return mediaStates.get();
	}

	public CameraState[] getCameraStates() {
		return cameraStates.get();
	}

	public int[] getPictures() {
		return pictures.get();
	}

	public Map<String, String> getExtra() {
		return extra.get();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("device", getDevice())
										.append("mediaStates", getMediaStates())
										.append("cameraStates", getCameraStates())
										.append("pictures", getPictures())
										.append("extra", getExtra())
										.toString();
	}
}
