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

import at.o2xfs.win32.Struct;
import at.o2xfs.win32.WORD;
import at.o2xfs.win32.ZSTR;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class WFSVersion
		extends Struct {

	private final static int WFSDDESCRIPTION_LEN = 256;
	private final static int WFSDSYSSTATUS_LEN = 256;

	/**
	 * The version number to be used.
	 */
	private WORD version = new WORD();

	/**
	 * The lowest version number that the called DLL can support.
	 */
	private WORD lowVersion = new WORD();

	/**
	 * The highest version number that the called DLL can support.
	 */
	private WORD highVersion = new WORD();

	/**
	 * A null-terminated ASCII string into which the called DLL copies a
	 * description of the implementation. The text (up to 256 characters in
	 * length) may contain any characters: the most likely use that an
	 * application will make of this is to display it (possibly truncated) in a
	 * status message.
	 */
	private ZSTR description = new ZSTR(WFSDDESCRIPTION_LEN + 1);

	/**
	 * A null-terminated ASCII string into which the called DLL copies relevant
	 * status or configuration information. Not to be considered as an extension
	 * of the szDescription field. Used only if the information might be useful
	 * to the user or support staff.
	 */
	private ZSTR systemStatus = new ZSTR(WFSDSYSSTATUS_LEN + 1);

	public WFSVersion() {
		add(version);
		add(lowVersion);
		add(highVersion);
		add(description);
		add(systemStatus);
	}

	public XfsVersion getVersion() {
		return new XfsVersion(version.intValue());
	}

	public XfsVersion getLowVersion() {
		return new XfsVersion(lowVersion.intValue());
	}

	public XfsVersion getHighVersion() {
		return new XfsVersion(highVersion.intValue());
	}

	public String getDescription() {
		return description.toString();
	}

	public String getSystemStatus() {
		return systemStatus.toString();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("version", getVersion())
										.append("lowVersion", getLowVersion())
										.append("highVersion", getHighVersion())
										.append("description", description)
										.append("systemStatus", systemStatus)
										.toString();

	}
}