/*
 * Copyright (c) 2018, Andreas Fagschlunger. All rights reserved.
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

package at.o2xfs.xfs.ptr;

import at.o2xfs.xfs.XfsConstant;

public enum PtrError implements XfsConstant {

	/*
	 * @since v3.00
	 */
	FORMNOTFOUND(-100L),

	/*
	 * @since v3.00
	 */
	FIELDNOTFOUND(-101L),

	/*
	 * @since v3.00
	 */
	NOMEDIAPRESENT(-102L),

	/*
	 * @since v3.00
	 */
	READNOTSUPPORTED(-103L),

	/*
	 * @since v3.00
	 */
	FLUSHFAIL(-104L),

	/*
	 * @since v3.00
	 */
	MEDIAOVERFLOW(-105L),

	/*
	 * @since v3.00
	 */
	FIELDSPECFAILURE(-106L),

	/*
	 * @since v3.00
	 */
	FIELDERROR(-107L),

	/*
	 * @since v3.00
	 */
	MEDIANOTFOUND(-108L),

	/*
	 * @since v3.00
	 */
	EXTENTNOTSUPPORTED(-109L),

	/*
	 * @since v3.00
	 */
	MEDIAINVALID(-110L),

	/*
	 * @since v3.00
	 */
	FORMINVALID(-111L),

	/*
	 * @since v3.00
	 */
	FIELDINVALID(-112L),

	/*
	 * @since v3.00
	 */
	MEDIASKEWED(-113L),

	/*
	 * @since v3.00
	 */
	RETRACTBINFULL(-114L),

	/*
	 * @since v3.00
	 */
	STACKERFULL(-115L),

	/*
	 * @since v3.00
	 */
	PAGETURNFAIL(-116L),

	/*
	 * @since v3.00
	 */
	MEDIATURNFAIL(-117L),

	/*
	 * @since v3.00
	 */
	SHUTTERFAIL(-118L),

	/*
	 * @since v3.00
	 */
	MEDIAJAMMED(-119L),

	/*
	 * @since v3.00
	 */
	FILE_IO_ERROR(-120L),

	/*
	 * @since v3.00
	 */
	CHARSETDATA(-121L),

	/*
	 * @since v3.00
	 */
	PAPERJAMMED(-122L),

	/*
	 * @since v3.00
	 */
	PAPEROUT(-123L),

	/*
	 * @since v3.00
	 */
	INKOUT(-124L),

	/*
	 * @since v3.00
	 */
	TONEROUT(-125L),

	/*
	 * @since v3.00
	 */
	LAMPINOP(-126L),

	/*
	 * @since v3.00
	 */
	SOURCEINVALID(-127L),

	/*
	 * @since v3.00
	 */
	SEQUENCEINVALID(-128L),

	/*
	 * @since v3.00
	 */
	MEDIASIZE(-129L),

	/*
	 * @since v3.10
	 */
	INVALID_PORT(-130L),

	/*
	 * @since v3.10
	 */
	MEDIARETAINED(-131L),

	/*
	 * @since v3.10
	 */
	BLACKMARK(-132L),

	/*
	 * @since v3.10
	 */
	DEFINITIONEXISTS(-133L),

	/*
	 * @since v3.10
	 */
	MEDIAREJECTED(-134L),

	/*
	 * @since v3.10
	 */
	MEDIARETRACTED(-135L),

	/*
	 * @since v3.10
	 */
	MSFERROR(-136L),

	/*
	 * @since v3.10
	 */
	NOMSF(-137L),

	/*
	 * @since v3.10
	 */
	FILENOTFOUND(-138L),

	/*
	 * @since v3.10
	 */
	POWERSAVETOOSHORT(-139L),

	/*
	 * @since v3.10
	 */
	POWERSAVEMEDIAPRESENT(-140L),

	/*
	 * @since v3.20
	 */
	PASSBOOKCLOSED(-141L),

	/*
	 * @since v3.20
	 */
	LASTORFIRSTPAGEREACHED(-142L),

	/*
	 * @since v3.30
	 */
	COMMANDUNSUPP(-143L),

	/*
	 * @since v3.30
	 */
	SYNCHRONIZEUNSUPP(-144L);

	private final long value;

	private PtrError(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}
