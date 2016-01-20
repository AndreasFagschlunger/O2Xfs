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

import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.XfsData;
import at.o2xfs.xfs.XfsStruct;
import at.o2xfs.xfs.XfsVersion;
import at.o2xfs.xfs.XfsWord;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class PTRImageStruct
		extends XfsStruct {

	private final XfsWord<PTRImageType> imageType = new XfsWord<PTRImageType>(PTRImageType.class);
	private final XfsWord<PTRImageSource> imageSource = new XfsWord<PTRImageSource>(PTRImageSource.class);
	private final XfsWord<PTRData> status = new XfsWord<PTRData>(PTRData.class);
	private final XfsData data = new XfsData();

	private PTRImageStruct(XfsVersion version) {
		if (XfsVersion.V2_00.isGE(version)) {
			add(imageType);
			imageSource.allocate();
			status.allocate();
		} else {
			imageType.allocate();
			add(imageSource);
			add(status);
		}
		add(data);
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
		data.set(copy.getData());
	}

	public PTRImageType getImageType() {
		return imageType.get();
	}

	public PTRImageSource getImageSource() {
		return imageSource.get();
	}

	public PTRData getStatus() {
		return status.get();
	}

	public byte[] getData() {
		return data.getData();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("imageType", getImageType())
										.append("imageSource", getImageSource())
										.append("status", getStatus())
										.append("data", getData())
										.toString();
	}
}