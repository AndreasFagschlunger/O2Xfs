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

package at.o2xfs.xfs.idc;

import at.o2xfs.xfs.XfsConstant;

public enum IDCChipProtocol implements XfsConstant {

	/**
	 * The ID card unit can handle the T=0 protocol.
	 */
	T0(0x0001L),

	/**
	 * The ID card unit can handle the T=1 protocol.
	 */
	T1(0x0002L),

	/**
	 * The ID card unit is capable of communicating with a chip card without
	 * requiring the application to specify any protocol.
	 */
	PROTOCOL_NOT_REQUIRED(0x0004L),

	/**
	 * The ID card unit can handle the ISO 14443 (Part3) Type A contactless chip
	 * card protocol.
	 */
	TYPEA_PART3(0x0008L),

	/**
	 * The ID card unit can handle the ISO 14443 (Part4) Type A contactless chip
	 * card protocol.
	 */
	TYPEA_PART4(0x0010L),

	/**
	 * The ID card unit can handle the ISO 14443 Type B contactless chip card
	 * protocol.
	 */
	TYPEB(0x0020L),

	/**
	 * The ID card unit can handle the ISO 18092 (106/212/424kbps) contactless
	 * chip card protocol.
	 */
	NFC(0x0040L);

	private final long value;

	private IDCChipProtocol(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}

}