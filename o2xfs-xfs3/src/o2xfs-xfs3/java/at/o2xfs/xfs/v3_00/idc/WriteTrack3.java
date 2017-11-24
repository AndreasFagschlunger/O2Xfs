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

import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.xfs.idc.WriteMethod;
import at.o2xfs.xfs.win32.XfsWord;

public class WriteTrack3 extends Struct {

	protected final LPSTR formName = new LPSTR();
	protected final LPSTR trackData = new LPSTR();
	protected final XfsWord<WriteMethod> writeMethod = new XfsWord<>(WriteMethod.class);

	protected WriteTrack3() {
		add(formName);
		add(trackData);
		add(writeMethod);
	}

	public WriteTrack3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public WriteTrack3(WriteTrack3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(WriteTrack3 copy) {
		formName.set(copy.getFormName());
		trackData.set(copy.getTrackData());
		writeMethod.set(copy.getWriteMethod());
	}

	public String getFormName() {
		return formName.get();
	}

	public String getTrackData() {
		return trackData.get();
	}

	public WriteMethod getWriteMethod() {
		return writeMethod.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getFormName()).append(getTrackData()).append(getWriteMethod()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof WriteTrack3) {
			WriteTrack3 writeTrack3 = (WriteTrack3) obj;
			return new EqualsBuilder()
					.append(getFormName(), writeTrack3.getFormName())
					.append(getTrackData(), writeTrack3.getTrackData())
					.append(getWriteMethod(), writeTrack3.getWriteMethod())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("formName", getFormName())
				.append("trackData", getTrackData())
				.append("writeMethod", getWriteMethod())
				.toString();
	}
}
