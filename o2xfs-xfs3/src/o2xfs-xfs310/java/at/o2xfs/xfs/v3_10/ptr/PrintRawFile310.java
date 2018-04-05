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

package at.o2xfs.xfs.v3_10.ptr;

import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.xfs.ptr.MediaControl;
import at.o2xfs.xfs.ptr.PaperSource;
import at.o2xfs.xfs.win32.XfsDWord;
import at.o2xfs.xfs.win32.XfsDWordBitmask;

public class PrintRawFile310 extends Struct {

	protected final LPSTR fileName = new LPSTR();
	protected final XfsDWordBitmask<MediaControl> mediaControl = new XfsDWordBitmask<>(MediaControl.class);
	protected final XfsDWord<PaperSource> paperSource = new XfsDWord<>(PaperSource.class);

	protected PrintRawFile310() {
		add(fileName);
		add(mediaControl);
		add(paperSource);
	}

	public PrintRawFile310(Pointer p) {
		this();
		assignBuffer(p);
	}

	public PrintRawFile310(PrintRawFile310 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(PrintRawFile310 copy) {
		fileName.set(copy.getFileName());
		mediaControl.set(copy.getMediaControl());
		paperSource.set(copy.getPaperSource());
	}

	public String getFileName() {
		return fileName.get();
	}

	public Set<MediaControl> getMediaControl() {
		return mediaControl.get();
	}

	public PaperSource getPaperSource() {
		return paperSource.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(getFileName())
				.append(getMediaControl())
				.append(getPaperSource())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PrintRawFile310) {
			PrintRawFile310 printRawFile310 = (PrintRawFile310) obj;
			return new EqualsBuilder()
					.append(getFileName(), printRawFile310.getFileName())
					.append(getMediaControl(), printRawFile310.getMediaControl())
					.append(getPaperSource(), printRawFile310.getPaperSource())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("fileName", getFileName())
				.append("mediaControl", getMediaControl())
				.append("paperSource", getPaperSource())
				.toString();
	}
}
