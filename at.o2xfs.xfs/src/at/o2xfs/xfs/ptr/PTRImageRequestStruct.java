package at.o2xfs.xfs.ptr;

import org.apache.commons.lang.builder.ToStringBuilder;

import at.o2xfs.win32.LPSTR;
import at.o2xfs.xfs.XfsStruct;
import at.o2xfs.xfs.XfsVersion;
import at.o2xfs.xfs.XfsWord;

public class PTRImageRequestStruct extends XfsStruct {

	private final XfsWord<PTRImageType> frontImageType = new XfsWord<PTRImageType>(
			PTRImageType.class);
	private final XfsWord<PTRImageType> backImageType = new XfsWord<PTRImageType>(
			PTRImageType.class);
	private final XfsWord<PTRImageColor> frontImageColorFormat = new XfsWord<PTRImageColor>(
			PTRImageColor.class);
	private final XfsWord<PTRImageColor> backImageColorFormat = new XfsWord<PTRImageColor>(
			PTRImageColor.class);
	private final XfsWord<PTRCodelineFormat> codelineFormat = new XfsWord<PTRCodelineFormat>(
			PTRCodelineFormat.class);
	private final XfsWord<PTRImageSource> imageSource = new XfsWord<PTRImageSource>(
			PTRImageSource.class);
	private final LPSTR frontImageFile = new LPSTR();
	private final LPSTR backImageFile = new LPSTR();

	public PTRImageRequestStruct(XfsVersion version) {
		new XfsStructInit(version).add(frontImageType).add(backImageType)
				.add(frontImageColorFormat).add(backImageColorFormat)
				.add(codelineFormat).add(imageSource).add(frontImageFile)
				.add(backImageFile).init(this);
	}

	public PTRImageType getFrontImageType() {
		return frontImageType.enumValue();
	}

	public void setFrontImageType(PTRImageType frontImageType) {
		this.frontImageType.set(frontImageType);
	}

	public PTRImageType getBackImageType() {
		return backImageType.enumValue();
	}

	public void setBackImageType(PTRImageType backImageType) {
		this.backImageType.set(backImageType);
	}

	public PTRImageColor getFrontImageColorFormat() {
		return frontImageColorFormat.enumValue();
	}

	public void setFrontImageColorFormat(PTRImageColor frontImageColorFormat) {
		this.frontImageColorFormat.set(frontImageColorFormat);
	}

	public PTRImageColor getBackImageColorFormat() {
		return backImageColorFormat.enumValue();
	}

	public void setBackImageColorFormat(PTRImageColor backImageColorFormat) {
		this.backImageColorFormat.set(backImageColorFormat);
	}

	public PTRCodelineFormat getCodelineFormat() {
		return codelineFormat.enumValue();
	}

	public void setCodelineFormat(PTRCodelineFormat codelineFormat) {
		this.codelineFormat.set(codelineFormat);
	}

	public PTRImageSource getImageSource() {
		return imageSource.enumValue();
	}

	public void setImageSource(PTRImageSource imageSource) {
		this.imageSource.set(imageSource);
	}

	public String getFrontImageFile() {
		return frontImageFile.toString();
	}

	public void setFrontImageFile(String frontImageFile) {
		this.frontImageFile.pointTo(frontImageFile);
	}

	public String getBackImageFile() {
		return backImageFile.toString();
	}

	public void setBackImageFile(String backImageFile) {
		this.backImageFile.pointTo(backImageFile);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("frontImageType", getFrontImageType())
				.append("backImageType", getBackImageType())
				.append("frontImageColorFormat", getFrontImageColorFormat())
				.append("backImageColorFormat", getBackImageColorFormat())
				.append("codelineFormat", getCodelineFormat())
				.append("imageSource", getImageSource())
				.append("frontImageFile", getFrontImageFile())
				.append("backImageFile", getBackImageFile()).toString();
	}
}
