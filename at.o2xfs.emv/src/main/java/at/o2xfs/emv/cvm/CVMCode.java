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

package at.o2xfs.emv.cvm;

public final class CVMCode {

	/**
	 * Fail CVM processing
	 */
	public static final int FAIL_CVM_PROCESSING = 0x00;

	/**
	 * Plaintext PIN verification performed by ICC
	 */
	public static final int PLAINTEXT_PIN_BY_ICC = 0x01;

	/**
	 * Enciphered PIN verified online
	 */
	public static final int ENCIPHERED_PIN_VERIFIED_ONLINE = 0x02;

	/**
	 * Plaintext PIN verification performed by ICC and signature (paper)
	 */
	public static final int PLAINTEXT_PIN_BY_ICC_AND_SIGNATURE = 0x03;

	/**
	 * Enciphered PIN verification performed by ICC
	 */
	public static final int ENCIPHERED_PIN_BY_ICC = 0x04;

	/**
	 * Enciphered PIN verification performed by ICC and signature (paper)
	 */
	public static final int ENCIPHERED_PIN_BY_ICC_AND_SIGNATURE = 0x05;

	/**
	 * Signature (paper)
	 */
	public static final int SIGNATURE = 0x1E;

	/**
	 * No CVM required
	 */
	public static final int NO_CVM_REQUIRED = 0x1F;

	private CVMCode() {
		throw new AssertionError();
	}
}