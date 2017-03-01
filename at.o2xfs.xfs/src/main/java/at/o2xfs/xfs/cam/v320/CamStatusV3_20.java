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

import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.cam.AntiFraudModule;
import at.o2xfs.xfs.cam.CamStatus;
import at.o2xfs.xfs.win32.XfsWord;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class CamStatusV3_20
		extends CamStatus {

	private final XfsWord<AntiFraudModule> antiFraudModule = new XfsWord<AntiFraudModule>(AntiFraudModule.class);

	private CamStatusV3_20() {
		super();
		add(antiFraudModule);
	}

	public CamStatusV3_20(Pointer p) {
		this();
		assignBuffer(p);
	}

	public CamStatusV3_20(CamStatusV3_20 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(CamStatusV3_20 status) {
		set((CamStatus) status);
		antiFraudModule.set(status.getAntiFraudModule());
	}

	public AntiFraudModule getAntiFraudModule() {
		return antiFraudModule.get();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).appendSuper(super.toString())
										.append("antiFraudModule", getAntiFraudModule())
										.toString();
	}
}