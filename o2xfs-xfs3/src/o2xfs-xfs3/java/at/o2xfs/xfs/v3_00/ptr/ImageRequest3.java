/*
 * Copyright (c) 2018, Andreas Fagschlunger. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package at.o2xfs.xfs.v3_00.ptr;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.xfs.ptr.BackImageColorFormat;
import at.o2xfs.xfs.ptr.BackImageFormat;
import at.o2xfs.xfs.ptr.CodelineFormat;
import at.o2xfs.xfs.ptr.ImageSource;
import at.o2xfs.xfs.win32.XfsWord;

public class ImageRequest3 extends Struct {

	protected final XfsWord<BackImageFormat> frontImageType = new XfsWord<>(BackImageFormat.class);
	protected final XfsWord<BackImageFormat> backImageType = new XfsWord<>(BackImageFormat.class);
	protected final XfsWord<BackImageColorFormat> frontImageColorFormat = new XfsWord<>(BackImageColorFormat.class);
	protected final XfsWord<BackImageColorFormat> backImageColorFormat = new XfsWord<>(BackImageColorFormat.class);
	protected final XfsWord<CodelineFormat> codelineFormat = new XfsWord<>(CodelineFormat.class);
	protected final XfsWord<ImageSource> imageSource = new XfsWord<>(ImageSource.class);
	protected final LPSTR frontImageFile = new LPSTR();
	protected final LPSTR backImageFile = new LPSTR();

	protected ImageRequest3() {
		add(frontImageType);
		add(backImageType);
		add(frontImageColorFormat);
		add(backImageColorFormat);
		add(codelineFormat);
		add(imageSource);
		add(frontImageFile);
		add(backImageFile);
	}

	public ImageRequest3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public ImageRequest3(ImageRequest3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(ImageRequest3 copy) {
		frontImageType.set(copy.getFrontImageType());
		backImageType.set(copy.getBackImageType());
		frontImageColorFormat.set(copy.getFrontImageColorFormat());
		backImageColorFormat.set(copy.getBackImageColorFormat());
		codelineFormat.set(copy.getCodelineFormat());
		imageSource.set(copy.getImageSource());
		frontImageFile.set(copy.getFrontImageFile());
		backImageFile.set(copy.getBackImageFile());
	}

	public BackImageFormat getFrontImageType() {
		return frontImageType.get();
	}

	public BackImageFormat getBackImageType() {
		return backImageType.get();
	}

	public BackImageColorFormat getFrontImageColorFormat() {
		return frontImageColorFormat.get();
	}

	public BackImageColorFormat getBackImageColorFormat() {
		return backImageColorFormat.get();
	}

	public CodelineFormat getCodelineFormat() {
		return codelineFormat.get();
	}

	public ImageSource getImageSource() {
		return imageSource.get();
	}

	public String getFrontImageFile() {
		return frontImageFile.get();
	}

	public String getBackImageFile() {
		return backImageFile.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(getFrontImageType())
				.append(getBackImageType())
				.append(getFrontImageColorFormat())
				.append(getBackImageColorFormat())
				.append(getCodelineFormat())
				.append(getImageSource())
				.append(getFrontImageFile())
				.append(getBackImageFile())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ImageRequest3) {
			ImageRequest3 imageRequest3 = (ImageRequest3) obj;
			return new EqualsBuilder()
					.append(getFrontImageType(), imageRequest3.getFrontImageType())
					.append(getBackImageType(), imageRequest3.getBackImageType())
					.append(getFrontImageColorFormat(), imageRequest3.getFrontImageColorFormat())
					.append(getBackImageColorFormat(), imageRequest3.getBackImageColorFormat())
					.append(getCodelineFormat(), imageRequest3.getCodelineFormat())
					.append(getImageSource(), imageRequest3.getImageSource())
					.append(getFrontImageFile(), imageRequest3.getFrontImageFile())
					.append(getBackImageFile(), imageRequest3.getBackImageFile())
					.isEquals();
		}
		return false;
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
				.append("backImageFile", getBackImageFile())
				.toString();
	}
}
