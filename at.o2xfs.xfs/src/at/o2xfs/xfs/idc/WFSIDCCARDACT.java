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
import at.o2xfs.xfs.XfsWord;
import at.o2xfs.xfs.util.XfsConstants;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class WFSIDCCARDACT
		extends Struct {

	/**
	 * Specifies which action has been performed with the card. Possible values
	 * are: {@link IDCCardAction#WFS_IDC_CARDEJECTED}, {@link IDCCardAction#WFS_IDC_CARDRETAINED} and
	 * {@link IDCCardAction#WFS_IDC_CARDREADPOSITION}.
	 */
	private XfsWord<IDCCardAction> action = new XfsWord<>(IDCCardAction.class);

	/**
	 * Position of card before being retained or ejected. Possible values are: {@link IDCMedia#WFS_IDC_MEDIAUNKNOWN},
	 * {@link IDCMedia#WFS_IDC_MEDIAPRESENT} and {@link IDCMedia#WFS_IDC_MEDIAENTERING}.
	 */
	private XfsWord<IDCMedia> position = new XfsWord<>(IDCMedia.class);

	public WFSIDCCARDACT() {
		add(action);
		add(position);
	}

	public WFSIDCCARDACT(final Pointer p) {
		this();
		assignBuffer(p);
	}

	public WFSIDCCARDACT(final WFSIDCCARDACT copy) {
		this();
		allocate();
		action.set(copy.getAction());
		position.set(copy.getPosition());
	}

	public IDCCardAction getAction() {
		return XfsConstants.valueOf(action, IDCCardAction.class);
	}

	public void setAction(final IDCCardAction action) {
		this.action.set(action);
	}

	public IDCMedia getPosition() {
		return XfsConstants.valueOf(position, IDCMedia.class);
	}

	public void setPosition(final IDCMedia position) {
		this.position.set(position);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("action", getAction()).append("position", getPosition()).toString();
	}
}