/*
 * Copyright (c) 2014, Andreas Fagschlunger. All rights reserved.
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

public enum PINAlgorithm implements XfsConstant {

	/**
	 * Electronic Code Book
	 */
	WFS_PIN_CRYPTDESECB(0x0001L),

	/**
	 * Cipher Block Chaining
	 */
	WFS_PIN_CRYPTDESCBC(0x0002L),

	/**
	 * Cipher Feed Back
	 */
	WFS_PIN_CRYPTDESCFB(0x0004L),

	/**
	 * RSA Encryption
	 */
	WFS_PIN_CRYPTRSA(0x0008L),

	/**
	 * ECMA Encryption
	 */
	WFS_PIN_CRYPTECMA(0x0010L),

	/**
	 * MAC calculation using CBC
	 */
	WFS_PIN_CRYPTDESMAC(0x0020L),

	/**
	 * Triple DES with Electronic Code Book
	 */
	WFS_PIN_CRYPTTRIDESECB(0x0040L),

	/**
	 * Triple DES with Cipher Block Chaining
	 */
	WFS_PIN_CRYPTTRIDESCBC(0x0080L),

	/**
	 * Triple DES with Cipher Feed Back
	 */
	WFS_PIN_CRYPTTRIDESCFB(0x0100L),

	/**
	 * Triple DES MAC calculation using CBC
	 */
	WFS_PIN_CRYPTTRIDESMAC(0x0200L);

	private final long value;

	private PINAlgorithm(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}