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

package at.o2xfs.xfs.ptr;

import org.apache.commons.lang.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Structure;
import at.o2xfs.win32.USHORT;
import at.o2xfs.win32.WORD;
import at.o2xfs.xfs.util.XfsConstants;

public class WFSPTRRETRACTBINS extends Structure {

	private final WORD retractBin = new WORD();
	private final USHORT retractCount = new USHORT();

	public WFSPTRRETRACTBINS() {
		add(retractBin);
		add(retractCount);
	}

	public WFSPTRRETRACTBINS(final Pointer p) {
		this();
		useBuffer(p);
	}

	public WFSPTRRETRACTBINS(final WFSPTRRETRACTBINS bin) {
		this();
		allocate();
		retractBin.put(bin.retractBin);
		retractCount.put(bin.retractCount);
	}

	/**
	 * Specifies the state of the printer retract bin as one of the following
	 * values: {@link PTRRetractBin}
	 */
	public PTRRetractBin getRetractBin() {
		return XfsConstants.valueOf(retractBin, PTRRetractBin.class);
	}

	/**
	 * The number of media retracted to this bin. This value is persistent; it
	 * may be reset to zero by the WFS_CMD_PTR_RESET_COUNT command.
	 */
	public int getRetractCount() {
		return retractCount.intValue();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("retractBin", getRetractBin())
				.append("retractCount", getRetractCount()).toString();
	}

}
