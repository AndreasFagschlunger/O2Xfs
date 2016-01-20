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

import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.ZSTR;
import at.o2xfs.xfs.XfsVersion;
import at.o2xfs.xfs.XfsWord;
import at.o2xfs.xfs.util.XfsConstants;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class WFSIDCWRITETRACK
		extends Struct {

	/**
	 * Points to the name of the form to be used.
	 */
	private LPSTR formName = new LPSTR();

	/**
	 * Points to the data to be used in the form.
	 */
	private LPSTR trackData = new LPSTR();

	/**
	 * Indicates whether a low coercivity or high coercivity magnetic stripe is
	 * being written.
	 *
	 * @since 3.00
	 */
	private XfsWord<IDCWriteMethod> writeMethod = new XfsWord<>(IDCWriteMethod.class);

	public WFSIDCWRITETRACK(final XfsVersion xfsVersion) {
		add(formName);
		add(trackData);
		if (xfsVersion.isGE(XfsVersion.V3_00)) {
			add(writeMethod);
		} else {
			writeMethod.allocate();
		}
	}

	public String getFormName() {
		return formName.toString();
	}

	public void setFormName(final String formName) {
		this.formName.pointTo(new ZSTR(formName));
	}

	public String getTrackData() {
		return trackData.toString();
	}

	public void setTrackData(final String trackData) {
		this.trackData.pointTo(new ZSTR(trackData));
	}

	public IDCWriteMethod getWriteMethod() {
		return XfsConstants.valueOf(writeMethod, IDCWriteMethod.class);
	}

	public void setWriteMethod(final IDCWriteMethod writeMethod) {
		this.writeMethod.set(writeMethod);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("formName", getFormName())
										.append("trackData", getTrackData())
										.append("writeMethod", getWriteMethod())
										.toString();
	}
}