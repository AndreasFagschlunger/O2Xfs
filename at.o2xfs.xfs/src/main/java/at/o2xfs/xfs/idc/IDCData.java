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

/**
 * @author Andreas Fagschlunger
 */
public enum IDCData implements XfsConstant {

	/**
	 * The data is ok.
	 */
	OK(0L),

	/**
	 * The track/chip is blank.
	 */
	MISSING(1L),

	/**
	 * The data contained on the track/chip is invalid.
	 */
	INVALID(2L),

	/**
	 * The data contained on the track/chip is too long.
	 */
	TOOLONG(3L),

	/**
	 * The data contained on the track/chip is too short.
	 */
	TOOSHORT(4L),

	/**
	 * The data source to read from is not supported by the service provider.
	 */
	SRCNOTSUPP(5L),

	/**
	 * The data source to read from is missing on the card.
	 */
	SRCMISSING(6L);

	private final long value;

	/**
	 * @param value
	 */
	private IDCData(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}