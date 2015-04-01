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

import at.o2xfs.xfs.XfsConstant;

public enum PTRPassbookControl implements XfsConstant {

	/**
	 * WFS_PTR_PBKCTRLNOTSUPP
	 */
	NOTSUPP(0x0000L),

	/**
	 * WFS_PTR_PBKCTRLTURNFORWARD
	 */
	TURNFORWARD(0x0001L),

	/**
	 * WFS_PTR_PBKCTRLTURNBACKWARD
	 */
	TURNBACKWARD(0x0002L),

	/**
	 * WFS_PTR_PBKCTRLCLOSEFORWARD
	 */
	CLOSEFORWARD(0x0004L),

	/**
	 * WFS_PTR_PBKCTRLCLOSEBACKWARD
	 */
	CLOSEBACKWARD(0x0008L);

	private final long value;

	private PTRPassbookControl(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}