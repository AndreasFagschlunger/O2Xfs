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

package at.o2xfs.xfs.cdm;

import at.o2xfs.xfs.XfsConstant;

public enum CdmError implements XfsConstant {

	/*
	 * @since v3.00
	 */
	INVALIDCURRENCY(-300L),

	/*
	 * @since v3.00
	 */
	INVALIDTELLERID(-301L),

	/*
	 * @since v3.00
	 */
	CASHUNITERROR(-302L),

	/*
	 * @since v3.00
	 */
	INVALIDDENOMINATION(-303L),

	/*
	 * @since v3.00
	 */
	INVALIDMIXNUMBER(-304L),

	/*
	 * @since v3.00
	 */
	NOCURRENCYMIX(-305L),

	/*
	 * @since v3.00
	 */
	NOTDISPENSABLE(-306L),

	/*
	 * @since v3.00
	 */
	TOOMANYITEMS(-307L),

	/*
	 * @since v3.00
	 */
	UNSUPPOSITION(-308L),

	/*
	 * @since v3.00
	 */
	SAFEDOOROPEN(-310L),

	/*
	 * @since v3.00
	 */
	SHUTTERNOTOPEN(-312L),

	/*
	 * @since v3.00
	 */
	SHUTTEROPEN(-313L),

	/*
	 * @since v3.00
	 */
	SHUTTERCLOSED(-314L),

	/*
	 * @since v3.00
	 */
	INVALIDCASHUNIT(-315L),

	/*
	 * @since v3.00
	 */
	NOITEMS(-316L),

	/*
	 * @since v3.00
	 */
	EXCHANGEACTIVE(-317L),

	/*
	 * @since v3.00
	 */
	NOEXCHANGEACTIVE(-318L),

	/*
	 * @since v3.00
	 */
	SHUTTERNOTCLOSED(-319L),

	/*
	 * @since v3.00
	 */
	PRERRORNOITEMS(-320L),

	/*
	 * @since v3.00
	 */
	PRERRORITEMS(-321L),

	/*
	 * @since v3.00
	 */
	PRERRORUNKNOWN(-322L),

	/*
	 * @since v3.00
	 */
	ITEMSTAKEN(-323L),

	/*
	 * @since v3.00
	 */
	INVALIDMIXTABLE(-327L),

	/*
	 * @since v3.00
	 */
	OUTPUTPOS_NOT_EMPTY(-328L),

	/*
	 * @since v3.00
	 */
	INVALIDRETRACTPOSITION(-329L),

	/*
	 * @since v3.00
	 */
	NOTRETRACTAREA(-330L),

	/*
	 * @since v3.00
	 */
	NOCASHBOXPRESENT(-333L),

	/*
	 * @since v3.00
	 */
	AMOUNTNOTINMIXTABLE(-334L),

	/*
	 * @since v3.00
	 */
	ITEMSNOTTAKEN(-335L),

	/*
	 * @since v3.00
	 */
	ITEMSLEFT(-336L),

	/*
	 * @since v3.10
	 */
	INVALID_PORT(-337L),

	/*
	 * @since v3.10
	 */
	POWERSAVETOOSHORT(-338L),

	/*
	 * @since v3.10
	 */
	POWERSAVEMEDIAPRESENT(-339L),

	/*
	 * @since v3.30
	 */
	POSITION_NOT_EMPTY(-340L),

	/*
	 * @since v3.30
	 */
	INCOMPLETERETRACT(-341L),

	/*
	 * @since v3.30
	 */
	COMMANDUNSUPP(-342L),

	/*
	 * @since v3.30
	 */
	SYNCHRONIZEUNSUPP(-343L);

	private final long value;

	private CdmError(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}