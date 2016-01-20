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
 * 
 */
public enum IDCInfoCommand implements XfsConstant {

	/**
	 * This command reports the full range of information available, including
	 * the information that is provided either by the service provider or, if
	 * present, by any of the security modules. In addition to that, the number
	 * of cards retained is transmitted for motor driven card reader/writer (for
	 * devices of the other categories this number is always set to zero).
	 * 
	 * @see WFSIDCSTATUS
	 */
	STATUS(201L),

	/**
	 * This command is used to retrieve the capabilities of the ID card unit.
	 * 
	 * @see WFSIDCCAPS
	 */
	CAPABILITIES(202L),

	/**
	 * This command is used to retrieve the list of forms available on the
	 * device.
	 */
	FORM_LIST(203L),

	/**
	 * This command is used to retrieve details of the definition of a specified
	 * form.
	 */
	QUERY_FORM(204L),

	/**
	 * This command is used to retrieve the complete list of registration
	 * authority Interface Module (IFM) identifiers. The primary registration
	 * authority is EMVCo but other organizations are also supported for
	 * historical or local country requirements. New registration authorities
	 * may be added in the future so applications should be able to handle the
	 * return of new (as yet undefined) IFM identifiers.
	 * 
	 * @since 3.10
	 */
	QUERY_IFM_IDENTIFIER(205L);

	private final long value;

	/**
	 * @param value
	 */
	private IDCInfoCommand(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}