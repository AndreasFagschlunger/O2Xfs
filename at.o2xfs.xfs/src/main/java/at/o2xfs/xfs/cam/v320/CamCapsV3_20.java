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

package at.o2xfs.xfs.cam.v320;

import at.o2xfs.win32.BOOL;
import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.cam.CamCaps;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class CamCapsV3_20
		extends CamCaps {

	private final BOOL pictureFile = new BOOL();
	private final BOOL antiFraudModule = new BOOL();

	private CamCapsV3_20() {
		add(pictureFile);
		add(antiFraudModule);
	}

	public CamCapsV3_20(Pointer p) {
		this();
		assignBuffer(p);
	}

	public CamCapsV3_20(CamCapsV3_20 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(CamCapsV3_20 caps) {
		super.set(caps);
		pictureFile.set(caps.isPictureFile());
		antiFraudModule.set(caps.isAntiFraudModule());
	}

	public boolean isPictureFile() {
		return pictureFile.booleanValue();
	}

	public boolean isAntiFraudModule() {
		return antiFraudModule.booleanValue();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).appendSuper(super.toString())
										.append("pictureFile", isPictureFile())
										.append("antiFraudModule", isAntiFraudModule())
										.toString();
	}
}