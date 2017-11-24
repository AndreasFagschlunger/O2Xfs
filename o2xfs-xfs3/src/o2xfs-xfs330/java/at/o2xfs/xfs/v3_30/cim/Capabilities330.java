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

package at.o2xfs.xfs.v3_30.cim;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.BOOL;
import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.cim.CimExecuteCommand;
import at.o2xfs.xfs.v3_20.cim.Capabilities320;

public class Capabilities330 extends Capabilities320 {

	protected final BOOL deplete = new BOOL();
	protected final BOOL blacklist = new BOOL();
	protected final Pointer synchronizableCommands = new Pointer();

	protected Capabilities330() {
		add(deplete);
		add(blacklist);
		add(synchronizableCommands);
	}

	public Capabilities330(Pointer p) {
		this();
		assignBuffer(p);
	}

	public Capabilities330(Capabilities330 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(Capabilities330 copy) {
		super.set(copy);
		deplete.set(copy.isDeplete());
		blacklist.set(copy.isBlacklist());
		synchronizableCommands.pointTo(new SynchronizableCommands(copy.getSynchronizableCommands()));
	}

	public boolean isDeplete() {
		return deplete.get();
	}

	public boolean isBlacklist() {
		return blacklist.get();
	}

	public CimExecuteCommand[] getSynchronizableCommands() {
		return new SynchronizableCommands(synchronizableCommands).get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().appendSuper(super.hashCode()).append(isDeplete()).append(isBlacklist()).append(getSynchronizableCommands()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Capabilities330) {
			Capabilities330 capabilities330 = (Capabilities330) obj;
			return new EqualsBuilder().appendSuper(super.equals(obj)).append(isDeplete(), capabilities330.isDeplete()).append(isBlacklist(), capabilities330.isBlacklist())
					.append(getSynchronizableCommands(), capabilities330.getSynchronizableCommands()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).appendSuper(super.toString()).append("deplete", isDeplete()).append("blacklist", isBlacklist())
				.append("synchronizableCommands", getSynchronizableCommands()).toString();
	}
}
