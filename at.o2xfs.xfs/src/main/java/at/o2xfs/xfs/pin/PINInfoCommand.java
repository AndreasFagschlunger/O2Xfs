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

public enum PINInfoCommand implements XfsConstant {

	/**
	 * The STATUS command returns several kinds of status information.
	 */
	STATUS(401L),

	/**
	 * This command is used to retrieve the capabilities of the PIN pad.
	 * 
	 * @see WFSPINCAPS
	 */
	CAPABILITIES(402L),

	/**
	 * This command returns detailed information about the keys in the
	 * encryption module.
	 */
	KEY_DETAIL(404L),

	/**
	 * This command returns information about the names of the Function Keys
	 * supported by the device. Location information is also returned for the
	 * supported FDKs (Function Descriptor Keys) or Touch Screen Pads if this
	 * XFS interface is used for Touch Screen input.
	 */
	FUNCKEY_DETAIL(405L),

	/**
	 * @since 3.00
	 */
	HSM_TDATA(406L),

	/**
	 * @since 3.00
	 */
	KEY_DETAIL_EX(407L),

	/**
	 * @since 3.00
	 */
	SECUREKEY_DETAIL(408L),

	/**
	 * @since 3.10
	 */
	QUERY_LOGICAL_HSM_DETAIL(409L);

	private final long value;

	private PINInfoCommand(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}

}