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

public enum PINUse implements XfsConstant {

	WFS_PIN_USECONSTRUCT(0x0100L),
	WFS_PIN_USESECURECONSTRUCT(0x0200L),
	WFS_PIN_USEANSTR31MASTER(0x0400L),
	WFS_PIN_USEPINLOCAL(0x00010000L),
	WFS_PIN_USERSAPUBLIC(0x00020000L),
	WFS_PIN_USERSAPRIVATE(0x00040000L),
	WFS_PIN_USECHIPINFO(0x00100000L),
	WFS_PIN_USECHIPPIN(0x00200000L),
	WFS_PIN_USECHIPPS(0x00400000L),
	WFS_PIN_USECHIPMAC(0x00800000L),
	WFS_PIN_USECHIPLT(0x01000000L),
	WFS_PIN_USECHIPMACLZ(0x02000000L),
	WFS_PIN_USECHIPMACAZ(0x04000000L),
	WFS_PIN_USERSAPUBLICVERIFY(0x08000000L),
	WFS_PIN_USERSAPRIVATESIGN(0x10000000L);

	private final long value;

	private PINUse(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}
