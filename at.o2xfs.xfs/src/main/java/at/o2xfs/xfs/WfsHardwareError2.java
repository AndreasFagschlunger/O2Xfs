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

package at.o2xfs.xfs;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.common.Bytes;
import at.o2xfs.win32.ByteArray;
import at.o2xfs.win32.DWORD;
import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;

public class WfsHardwareError2 extends Struct {

	protected final LPSTR logicalName = new LPSTR();
	protected final LPSTR workstationName = new LPSTR();
	protected final LPSTR appId = new LPSTR();
	protected final DWORD length = new DWORD();
	protected final Pointer description = new Pointer();

	protected WfsHardwareError2() {
		super();
	}

	public WfsHardwareError2(Pointer p) {
		addFields();
		assignBuffer(p);
	}

	public WfsHardwareError2(WfsHardwareError2 copy) {
		addFields();
		logicalName.set(copy.getLogicalName());
		workstationName.set(copy.getWorkstationName());
		appId.set(copy.getAppId());
		length.set(copy.length);
		description.pointTo(new ByteArray(copy.getDescription()));
	}

	private void addFields() {
		add(logicalName);
		add(workstationName);
		add(appId);
		add(length);
		add(description);
	}

	public String getLogicalName() {
		return logicalName.get();
	}

	public String getWorkstationName() {
		return workstationName.get();
	}

	public String getAppId() {
		return appId.get();
	}

	public byte[] getDescription() {
		byte[] result = Bytes.EMPTY;
		if (length.intValue() > 0) {
			result = description.buffer(length.intValue()).get();
		}
		return result;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(getLogicalName())
				.append(getWorkstationName())
				.append(getAppId())
				.append(getSize())
				.append(getDescription())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof WfsHardwareError2) {
			WfsHardwareError2 hwError = (WfsHardwareError2) obj;
			return new EqualsBuilder()
					.append(getLogicalName(), hwError.getLogicalName())
					.append(getWorkstationName(), hwError.getWorkstationName())
					.append(getAppId(), hwError.getAppId())
					.append(getSize(), hwError.getSize())
					.append(getDescription(), hwError.getDescription())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("logicalName", getLogicalName())
				.append("workstationName", getWorkstationName())
				.append("appId", getAppId())
				.append("length", length.intValue())
				.append("description", getDescription())
				.toString();
	}
}