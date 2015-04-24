package at.o2xfs.xfs.cam;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.XfsBitmask;
import at.o2xfs.xfs.XfsExtra;
import at.o2xfs.xfs.XfsServiceClass;
import at.o2xfs.xfs.XfsWord;
import at.o2xfs.xfs.XfsWordArray;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class CamCaps
		extends Struct {

	private final XfsWord<XfsServiceClass> serviceClass = new XfsWord<XfsServiceClass>(XfsServiceClass.class);
	private final XfsWord<CamType> type = new XfsWord<CamType>(CamType.class);
	private final XfsWordArray<CameraAvailable> cameras = new XfsWordArray<>(	CameraAvailable.class,
																				CamConstant.CAMERAS_SIZE);
	private final USHORT maxPictures = new USHORT();
	private final XfsBitmask<CamData> camData = new XfsBitmask<CamData>(CamData.class);
	private final USHORT maxDataLength = new USHORT();
	private final XfsBitmask<CharSupport> charSupport = new XfsBitmask<CharSupport>(CharSupport.class);
	private final XfsExtra extra = new XfsExtra();

	protected CamCaps() {
		add(serviceClass);
		add(type);
		add(cameras);
		add(maxPictures);
		add(camData);
		add(maxDataLength);
		add(charSupport);
		add(extra);
	}

	public CamCaps(Pointer p) {
		this();
		assignBuffer(p);
	}

	public CamCaps(CamCaps copy) {
		this();
		set(copy);
	}

	protected void set(CamCaps caps) {
		serviceClass.set(caps.serviceClass);
		type.set(caps.type);
		cameras.set(caps.cameras);
		maxPictures.set(caps.maxPictures);
		camData.set(caps.camData);
		maxDataLength.set(caps.maxDataLength);
		charSupport.set(caps.charSupport);
		extra.set(caps.extra);
	}

	public XfsServiceClass getServiceClass() {
		return serviceClass.get();
	}

	public CamType getType() {
		return type.get();
	}

	public CameraAvailable[] getCameras() {
		return cameras.get();
	}

	public int getMaxPictures() {
		return maxPictures.intValue();
	}

	public Set<CamData> getCamData() {
		return camData.get();
	}

	public int getMaxDataLength() {
		return maxDataLength.intValue();
	}

	public Set<CharSupport> getCharSupport() {
		return charSupport.get();
	}

	public Map<String, String> getExtra() {
		return extra.get();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("serviceClass", getServiceClass())
										.append("type", getType())
										.append("cameras", getCameras())
										.append("maxPictures", getMaxPictures())
										.append("camData", getCamData())
										.append("maxDataLength", getMaxDataLength())
										.append("charSupport", getCharSupport())
										.append("extra", getExtra())
										.toString();
	}
}
