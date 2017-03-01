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

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.UShortArray;
import at.o2xfs.xfs.XfsExtra;
import at.o2xfs.xfs.XfsStruct;
import at.o2xfs.xfs.win32.XfsWord;
import at.o2xfs.xfs.win32.XfsWordArray;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class CamStatus
		extends XfsStruct {

	protected final XfsWord<CamDeviceState> device = new XfsWord<CamDeviceState>(CamDeviceState.class);
	protected final XfsWordArray<Media> mediaStates = new XfsWordArray<Media>(Media.class, CamConstant.CAMERAS_SIZE);
	protected final XfsWordArray<CameraState> cameraStates = new XfsWordArray<CameraState>(	CameraState.class,
																							CamConstant.CAMERAS_SIZE);
	protected final UShortArray pictures = new UShortArray(CamConstant.CAMERAS_SIZE);
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