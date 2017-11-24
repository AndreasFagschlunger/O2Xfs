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

package at.o2xfs.xfs.v3_20.idc;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.CHAR;
import at.o2xfs.win32.LPZZSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.v3_00.idc.Form3;

public class Form320 extends Form3 {

	protected final LPZZSTR frontTrack1Fields = new LPZZSTR();
	protected final CHAR fieldSeparatorFrontTrack1 = new CHAR();
	protected final LPZZSTR jIS1Track1Fields = new LPZZSTR();
	protected final LPZZSTR jIS1Track3Fields = new LPZZSTR();
	protected final CHAR fieldSeparatorJIS1Track1 = new CHAR();
	protected final CHAR fieldSeparatorJIS1Track3 = new CHAR();

	protected Form320() {
		add(frontTrack1Fields);
		add(fieldSeparatorFrontTrack1);
		add(jIS1Track1Fields);
		add(jIS1Track3Fields);
		add(fieldSeparatorJIS1Track1);
		add(fieldSeparatorJIS1Track3);
	}

	public Form320(Pointer p) {
		this();
		assignBuffer(p);
	}

	public Form320(Form320 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(Form320 copy) {
		super.set(copy);
		frontTrack1Fields.set(copy.getFrontTrack1Fields());
		fieldSeparatorFrontTrack1.set(copy.getFieldSeparatorFrontTrack1());
		jIS1Track1Fields.set(copy.getJIS1Track1Fields());
		jIS1Track3Fields.set(copy.getJIS1Track3Fields());
		fieldSeparatorJIS1Track1.set(copy.getFieldSeparatorJIS1Track1());
		fieldSeparatorJIS1Track3.set(copy.getFieldSeparatorJIS1Track3());
	}

	public String[] getFrontTrack1Fields() {
		return frontTrack1Fields.get();
	}

	public char getFieldSeparatorFrontTrack1() {
		return fieldSeparatorFrontTrack1.get();
	}

	public String[] getJIS1Track1Fields() {
		return jIS1Track1Fields.get();
	}

	public String[] getJIS1Track3Fields() {
		return jIS1Track3Fields.get();
	}

	public char getFieldSeparatorJIS1Track1() {
		return fieldSeparatorJIS1Track1.get();
	}

	public char getFieldSeparatorJIS1Track3() {
		return fieldSeparatorJIS1Track3.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.appendSuper(super.hashCode())
				.append(getFrontTrack1Fields())
				.append(getFieldSeparatorFrontTrack1())
				.append(getJIS1Track1Fields())
				.append(getJIS1Track3Fields())
				.append(getFieldSeparatorJIS1Track1())
				.append(getFieldSeparatorJIS1Track3())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Form320) {
			Form320 form320 = (Form320) obj;
			return new EqualsBuilder()
					.appendSuper(super.equals(obj))
					.append(getFrontTrack1Fields(), form320.getFrontTrack1Fields())
					.append(getFieldSeparatorFrontTrack1(), form320.getFieldSeparatorFrontTrack1())
					.append(getJIS1Track1Fields(), form320.getJIS1Track1Fields())
					.append(getJIS1Track3Fields(), form320.getJIS1Track3Fields())
					.append(getFieldSeparatorJIS1Track1(), form320.getFieldSeparatorJIS1Track1())
					.append(getFieldSeparatorJIS1Track3(), form320.getFieldSeparatorJIS1Track3())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.appendSuper(super.toString())
				.append("frontTrack1Fields", getFrontTrack1Fields())
				.append("fieldSeparatorFrontTrack1", getFieldSeparatorFrontTrack1())
				.append("jIS1Track1Fields", getJIS1Track1Fields())
				.append("jIS1Track3Fields", getJIS1Track3Fields())
				.append("fieldSeparatorJIS1Track1", getFieldSeparatorJIS1Track1())
				.append("fieldSeparatorJIS1Track3", getFieldSeparatorJIS1Track3())
				.toString();
	}
}
