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

import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.LPWSTR;
import at.o2xfs.win32.Struct;
import at.o2xfs.xfs.win32.XfsWord;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Takepict
		extends Struct {

	protected final XfsWord<Camera> camera = new XfsWord<>(Camera.class);
	protected final LPSTR camData = new LPSTR();
	protected final LPWSTR unicodeCamData = new LPWSTR();

	protected Takepict() {
		add(camera);
		add(camData);
		add(unicodeCamData);
	}

	public Takepict(Camera aCamera, String aCamData, String aUnicodeCamData) {
		this();
		allocate();
		camera.set(aCamera);
		camData.set(aCamData);
		unicodeCamData.set(aUnicodeCamData);
	}

	public Takepict(Takepict copy) {
		this(copy.getCamera(), copy.getCamData(), copy.getUnicodeCamData());
	}

	public Camera getCamera() {
		return camera.get();
	}

	public String getCamData() {
		return camData.toString();
	}

	public String getUnicodeCamData() {
		return unicodeCamData.get();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("camera", getCamera())
										.append("camData", getCamData())
										.append("unicodeCamData", getUnicodeCamData())
										.toString();
	}
}