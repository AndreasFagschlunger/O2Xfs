/*
 * Copyright (c) 2017, Andreas Fagschlunger. All rights reserved.
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

package at.o2xfs.xfs.cam;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.XfsExtra;
import at.o2xfs.xfs.XfsServiceClass;
import at.o2xfs.xfs.win32.XfsWord;
import at.o2xfs.xfs.win32.XfsWordArray;
import at.o2xfs.xfs.win32.XfsWordBitmask;

public class CamCaps extends Struct {

	private final XfsWord<XfsServiceClass> serviceClass = new XfsWord<XfsServiceClass>(XfsServiceClass.class);
	private final XfsWord<CamType> type = new XfsWord<CamType>(CamType.class);
	private final XfsWordArray<CameraAvailable> cameras = new XfsWordArray<>(CameraAvailable.class,
			CamConstant.CAMERAS_SIZE);
	private final USHORT maxPictures = new USHORT();
	private final XfsWordBitmask<CamData> camData = new XfsWordBitmask<CamData>(CamData.class);
	private final USHORT maxDataLength = new USHORT();
	private final XfsWordBitmask<CharSupport> charSupport = new XfsWordBitmask<CharSupport>(CharSupport.class);
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
		camData.set(caps.camData.get());
		maxDataLength.set(caps.maxDataLength);
		charSupport.set(caps.charSupport.get());
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
		return new ToStringBuilder(this)
				.append("serviceClass", getServiceClass())
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