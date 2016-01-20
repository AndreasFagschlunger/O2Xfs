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

public enum PINUse implements XfsConstant {

	CONSTRUCT(0x0100L),
	SECURECONSTRUCT(0x0200L),
	ANSTR31MASTER(0x0400L),
	PIN_LOCAL(0x00010000L),
	RSA_PUBLIC(0x00020000L),
	RSA_PRIVATE(0x00040000L),
	CHIPINFO(0x00100000L),
	CHIPPIN(0x00200000L),
	CHIPPS(0x00400000L),
	CHIPMAC(0x00800000L),
	CHIPLT(0x01000000L),
	CHIPMACLZ(0x02000000L),
	CHIPMACAZ(0x04000000L),
	RSA_PUBLIC_VERIFY(0x08000000L),
	RSA_PRIVATE_SIGN(0x10000000L);

	private final long value;

	private PINUse(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}