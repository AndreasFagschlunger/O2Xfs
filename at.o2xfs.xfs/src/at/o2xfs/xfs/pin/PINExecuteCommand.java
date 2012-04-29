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

public enum PINExecuteCommand implements XfsConstant {

	/**
	 * 
	 */
	WFS_CMD_PIN_CRYPT(401L),

	/**
	 * 
	 */
	WFS_CMD_PIN_IMPORT_KEY(403L),

	/**
	 * 
	 */
	WFS_CMD_PIN_GET_PIN(405L),

	/**
	 * 
	 */
	WFS_CMD_PIN_GET_PINBLOCK(407L),

	/**
	 * 
	 */
	WFS_CMD_PIN_GET_DATA(408L),

	/**
	 * 
	 */
	WFS_CMD_PIN_INITIALIZATION(409L),

	/**
	 * 
	 */
	WFS_CMD_PIN_LOCAL_DES(410L),

	/**
	 * 
	 */
	WFS_CMD_PIN_LOCAL_EUROCHEQUE(411L),

	/**
	 * 
	 */
	WFS_CMD_PIN_LOCAL_VISA(412L),

	/**
	 * 
	 */
	WFS_CMD_PIN_CREATE_OFFSET(413L),

	/**
	 * 
	 */
	WFS_CMD_PIN_DERIVE_KEY(414L),

	/**
	 * 
	 */
	WFS_CMD_PIN_PRESENT_IDC(415L),

	/**
	 * @since 3.00
	 */
	WFS_CMD_PIN_LOCAL_BANKSYS(416L),

	/**
	 * @since 3.00
	 */
	WFS_CMD_PIN_BANKSYS_IO(417L),

	/**
	 * @since 3.00
	 */
	WFS_CMD_PIN_RESET(418L),

	/**
	 * @since 3.00
	 */
	WFS_CMD_PIN_HSM_SET_TDATA(419L),

	/**
	 * @since 3.00
	 */
	WFS_CMD_PIN_SECURE_MSG_SEND(420L),

	/**
	 * @since 3.00
	 */
	WFS_CMD_PIN_SECURE_MSG_RECEIVE(421L),

	/**
	 * @since 3.00
	 */
	WFS_CMD_PIN_GET_JOURNAL(422L),

	/**
	 * @since 3.00
	 */
	WFS_CMD_PIN_IMPORT_KEY_EX(423L),

	/**
	 * @since 3.00
	 */
	WFS_CMD_PIN_ENC_IO(424L),

	/**
	 * @since 3.00
	 */
	WFS_CMD_PIN_HSM_INIT(425L),

	/**
	 * @since 3.00
	 */
	WFS_CMD_PIN_IMPORT_RSA_PUBLIC_KEY(426L),

	/**
	 * @since 3.00
	 */
	WFS_CMD_PIN_EXPORT_RSA_ISSUER_SIGNED_ITEM(427L),

	/**
	 * @since 3.00
	 */
	WFS_CMD_PIN_IMPORT_RSA_SIGNED_DES_KEY(428L),

	/**
	 * @since 3.00
	 */
	WFS_CMD_PIN_GENERATE_RSA_KEY_PAIR(429L),

	/**
	 * @since 3.00
	 */
	WFS_CMD_PIN_EXPORT_RSA_EPP_SIGNED_ITEM(430L),

	/**
	 * @since 3.00
	 */
	WFS_CMD_PIN_LOAD_CERTIFICATE(431L),

	/**
	 * @since 3.00
	 */
	WFS_CMD_PIN_GET_CERTIFICATE(432L),

	/**
	 * @since 3.00
	 */
	WFS_CMD_PIN_REPLACE_CERTIFICATE(433L),

	/**
	 * @since 3.00
	 */
	WFS_CMD_PIN_START_KEY_EXCHANGE(434L),

	/**
	 * @since 3.00
	 */
	WFS_CMD_PIN_IMPORT_RSA_ENCIPHERED_PKCS7_KEY(435L),

	/**
	 * @since 3.00
	 */
	WFS_CMD_PIN_EMV_IMPORT_PUBLIC_KEY(436L),

	/**
	 * @since 3.00
	 */
	WFS_CMD_PIN_DIGEST(437L),

	/**
	 * @since 3.00
	 */
	WFS_CMD_PIN_SECUREKEY_ENTRY(438L),

	/**
	 * @since 3.00
	 */
	WFS_CMD_PIN_GENERATE_KCV(439L),

	/**
	 * @since 3.10
	 */
	WFS_CMD_PIN_SET_GUIDANCE_LIGHT(441L),

	/**
	 * @since 3.10
	 */
	WFS_CMD_PIN_MAINTAIN_PIN(442L),

	/**
	 * @since 3.10
	 */
	WFS_CMD_PIN_KEYPRESS_BEEP(443L),

	/**
	 * @since 3.10
	 */
	WFS_CMD_PIN_SET_PINBLOCK_DATA(444L),

	/**
	 * @since 3.10
	 */
	WFS_CMD_PIN_SET_LOGICAL_HSM(445L),

	/**
	 * @since 3.10
	 */
	WFS_CMD_PIN_IMPORT_KEYBLOCK(446L),

	/**
	 * @since 3.10
	 */
	WFS_CMD_PIN_POWER_SAVE_CONTROL(447L);

	private final long value;

	private PINExecuteCommand(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}
