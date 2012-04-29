/*
 * Copyright (c) 2012, Andreas Fagschlunger. All rights reserved.
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

package at.o2xfs.xfs.pin;

import at.o2xfs.xfs.XfsConstant;

public enum PINFK implements XfsConstant {
	WFS_PIN_FK_0(0x00000001L),
	WFS_PIN_FK_1(0x00000002L),
	WFS_PIN_FK_2(0x00000004L),
	WFS_PIN_FK_3(0x00000008L),
	WFS_PIN_FK_4(0x00000010L),
	WFS_PIN_FK_5(0x00000020L),
	WFS_PIN_FK_6(0x00000040L),
	WFS_PIN_FK_7(0x00000080L),
	WFS_PIN_FK_8(0x00000100L),
	WFS_PIN_FK_9(0x00000200L),
	WFS_PIN_FK_ENTER(0x00000400L),
	WFS_PIN_FK_CANCEL(0x00000800L),
	WFS_PIN_FK_CLEAR(0x00001000L),
	WFS_PIN_FK_BACKSPACE(0x00002000L),
	WFS_PIN_FK_HELP(0x00004000L),
	WFS_PIN_FK_DECPOINT(0x00008000L),
	WFS_PIN_FK_00(0x00010000L),
	WFS_PIN_FK_000(0x00020000L),
	WFS_PIN_FK_RES1(0x00040000L),
	WFS_PIN_FK_RES2(0x00080000L),
	WFS_PIN_FK_RES3(0x00100000L),
	WFS_PIN_FK_RES4(0x00200000L),
	WFS_PIN_FK_RES5(0x00400000L),
	WFS_PIN_FK_RES6(0x00800000L),
	WFS_PIN_FK_RES7(0x01000000L),
	WFS_PIN_FK_RES8(0x02000000L),
	WFS_PIN_FK_OEM1(0x04000000L),
	WFS_PIN_FK_OEM2(0x08000000L),
	WFS_PIN_FK_OEM3(0x10000000L),
	WFS_PIN_FK_OEM4(0x20000000L),
	WFS_PIN_FK_OEM5(0x40000000L),
	WFS_PIN_FK_OEM6(0x80000000L);

	private final long value;

	private PINFK(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}

}
