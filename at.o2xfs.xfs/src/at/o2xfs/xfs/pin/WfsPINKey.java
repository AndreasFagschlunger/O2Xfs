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
import at.o2xfs.win32.ULONG;
import at.o2xfs.xfs.XfsWord;
import at.o2xfs.xfs.util.XfsConstants;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class WfsPINKey
		extends Struct {

	/**
	 * Specifies the reason for completion of the entry. Possible values are: {@link PINCompletion}
	 */
	private XfsWord<PINCompletion> completion = new XfsWord<>(PINCompletion.class);

	/**
	 * Specifies the digit entered by the user or the replace character when
	 * working in encryption mode (WFS_CMD_PIN_GET_PIN). If no digit but a
	 * function key has been depressed, the key code is returned in this
	 * parameter.
	 */
	private ULONG digit = new ULONG();

	public WfsPINKey() {
		add(completion);
		add(digit);
	}

	public WfsPINKey(Pointer p) {
		this();
		assignBuffer(p);
	}

	/**
	 * Copy constructor.
	 */
	public WfsPINKey(final WfsPINKey pinKey) {
		this();
		allocate();
		completion.set(pinKey.getCompletion());
		digit.set(pinKey.digit);
	}

	public PINCompletion getCompletion() {
		return XfsConstants.valueOf(completion, PINCompletion.class);
	}

	public void setCompletion(final PINCompletion completion) {
		this.completion.set(completion);
	}

	public long getDigit() {
		return digit.longValue();
	}

	public void setDigit(long digit) {
		this.digit.set(digit);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("completion", getCompletion()).append("digit", getDigit()).toString();
	}
}