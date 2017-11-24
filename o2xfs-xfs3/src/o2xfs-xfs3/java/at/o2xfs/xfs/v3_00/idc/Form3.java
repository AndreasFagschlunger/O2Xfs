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

package at.o2xfs.xfs.v3_00.idc;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.BOOL;
import at.o2xfs.win32.CHAR;
import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.xfs.idc.FormAction;
import at.o2xfs.xfs.win32.XfsWord;

public class Form3 extends Struct {

	protected final LPSTR formName = new LPSTR();
	protected final CHAR fieldSeparatorTrack1 = new CHAR();
	protected final CHAR fieldSeparatorTrack2 = new CHAR();
	protected final CHAR fieldSeparatorTrack3 = new CHAR();
	protected final XfsWord<FormAction> action = new XfsWord<>(FormAction.class);
	protected final LPSTR tracks = new LPSTR();
	protected final BOOL secure = new BOOL();
	protected final LPSTR track1Fields = new LPSTR();
	protected final LPSTR track2Fields = new LPSTR();
	protected final LPSTR track3Fields = new LPSTR();

	protected Form3() {
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

	public Form3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public Form3(Form3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(Form3 copy) {
		formName.set(copy.getFormName());
		fieldSeparatorTrack1.set(copy.getFieldSeparatorTrack1());
		fieldSeparatorTrack2.set(copy.getFieldSeparatorTrack2());
		fieldSeparatorTrack3.set(copy.getFieldSeparatorTrack3());
		action.set(copy.getAction());
		tracks.set(copy.getTracks());
		secure.set(copy.isSecure());
		track1Fields.set(copy.getTrack1Fields());
		track2Fields.set(copy.getTrack2Fields());
		track3Fields.set(copy.getTrack3Fields());
	}

	public String getFormName() {
		return formName.get();
	}

	public char getFieldSeparatorTrack1() {
		return fieldSeparatorTrack1.get();
	}

	public char getFieldSeparatorTrack2() {
		return fieldSeparatorTrack2.get();
	}

	public char getFieldSeparatorTrack3() {
		return fieldSeparatorTrack3.get();
	}

	public FormAction getAction() {
		return action.get();
	}

	public String getTracks() {
		return tracks.get();
	}

	public boolean isSecure() {
		return secure.get();
	}

	public String getTrack1Fields() {
		return track1Fields.get();
	}

	public String getTrack2Fields() {
		return track2Fields.get();
	}

	public String getTrack3Fields() {
		return track3Fields.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(getFormName())
				.append(getFieldSeparatorTrack1())
				.append(getFieldSeparatorTrack2())
				.append(getFieldSeparatorTrack3())
				.append(getAction())
				.append(getTracks())
				.append(isSecure())
				.append(getTrack1Fields())
				.append(getTrack2Fields())
				.append(getTrack3Fields())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Form3) {
			Form3 form3 = (Form3) obj;
			return new EqualsBuilder()
					.append(getFormName(), form3.getFormName())
					.append(getFieldSeparatorTrack1(), form3.getFieldSeparatorTrack1())
					.append(getFieldSeparatorTrack2(), form3.getFieldSeparatorTrack2())
					.append(getFieldSeparatorTrack3(), form3.getFieldSeparatorTrack3())
					.append(getAction(), form3.getAction())
					.append(getTracks(), form3.getTracks())
					.append(isSecure(), form3.isSecure())
					.append(getTrack1Fields(), form3.getTrack1Fields())
					.append(getTrack2Fields(), form3.getTrack2Fields())
					.append(getTrack3Fields(), form3.getTrack3Fields())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("formName", getFormName())
				.append("fieldSeparatorTrack1", getFieldSeparatorTrack1())
				.append("fieldSeparatorTrack2", getFieldSeparatorTrack2())
				.append("fieldSeparatorTrack3", getFieldSeparatorTrack3())
				.append("action", getAction())
				.append("tracks", getTracks())
				.append("secure", isSecure())
				.append("track1Fields", getTrack1Fields())
				.append("track2Fields", getTrack2Fields())
				.append("track3Fields", getTrack3Fields())
				.toString();
	}
}
