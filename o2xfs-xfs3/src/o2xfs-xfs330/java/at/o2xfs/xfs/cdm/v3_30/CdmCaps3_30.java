/*
 * Copyright (c) 2016, Andreas Fagschlunger. All rights reserved.
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

package at.o2xfs.xfs.cdm.v3_30;

import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.BOOL;
import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.cdm.CdmExecuteCommand;
import at.o2xfs.xfs.cdm.ItemInfoType;
import at.o2xfs.xfs.cdm.v3_20.CdmCaps3_20;
import at.o2xfs.xfs.win32.XfsDWordBitmask;

public class CdmCaps3_30 extends CdmCaps3_20 {

	protected final XfsDWordBitmask<ItemInfoType> itemInfoTypes = new XfsDWordBitmask<>(ItemInfoType.class);
	protected final BOOL blacklist = new BOOL();
	protected final Pointer synchronizableCommands = new Pointer();

	protected CdmCaps3_30() {
		add(itemInfoTypes);
		add(blacklist);
		add(synchronizableCommands);
	}

	public CdmCaps3_30(Pointer p) {
		this();
		assignBuffer(p);
	}

	public CdmCaps3_30(CdmCaps3_30 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(CdmCaps3_30 copy) {
		super.set(copy);
		itemInfoTypes.set(copy.getItemInfoTypes());
		blacklist.set(copy.isBlacklist());
		synchronizableCommands.pointTo(new SynchronizableCommands(copy.getSynchronizableCommands()));
	}

	public Set<ItemInfoType> getItemInfoTypes() {
		return itemInfoTypes.get();
	}

	public boolean isBlacklist() {
		return blacklist.get();
	}

	public CdmExecuteCommand[] getSynchronizableCommands() {
		return new SynchronizableCommands(synchronizableCommands).get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().appendSuper(super.hashCode()).append(getItemInfoTypes()).append(isBlacklist()).append(getSynchronizableCommands()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CdmCaps3_30) {
			CdmCaps3_30 cdmCaps3_30 = (CdmCaps3_30) obj;
			return new EqualsBuilder().appendSuper(super.equals(obj)).append(getItemInfoTypes(), cdmCaps3_30.getItemInfoTypes()).append(isBlacklist(), cdmCaps3_30.isBlacklist())
					.append(getSynchronizableCommands(), cdmCaps3_30.getSynchronizableCommands()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).appendSuper(super.toString()).append("itemInfoTypes", getItemInfoTypes()).append("blacklist", isBlacklist())
				.append("synchronizableCommands", getSynchronizableCommands()).toString();
	}
}