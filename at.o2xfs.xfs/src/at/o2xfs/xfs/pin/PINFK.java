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

import at.o2xfs.xfs.XfsConstant;

public enum PINFK implements XfsConstant {
	FK_0(0x00000001L),
	FK_1(0x00000002L),
	FK_2(0x00000004L),
	FK_3(0x00000008L),
	FK_4(0x00000010L),
	FK_5(0x00000020L),
	FK_6(0x00000040L),
	FK_7(0x00000080L),
	FK_8(0x00000100L),
	FK_9(0x00000200L),
	FK_ENTER(0x00000400L),
	FK_CANCEL(0x00000800L),
	FK_CLEAR(0x00001000L),
	FK_BACKSPACE(0x00002000L),
	FK_HELP(0x00004000L),
	FK_DECPOINT(0x00008000L),
	FK_00(0x00010000L),
	FK_000(0x00020000L),
	FK_RES1(0x00040000L),
	FK_RES2(0x00080000L),
	FK_RES3(0x00100000L),
	FK_RES4(0x00200000L),
	FK_RES5(0x00400000L),
	FK_RES6(0x00800000L),
	FK_RES7(0x01000000L),
	FK_RES8(0x02000000L),
	FK_OEM1(0x04000000L),
	FK_OEM2(0x08000000L),
	FK_OEM3(0x10000000L),
	FK_OEM4(0x20000000L),
	FK_OEM5(0x40000000L),
	FK_OEM6(0x80000000L);

	private final long value;

	private PINFK(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}

}