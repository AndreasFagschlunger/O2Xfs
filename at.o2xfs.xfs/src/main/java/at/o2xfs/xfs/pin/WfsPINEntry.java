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

package at.o2xfs.xfs.pin;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.XfsWord;
import at.o2xfs.xfs.util.XfsConstants;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class WfsPINEntry
		extends Struct {

	private USHORT digits = new USHORT();
	private XfsWord<PINCompletion> completion = new XfsWord<>(PINCompletion.class);

	public WfsPINEntry() {
		add(digits);
		add(completion);
	}

	public WfsPINEntry(Pointer p) {
		this();
		assignBuffer(p);
	}

	public WfsPINEntry(WfsPINEntry copy) {
		this();
		allocate();
		digits.set(copy.digits);
		completion.set(copy.getCompletion());
	}

	public int getDigits() {
		return digits.intValue();
	}

	public void setDigits(int digits) {
		this.digits.set(digits);
	}

	public PINCompletion getCompletion() {
		return XfsConstants.valueOf(completion, PINCompletion.class);
	}

	public void setCompletion(PINCompletion completion) {
		this.completion.set(completion);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("digits", getDigits()).append("completion", getCompletion()).toString();
	}
}