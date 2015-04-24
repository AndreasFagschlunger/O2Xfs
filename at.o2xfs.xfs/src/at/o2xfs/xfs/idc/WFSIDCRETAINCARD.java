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

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.XfsWord;
import at.o2xfs.xfs.util.XfsConstants;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class WFSIDCRETAINCARD
		extends Struct {

	/**
	 * Total number of ID cards retained up to and including this operation,
	 * since the last {@link IDCExecuteCommand#WFS_CMD_IDC_RESET_COUNT} command
	 * was executed.
	 */
	private USHORT count = new USHORT();

	/**
	 * Position of card; only relevant if card could not be retained. Possible
	 * positions: {@link IDCMedia#WFS_IDC_MEDIAUNKNOWN}, {@link IDCMedia#WFS_IDC_MEDIAPRESENT},
	 * {@link IDCMedia#WFS_IDC_MEDIAENTERING}
	 */
	private XfsWord<IDCMedia> position = new XfsWord<>(IDCMedia.class);

	private WFSIDCRETAINCARD(final boolean allocate) {
		add(count);
		add(position);
		if (allocate) {
			allocate();
		}
	}

	public WFSIDCRETAINCARD(final Pointer p) {
		this(false);
		assignBuffer(p);
	}

	public WFSIDCRETAINCARD(final WFSIDCRETAINCARD retainCard) {
		this(true);
		count.set(retainCard.count);
		position.set(retainCard.getPosition());
	}

	public int getCount() {
		return count.intValue();
	}

	public void setCount(final int count) {
		this.count.set(count);
	}

	public IDCMedia getPosition() {
		return XfsConstants.valueOf(position, IDCMedia.class);
	}

	public void setPosition(final IDCMedia position) {
		this.position.set(position);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("count", getCount()).append("position", getPosition()).toString();
	}

}