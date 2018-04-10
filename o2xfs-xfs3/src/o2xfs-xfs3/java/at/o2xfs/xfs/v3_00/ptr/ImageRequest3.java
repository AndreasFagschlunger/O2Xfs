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

import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.xfs.ptr.ImageColorFormat;
import at.o2xfs.xfs.ptr.ImageFormat;
import at.o2xfs.xfs.ptr.CodelineFormat;
import at.o2xfs.xfs.ptr.ImageSource;
import at.o2xfs.xfs.win32.XfsWord;
import at.o2xfs.xfs.win32.XfsWordBitmask;

public class ImageRequest3 extends Struct {

	protected final XfsWord<ImageFormat> frontImageType = new XfsWord<>(ImageFormat.class);
	protected final XfsWord<ImageFormat> backImageType = new XfsWord<>(ImageFormat.class);
	protected final XfsWord<ImageColorFormat> frontImageColorFormat = new XfsWord<>(ImageColorFormat.class);
	protected final XfsWord<ImageColorFormat> backImageColorFormat = new XfsWord<>(ImageColorFormat.class);
	protected final XfsWord<CodelineFormat> codelineFormat = new XfsWord<>(CodelineFormat.class);
	protected final XfsWordBitmask<ImageSource> imageSource = new XfsWordBitmask<>(ImageSource.class);
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
		Optional<ImageFormat> frontImageType = copy.getFrontImageType();
		if (frontImageType.isPresent()) {
			this.frontImageType.set(frontImageType.get());
		}
		Optional<ImageFormat> backImageType = copy.getBackImageType();
		if (backImageType.isPresent()) {
			this.backImageType.set(backImageType.get());
		}
		Optional<ImageColorFormat> frontImageColorFormat = copy.getFrontImageColorFormat();
		if (frontImageColorFormat.isPresent()) {
			this.frontImageColorFormat.set(frontImageColorFormat.get());
		}
		Optional<ImageColorFormat> backImageColorFormat = copy.getBackImageColorFormat();
		if (backImageColorFormat.isPresent()) {
			this.backImageColorFormat.set(backImageColorFormat.get());
		}
		Optional<CodelineFormat> codelineFormat = copy.getCodelineFormat();
		if (codelineFormat.isPresent()) {
			this.codelineFormat.set(codelineFormat.get());
		}
		imageSource.set(copy.getImageSource());
		Optional<String> frontImageFile = copy.getFrontImageFile();
		if (frontImageFile.isPresent()) {
			this.frontImageFile.set(frontImageFile.get());
		}
		Optional<String> backImageFile = copy.getBackImageFile();
		if (backImageFile.isPresent()) {
			this.backImageFile.set(backImageFile.get());
		}
	}

	public Optional<ImageFormat> getFrontImageType() {
		Optional<ImageFormat> result = Optional.empty();
		if (frontImageType.intValue() != 0) {
			result = Optional.of(frontImageType.get());
		}
		return result;
	}

	public Optional<ImageFormat> getBackImageType() {
		Optional<ImageFormat> result = Optional.empty();
		if (backImageType.intValue() != 0) {
			result = Optional.of(backImageType.get());
		}
		return result;
	}

	public Optional<ImageColorFormat> getFrontImageColorFormat() {
		Optional<ImageColorFormat> result = Optional.empty();
		if (frontImageColorFormat.intValue() != 0) {
			result = Optional.of(frontImageColorFormat.get());
		}
		return result;
	}

	public Optional<ImageColorFormat> getBackImageColorFormat() {
		Optional<ImageColorFormat> result = Optional.empty();
		if (backImageColorFormat.intValue() != 0) {
			result = Optional.of(backImageColorFormat.get());
		}
		return result;
	}

	public Optional<CodelineFormat> getCodelineFormat() {
		Optional<CodelineFormat> result = Optional.empty();
		if (codelineFormat.intValue() != 0) {
			result = Optional.of(codelineFormat.get());
		}
		return result;
	}

	public Set<ImageSource> getImageSource() {
		return imageSource.get();
	}

	public Optional<String> getFrontImageFile() {
		Optional<String> result = Optional.empty();
		if (!Pointer.NULL.equals(frontImageFile)) {
			result = Optional.of(frontImageFile.get());
		}
		return result;
	}

	public Optional<String> getBackImageFile() {
		Optional<String> result = Optional.empty();
		if (!Pointer.NULL.equals(backImageFile)) {
			result = Optional.of(backImageFile.get());
		}
		return result;
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
