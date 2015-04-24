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

import at.o2xfs.win32.BOOL;
import at.o2xfs.win32.CHAR;
import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.LPZZSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.WORD;
import at.o2xfs.xfs.util.XfsConstants;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class WFSIDCFORM
		extends Struct {

	private final LPSTR formName = new LPSTR();
	private final CHAR fieldSeparatorTrack1 = new CHAR();
	private final CHAR fieldSeparatorTrack2 = new CHAR();
	private final CHAR fieldSeparatorTrack3 = new CHAR();
	private final WORD action = new WORD();
	private final LPSTR tracks = new LPSTR();
	private final BOOL secure = new BOOL();
	private final LPZZSTR track1Fields = new LPZZSTR();
	private final LPZZSTR track2Fields = new LPZZSTR();
	private final LPZZSTR track3Fields = new LPZZSTR();

	private WFSIDCFORM() {
		add(formName);
		add(fieldSeparatorTrack1);
		add(fieldSeparatorTrack2);
		add(fieldSeparatorTrack3);
		add(action);
		add(tracks);
		add(secure);
		add(track1Fields);
		add(track2Fields);
		add(track3Fields);
	}

	public WFSIDCFORM(final Pointer pForm) {
		this();
		assignBuffer(pForm);
	}

	public String getFormName() {
		return formName.toString();
	}

	public char getFieldSeparatorTrack1() {
		return fieldSeparatorTrack1.charValue();
	}

	public char getFieldSeparatorTrack2() {
		return fieldSeparatorTrack2.charValue();
	}

	public char getFieldSeparatorTrack3() {
		return fieldSeparatorTrack3.charValue();
	}

	public IDCAction getAction() {
		return XfsConstants.valueOf(action, IDCAction.class);
	}

	public String getTracks() {
		return tracks.toString();
	}

	public boolean isSecure() {
		return secure.booleanValue();
	}

	public String[] getTrack1Fields() {
		return track1Fields.values();
	}

	public String[] getTrack2Fields() {
		return track2Fields.values();
	}

	public String[] getTrack3Fields() {
		return track3Fields.values();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("formName", getFormName())
										.append("fieldSeparatorTrack1", getFieldSeparatorTrack1())
										.append("fieldSeparatorTrack2", getFieldSeparatorTrack2())
										.append("fieldSeparatorTrack3", getFieldSeparatorTrack3())
										.append("action", getAction())
										.append("tracks", getTracks())
										.append("track1Fields", getTrack1Fields())
										.append("track2Fields", getTrack2Fields())
										.append("track3Fields", getTrack3Fields())
										.toString();
	}
}