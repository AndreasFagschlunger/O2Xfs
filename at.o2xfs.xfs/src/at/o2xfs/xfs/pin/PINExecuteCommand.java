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

public enum PINExecuteCommand implements XfsConstant {

	/**
	 * 
	 */
	CRYPT(401L),

	/**
	 * 
	 */
	IMPORT_KEY(403L),

	/**
	 * 
	 */
	GET_PIN(405L),

	/**
	 * 
	 */
	GET_PINBLOCK(407L),

	/**
	 * 
	 */
	GET_DATA(408L),

	/**
	 * 
	 */
	INITIALIZATION(409L),

	/**
	 * 
	 */
	LOCAL_DES(410L),

	/**
	 * 
	 */
	LOCAL_EUROCHEQUE(411L),

	/**
	 * 
	 */
	LOCAL_VISA(412L),

	/**
	 * 
	 */
	CREATE_OFFSET(413L),

	/**
	 * 
	 */
	DERIVE_KEY(414L),

	/**
	 * 
	 */
	PRESENT_IDC(415L),

	/**
	 * @since 3.00
	 */
	LOCAL_BANKSYS(416L),

	/**
	 * @since 3.00
	 */
	BANKSYS_IO(417L),

	/**
	 * @since 3.00
	 */
	RESET(418L),

	/**
	 * @since 3.00
	 */
	HSM_SET_TDATA(419L),

	/**
	 * @since 3.00
	 */
	SECURE_MSG_SEND(420L),

	/**
	 * @since 3.00
	 */
	SECURE_MSG_RECEIVE(421L),

	/**
	 * @since 3.00
	 */
	GET_JOURNAL(422L),

	/**
	 * @since 3.00
	 */
	IMPORT_KEY_EX(423L),

	/**
	 * @since 3.00
	 */
	ENC_IO(424L),

	/**
	 * @since 3.00
	 */
	HSM_INIT(425L),

	/**
	 * @since 3.00
	 */
	IMPORT_RSA_PUBLIC_KEY(426L),

	/**
	 * @since 3.00
	 */
	EXPORT_RSA_ISSUER_SIGNED_ITEM(427L),

	/**
	 * @since 3.00
	 */
	IMPORT_RSA_SIGNED_DES_KEY(428L),

	/**
	 * @since 3.00
	 */
	GENERATE_RSA_KEY_PAIR(429L),

	/**
	 * @since 3.00
	 */
	EXPORT_RSA_EPP_SIGNED_ITEM(430L),

	/**
	 * @since 3.00
	 */
	LOAD_CERTIFICATE(431L),

	/**
	 * @since 3.00
	 */
	GET_CERTIFICATE(432L),

	/**
	 * @since 3.00
	 */
	REPLACE_CERTIFICATE(433L),

	/**
	 * @since 3.00
	 */
	START_KEY_EXCHANGE(434L),

	/**
	 * @since 3.00
	 */
	IMPORT_RSA_ENCIPHERED_PKCS7_KEY(435L),

	/**
	 * @since 3.00
	 */
	EMV_IMPORT_PUBLIC_KEY(436L),

	/**
	 * @since 3.00
	 */
	DIGEST(437L),

	/**
	 * @since 3.00
	 */
	SECUREKEY_ENTRY(438L),

	/**
	 * @since 3.00
	 */
	GENERATE_KCV(439L),

	/**
	 * @since 3.10
	 */
	SET_GUIDANCE_LIGHT(441L),

	/**
	 * @since 3.10
	 */
	MAINTAIN_PIN(442L),

	/**
	 * @since 3.10
	 */
	KEYPRESS_BEEP(443L),

	/**
	 * @since 3.10
	 */
	SET_PINBLOCK_DATA(444L),

	/**
	 * @since 3.10
	 */
	SET_LOGICAL_HSM(445L),

	/**
	 * @since 3.10
	 */
	IMPORT_KEYBLOCK(446L),

	/**
	 * @since 3.10
	 */
	POWER_SAVE_CONTROL(447L);

	private final long value;

	private PINExecuteCommand(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}