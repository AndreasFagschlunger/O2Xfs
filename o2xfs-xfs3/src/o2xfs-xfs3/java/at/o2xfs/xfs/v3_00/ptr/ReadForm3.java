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

package at.o2xfs.xfs.v3_00.ptr;

import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.LPZZSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.xfs.ptr.MediaControl;
import at.o2xfs.xfs.win32.XfsDWordBitmask;

public class ReadForm3 extends Struct {

	protected final LPSTR formName = new LPSTR();
	protected final LPZZSTR fieldNames = new LPZZSTR();
	protected final LPSTR mediaName = new LPSTR();
	protected final XfsDWordBitmask<MediaControl> mediaControl = new XfsDWordBitmask<>(MediaControl.class);

	protected ReadForm3() {
		add(formName);
		add(fieldNames);
		add(mediaName);
		add(mediaControl);
	}

	public ReadForm3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public ReadForm3(ReadForm3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(ReadForm3 copy) {
		formName.set(copy.getFormName());
		Optional<String[]> fieldNames = copy.getFieldNames();
		if (fieldNames.isPresent()) {
			this.fieldNames.set(fieldNames.get());
		}
		Optional<String> mediaName = copy.getMediaName();
		if (mediaName.isPresent()) {
			this.mediaName.set(mediaName.get());
		}
		mediaControl.set(copy.getMediaControl());
	}

	public String getFormName() {
		return formName.get();
	}

	public Optional<String[]> getFieldNames() {
		Optional<String[]> result = Optional.empty();
		if (!Pointer.NULL.equals(fieldNames)) {
			result = Optional.of(fieldNames.get());
		}
		return result;
	}

	public Optional<String> getMediaName() {
		Optional<String> result = Optional.empty();
		if (!Pointer.NULL.equals(mediaName)) {
			result = Optional.of(mediaName.get());
		}
		return result;
	}

	public Set<MediaControl> getMediaControl() {
		return mediaControl.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(getFormName())
				.append(getFieldNames().orElse(null))
				.append(getMediaName())
				.append(getMediaControl())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ReadForm3) {
			ReadForm3 readForm3 = (ReadForm3) obj;
			return new EqualsBuilder()
					.append(getFormName(), readForm3.getFormName())
					.append(getFieldNames().orElse(null), readForm3.getFieldNames().orElse(null))
					.append(getMediaName(), readForm3.getMediaName())
					.append(getMediaControl(), readForm3.getMediaControl())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("formName", getFormName())
				.append("fieldNames", getFieldNames())
				.append("mediaName", getMediaName())
				.append("mediaControl", getMediaControl())
				.toString();
	}
}
