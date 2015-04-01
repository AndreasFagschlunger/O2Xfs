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

package at.o2xfs.xfs.ptr;

import at.o2xfs.xfs.XfsConstant;

public enum PTRError implements XfsConstant {

	WFS_ERR_PTR_FORMNOTFOUND(-100L),
	WFS_ERR_PTR_FIELDNOTFOUND(-101L),
	WFS_ERR_PTR_NOMEDIAPRESENT(-102L),
	WFS_ERR_PTR_READNOTSUPPORTED(-103L),
	WFS_ERR_PTR_FLUSHFAIL(-104L),
	WFS_ERR_PTR_MEDIAOVERFLOW(-105L),
	WFS_ERR_PTR_FIELDSPECFAILURE(-106L),
	WFS_ERR_PTR_FIELDERROR(-107L),
	WFS_ERR_PTR_MEDIANOTFOUND(-108L),
	WFS_ERR_PTR_EXTENTNOTSUPPORTED(-109L),
	WFS_ERR_PTR_MEDIAINVALID(-110L),
	WFS_ERR_PTR_FORMINVALID(-111L),
	WFS_ERR_PTR_FIELDINVALID(-112L),
	WFS_ERR_PTR_MEDIASKEWED(-113L),
	WFS_ERR_PTR_RETRACTBINFULL(-114L),
	WFS_ERR_PTR_STACKERFULL(-115L),
	WFS_ERR_PTR_PAGETURNFAIL(-116L),
	WFS_ERR_PTR_MEDIATURNFAIL(-117L);

	private long value = 0;

	private PTRError(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}

}