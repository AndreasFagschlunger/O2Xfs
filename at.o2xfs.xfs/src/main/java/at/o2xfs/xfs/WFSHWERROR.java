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

import at.o2xfs.win32.ByteArray;
import at.o2xfs.win32.DWORD;
import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.ZSTR;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Andreas Fagschlunger
 */
public class WFSHWERROR
		extends Struct {

	/**
	 * Pointer to the logical service name of the service that generated the
	 * error (if any)
	 */
	private final LPSTR logicalName = new LPSTR();

	/**
	 * Pointer to the physical service name of the service that generated the
	 * error (if any)
	 *
	 * @since 3.00
	 */
	private final LPSTR physicalName = new LPSTR();

	/**
	 * Pointer to the the name of the workstation in which the logical service
	 * name is defined (if any)
	 */
	private final LPSTR workstationName = new LPSTR();

	/**
	 * Pointer to the application ID associated with the session that generated
	 * the error (if any)
	 */
	private final LPSTR appID = new LPSTR();

	/**
	 * The action required to manage the error. Possible values are:
	 *
	 * @since 3.00
	 */
	private final XfsWord<XfsErrorAction> action = new XfsWord<XfsErrorAction>(XfsErrorAction.class);

	/**
	 * The size in bytes of the following description
	 */
	private final DWORD size = new DWORD();

	/**
	 * Pointer to a vendor-specific description of the error
	 */
	private final Pointer description = new Pointer();

	public WFSHWERROR(final XfsVersion xfsVersion) {
		add(logicalName);
		if (xfsVersion.isGE(XfsVersion.V3_00)) {
			add(physicalName);
		} else {
			physicalName.allocate();
		}
		add(workstationName);
		add(appID);
		if (xfsVersion.isGE(XfsVersion.V3_00)) {
			add(action);
		} else {
			action.allocate();
		}
		add(size);
		add(description);
	}

	public WFSHWERROR(final XfsVersion xfsVersion, final Pointer p) {
		this(xfsVersion);
		assignBuffer(p.buffer(getSize()));
	}

	/**
	 * Copy constructor.
	 */
	public WFSHWERROR(final XfsVersion xfsVersion, final WFSHWERROR hwError) {
		this(xfsVersion);
		allocate();
		setLogicalName(hwError.getLogicalName());
		if (xfsVersion.isGE(XfsVersion.V3_00)) {
			setPhysicalName(hwError.getPhysicalName());
		}
		setWorkstationName(hwError.getWorkstationName());
		setAppID(hwError.getAppID());
		if (xfsVersion.isGE(XfsVersion.V3_00)) {
			setAction(hwError.getAction());
		}
		setDescription(hwError.getDescription());
	}

	public String getLogicalName() {
		return logicalName.toString();
	}

	public void setLogicalName(final String logicalName) {
		this.logicalName.pointTo(new ZSTR(logicalName));
	}

	public String getPhysicalName() {
		return physicalName.toString();
	}

	public void setPhysicalName(final String physicalName) {
		this.physicalName.pointTo(new ZSTR(physicalName));
	}

	public String getWorkstationName() {
		return workstationName.toString();
	}

	public void setWorkstationName(final String workstationName) {
		this.workstationName.pointTo(new ZSTR(workstationName));
	}

	public String getAppID() {
		return appID.toString();
	}

	public void setAppID(final String appID) {
		this.appID.pointTo(new ZSTR(appID));
	}

	public XfsErrorAction getAction() {
		return action.get();
	}

	public void setAction(final XfsErrorAction aAction) {
		action.set(aAction);
	}

	public byte[] getDescription() {
		if (Pointer.NULL.equals(description)) {
			return null;
		}
		final int capacity = (int) size.longValue();
		return description.buffer(capacity).get();
	}

	public void setDescription(final byte[] description) {
		size.set(description.length);
		this.description.pointTo(new ByteArray(description));
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("logicalName", getLogicalName())
										.append("physicalName", getPhysicalName())
										.append("workstationName", getWorkstationName())
										.append("appID", getAppID())
										.append("action", getAction())
										.append("size", size)
										.append("description", getDescription())
										.toString();
	}
}