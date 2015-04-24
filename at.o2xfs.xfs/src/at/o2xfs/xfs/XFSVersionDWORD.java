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

package at.o2xfs.xfs;

import at.o2xfs.win32.DWORD;
import at.o2xfs.xfs.util.XFSUtils;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Andreas Fagschlunger
 */
public class XFSVersionDWORD
		extends DWORD {

	public XFSVersionDWORD() {
		super();
	}

	public XFSVersionDWORD(final String version) {
		this(version, version);
	}

	public XFSVersionDWORD(final String lowVersion, final String highVersion) {
		super();
		allocate();
		setLowVersion(lowVersion);
		setHighVersion(highVersion);
	}

	public void setLowVersion(final XfsVersion lowVersion) {
		highWord.set(lowVersion.intValue());
	}

	public void setLowVersion(final String lowVersion) {
		highWord.set(XFSUtils.getVersion(lowVersion));
	}

	public void setHighVersion(final XfsVersion highVersion) {
		lowWord.set(highVersion.intValue());
	}

	public void setHighVersion(final String highVersion) {
		lowWord.set(XFSUtils.getVersion(highVersion));
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("lowVersion", XFSUtils.getVersionAsString(highWord.intValue()))
										.append("highVersion", XFSUtils.getVersionAsString(lowWord.intValue()))
										.toString();
	}
}