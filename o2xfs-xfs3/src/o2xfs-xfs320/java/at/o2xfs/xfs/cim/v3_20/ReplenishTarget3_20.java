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

package at.o2xfs.xfs.cim.v3_20;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.BOOL;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.ULONG;
import at.o2xfs.win32.USHORT;

public class ReplenishTarget3_20 extends Struct {

	protected final USHORT numberTarget = new USHORT();
	protected final ULONG numberOfItemsToMove = new ULONG();
	protected final BOOL removeAll = new BOOL();

	protected ReplenishTarget3_20() {
		add(numberTarget);
		add(numberOfItemsToMove);
		add(removeAll);
	}

	public ReplenishTarget3_20(Pointer p) {
		this();
		assignBuffer(p);
	}

	public ReplenishTarget3_20(ReplenishTarget3_20 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(ReplenishTarget3_20 copy) {
		numberTarget.set(copy.getNumberTarget());
		numberOfItemsToMove.set(copy.getNumberOfItemsToMove());
		removeAll.set(copy.isRemoveAll());
	}

	public int getNumberTarget() {
		return numberTarget.get();
	}

	public long getNumberOfItemsToMove() {
		return numberOfItemsToMove.get();
	}

	public boolean isRemoveAll() {
		return removeAll.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getNumberTarget()).append(getNumberOfItemsToMove()).append(isRemoveAll()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ReplenishTarget3_20) {
			ReplenishTarget3_20 replenishTarget3_20 = (ReplenishTarget3_20) obj;
			return new EqualsBuilder().append(getNumberTarget(), replenishTarget3_20.getNumberTarget())
					.append(getNumberOfItemsToMove(), replenishTarget3_20.getNumberOfItemsToMove()).append(isRemoveAll(), replenishTarget3_20.isRemoveAll()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("numberTarget", getNumberTarget()).append("numberOfItemsToMove", getNumberOfItemsToMove()).append("removeAll", isRemoveAll())
				.toString();
	}
}