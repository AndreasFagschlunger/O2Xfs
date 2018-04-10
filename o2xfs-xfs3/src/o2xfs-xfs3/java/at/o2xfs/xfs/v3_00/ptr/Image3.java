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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.ByteArray;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.ULONG;
import at.o2xfs.xfs.ptr.DataStatus;
import at.o2xfs.xfs.ptr.ImageSource;
import at.o2xfs.xfs.win32.XfsWord;

public class Image3 extends Struct {

	protected final XfsWord<ImageSource> imageSource = new XfsWord<>(ImageSource.class);
	protected final XfsWord<DataStatus> status = new XfsWord<>(DataStatus.class);
	protected final ULONG dataLength = new ULONG();
	protected final Pointer data = new Pointer();

	protected Image3() {
		add(imageSource);
		add(status);
		add(dataLength);
		add(data);
	}

	public Image3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public Image3(Image3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(Image3 copy) {
		imageSource.set(copy.getImageSource());
		status.set(copy.getStatus());
		dataLength.set(copy.dataLength);
		Optional<byte[]> data = copy.getData();
		if (data.isPresent()) {
			this.data.pointTo(new ByteArray(data.get()));
		}
	}

	public ImageSource getImageSource() {
		return imageSource.get();
	}

	public DataStatus getStatus() {
		return status.get();
	}

	public long getDataLength() {
		return dataLength.get();
	}

	public Optional<byte[]> getData() {
		Optional<byte[]> result = Optional.empty();
		if (!Pointer.NULL.equals(data)) {
			result = Optional.of(data.buffer(dataLength.intValue()).get());
		}
		return result;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(getImageSource())
				.append(getStatus())
				.append(getDataLength())
				.append(getData().orElse(null))
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Image3) {
			Image3 image3 = (Image3) obj;
			return new EqualsBuilder()
					.append(getImageSource(), image3.getImageSource())
					.append(getStatus(), image3.getStatus())
					.append(getDataLength(), image3.getDataLength())
					.append(getData().orElse(null), image3.getData().orElse(null))
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("imageSource", getImageSource())
				.append("status", getStatus())
				.append("dataLength", getDataLength())
				.append("data", getData())
				.toString();
	}
}
