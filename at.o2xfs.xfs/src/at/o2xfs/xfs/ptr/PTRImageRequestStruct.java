/*
 * Copyright (c) 2014, Andreas Fagschlunger. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * - Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package at.o2xfs.xfs.ptr;

import at.o2xfs.win32.LPSTR;
import at.o2xfs.xfs.XfsStruct;
import at.o2xfs.xfs.XfsVersion;
import at.o2xfs.xfs.XfsWord;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class PTRImageRequestStruct
		extends XfsStruct {

	private final XfsWord<PTRImageType> frontImageType = new XfsWord<PTRImageType>(PTRImageType.class);
	private final XfsWord<PTRImageType> backImageType = new XfsWord<PTRImageType>(PTRImageType.class);
	private final XfsWord<PTRImageColor> frontImageColorFormat = new XfsWord<PTRImageColor>(PTRImageColor.class);
	private final XfsWord<PTRImageColor> backImageColorFormat = new XfsWord<PTRImageColor>(PTRImageColor.class);
	private final XfsWord<PTRCodelineFormat> codelineFormat = new XfsWord<PTRCodelineFormat>(PTRCodelineFormat.class);
	private final XfsWord<PTRImageSource> imageSource = new XfsWord<PTRImageSource>(PTRImageSource.class);
	private final LPSTR frontImageFile = new LPSTR();
	private final LPSTR backImageFile = new LPSTR();

	public PTRImageRequestStruct(XfsVersion version) {
		add(frontImageType);
		add(backImageType);
		add(frontImageColorFormat);
		add(backImageColorFormat);
		add(codelineFormat);
		add(imageSource);
		add(frontImageFile);
		add(backImageFile);
	}

	public PTRImageType getFrontImageType() {
		return frontImageType.get();
	}

	public void setFrontImageType(PTRImageType frontImageType) {
		this.frontImageType.set(frontImageType);
	}

	public PTRImageType getBackImageType() {
		return backImageType.get();
	}

	public void setBackImageType(PTRImageType backImageType) {
		this.backImageType.set(backImageType);
	}

	public PTRImageColor getFrontImageColorFormat() {
		return frontImageColorFormat.get();
	}

	public void setFrontImageColorFormat(PTRImageColor frontImageColorFormat) {
		this.frontImageColorFormat.set(frontImageColorFormat);
	}

	public PTRImageColor getBackImageColorFormat() {
		return backImageColorFormat.get();
	}

	public void setBackImageColorFormat(PTRImageColor backImageColorFormat) {
		this.backImageColorFormat.set(backImageColorFormat);
	}

	public PTRCodelineFormat getCodelineFormat() {
		return codelineFormat.get();
	}

	public void setCodelineFormat(PTRCodelineFormat codelineFormat) {
		this.codelineFormat.set(codelineFormat);
	}

	public PTRImageSource getImageSource() {
		return imageSource.get();
	}

	public void setImageSource(PTRImageSource imageSource) {
		this.imageSource.set(imageSource);
	}

	public String getFrontImageFile() {
		return frontImageFile.toString();
	}

	public void setFrontImageFile(String frontImageFile) {
		this.frontImageFile.put(frontImageFile);
	}

	public String getBackImageFile() {
		return backImageFile.toString();
	}

	public void setBackImageFile(String backImageFile) {
		this.backImageFile.put(backImageFile);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("frontImageType", getFrontImageType())
										.append("backImageType", getBackImageType())
										.append("frontImageColorFormat", getFrontImageColorFormat())
										.append("backImageColorFormat", getBackImageColorFormat())
										.append("codelineFormat", getCodelineFormat())
										.append("imageSource", getImageSource())
										.append("frontImageFile", getFrontImageFile())
										.append("backImageFile", getBackImageFile())
										.toString();
	}
}