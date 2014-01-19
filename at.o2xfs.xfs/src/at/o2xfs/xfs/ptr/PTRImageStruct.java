package at.o2xfs.xfs.ptr;

import org.apache.commons.lang.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.XfsData;
import at.o2xfs.xfs.XfsStruct;
import at.o2xfs.xfs.XfsVersion;
import at.o2xfs.xfs.XfsWord;

public class PTRImageStruct extends XfsStruct {

	private final XfsWord<PTRImageType> imageType = new XfsWord<PTRImageType>(
			PTRImageType.class);
	private final XfsWord<PTRImageSource> imageSource = new XfsWord<PTRImageSource>(
			PTRImageSource.class);
	private final XfsWord<PTRData> status = new XfsWord<PTRData>(PTRData.class);
	private final XfsData data = new XfsData();

	private PTRImageStruct(XfsVersion version) {
		new XfsStructInit(version).addUntil(imageType, XfsVersion.V2_00)
				.addSince(imageSource, XfsVersion.V3_00)
				.addSince(status, XfsVersion.V3_00).add(data).init(this);
	}

	public PTRImageStruct(XfsVersion version, Pointer p) {
		this(version);
		assignBuffer(p);
	}

	public PTRImageStruct(XfsVersion version, PTRImageStruct copy) {
		this(version);
		imageType.set(copy.getImageType());
		imageSource.set(copy.getImageSource());
		status.set(copy.getStatus());
		data.set(copy.get());
	}

	public PTRImageType getImageType() {
		return imageType.enumValue();
	}

	public PTRImageSource getImageSource() {
		return imageSource.enumValue();
	}

	public PTRData getStatus() {
		return status.enumValue();
	}

	public byte[] getData() {
		return data.getData();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("imageType", getImageType())
				.append("imageSource", getImageSource())
				.append("status", getStatus()).append("data", getData())
				.toString();
	}
}