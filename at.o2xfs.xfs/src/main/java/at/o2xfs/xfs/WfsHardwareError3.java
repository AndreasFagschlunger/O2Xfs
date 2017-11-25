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

import at.o2xfs.win32.ByteArray;
import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.win32.XfsDWord;

public class WfsHardwareError3 extends WfsHardwareError2 {

	protected final LPSTR physicalName = new LPSTR();
	protected final XfsDWord<XfsErrorAction> action = new XfsDWord<>(XfsErrorAction.class);

	private WfsHardwareError3() {
		add(logicalName);
		add(physicalName);
		add(workstationName);
		add(appId);
		add(action);
		add(length);
		add(description);
	}

	public WfsHardwareError3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public WfsHardwareError3(WfsHardwareError3 copy) {
		this();
		allocate();
		logicalName.set(copy.getLogicalName());
		physicalName.set(copy.getPhysicalName());
		workstationName.set(copy.getWorkstationName());
		appId.set(copy.getAppId());
		action.set(copy.getAction());
		length.set(copy.length);
		description.pointTo(new ByteArray(copy.getDescription()));
	}

	public String getPhysicalName() {
		return physicalName.get();
	}

	public XfsErrorAction getAction() {
		return action.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(getLogicalName())
				.append(getPhysicalName())
				.append(getWorkstationName())
				.append(getAppId())
				.append(getAction())
				.append(getSize())
				.append(getDescription())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof WfsHardwareError3) {
			WfsHardwareError3 hwError = (WfsHardwareError3) obj;
			return new EqualsBuilder()
					.append(getLogicalName(), hwError.getLogicalName())
					.append(getPhysicalName(), hwError.getPhysicalName())
					.append(getWorkstationName(), hwError.getWorkstationName())
					.append(getAppId(), hwError.getAppId())
					.append(getAction(), hwError.getAction())
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
				.append("physicalName", getPhysicalName())
				.append("workstationName", getWorkstationName())
				.append("appId", getAppId())
				.append("action", getAction())
				.append("length", length.intValue())
				.append("description", getDescription())
				.toString();
	}
}