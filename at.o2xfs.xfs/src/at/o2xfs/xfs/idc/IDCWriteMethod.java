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

public enum IDCWriteMethod implements XfsConstant {

	/**
	 * Does not support writing of magnetic stripes.
	 *
	 * @since 3.00
	 */
	NOTSUPP(0x0000L),

	/**
	 * Does not support writing of magnetic stripes.
	 *
	 * @since 3.00
	 */
	UNKNOWN(0x0001L),

	/**
	 * Supports writing of loco magnetic stripes.
	 *
	 * @since 3.00
	 */
	LOCO(0x0002L),

	/**
	 * Supports writing of hico magnetic stripes.
	 *
	 * @since 3.00
	 */
	HICO(0x0004L),

	/**
	 * Service provider is capable of automatically determining whether loco or
	 * hico magnetic stripes should be written.
	 *
	 * @since 3.00
	 */
	AUTO(0x0008L);

	private final long value;

	private IDCWriteMethod(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}