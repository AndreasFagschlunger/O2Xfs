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

package at.o2xfs.xfs.cim;

import at.o2xfs.xfs.XfsConstant;

public enum CimError implements XfsConstant {

	/*
	 * @since v3.00
	 */
	INVALIDCURRENCY(-1300L),

	/*
	 * @since v3.00
	 */
	INVALIDTELLERID(-1301L),

	/*
	 * @since v3.00
	 */
	CASHUNITERROR(-1302L),

	/*
	 * @since v3.00
	 */
	TOOMANYITEMS(-1307L),

	/*
	 * @since v3.00
	 */
	UNSUPPOSITION(-1308L),

	/*
	 * @since v3.00
	 */
	SAFEDOOROPEN(-1310L),

	/*
	 * @since v3.00
	 */
	SHUTTERNOTOPEN(-1312L),

	/*
	 * @since v3.00
	 */
	SHUTTEROPEN(-1313L),

	/*
	 * @since v3.00
	 */
	SHUTTERCLOSED(-1314L),

	/*
	 * @since v3.00
	 */
	INVALIDCASHUNIT(-1315L),

	/*
	 * @since v3.00
	 */
	NOITEMS(-1316L),

	/*
	 * @since v3.00
	 */
	EXCHANGEACTIVE(-1317L),

	/*
	 * @since v3.00
	 */
	NOEXCHANGEACTIVE(-1318L),

	/*
	 * @since v3.00
	 */
	SHUTTERNOTCLOSED(-1319L),

	/*
	 * @since v3.00
	 */
	ITEMSTAKEN(-1323L),

	/*
	 * @since v3.00
	 */
	CASHINACTIVE(-1325L),

	/*
	 * @since v3.00
	 */
	NOCASHINACTIVE(-1326L),

	/*
	 * @since v3.00
	 */
	POSITION_NOT_EMPTY(-1328L),

	/*
	 * @since v3.00
	 */
	INVALIDRETRACTPOSITION(-1334L),

	/*
	 * @since v3.00
	 */
	NOTRETRACTAREA(-1335L),

	/*
	 * @since v3.10
	 */
	INVALID_PORT(-1336L),

	/*
	 * @since v3.10
	 */
	FOREIGN_ITEMS_DETECTED(-1337L),

	/*
	 * @since v3.10
	 */
	LOADFAILED(-1338L),

	/*
	 * @since v3.10
	 */
	CASHUNITNOTEMPTY(-1339L),

	/*
	 * @since v3.10
	 */
	INVALIDREFSIG(-1340L),

	/*
	 * @since v3.10
	 */
	INVALIDTRNSIG(-1341L),

	/*
	 * @since v3.10
	 */
	POWERSAVETOOSHORT(-1342L),

	/*
	 * @since v3.10
	 */
	POWERSAVEMEDIAPRESENT(-1343L),

	/*
	 * @since v3.20
	 */
	DEVICELOCKFAILURE(-1344L),

	/*
	 * @since v3.20
	 */
	TOOMANYITEMSTOCOUNT(-1345L),

	/*
	 * @since v3.20
	 */
	COUNTPOSNOTEMPTY(-1346L),

	/*
	 * @since v3.20
	 */
	MEDIAINACTIVE(-1347L),

	/*
	 * @since v3.30
	 */
	COMMANDUNSUPP(-1348L),

	/*
	 * @since v3.30
	 */
	SYNCHRONIZEUNSUPP(-1349L);

	private final long value;

	private CimError(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}