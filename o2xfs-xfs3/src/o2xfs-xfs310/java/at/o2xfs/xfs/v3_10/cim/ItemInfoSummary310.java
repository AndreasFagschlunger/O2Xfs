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

package at.o2xfs.xfs.v3_10.cim;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.cim.Level;
import at.o2xfs.xfs.win32.XfsWord;

public class ItemInfoSummary310 extends Struct {

	protected final XfsWord<Level> level = new XfsWord<>(Level.class);
	protected final USHORT numOfItems = new USHORT();

	protected ItemInfoSummary310() {
		add(level);
		add(numOfItems);
	}

	public ItemInfoSummary310(Pointer p) {
		this();
		assignBuffer(p);
	}

	public ItemInfoSummary310(ItemInfoSummary310 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(ItemInfoSummary310 copy) {
		level.set(copy.getLevel());
		numOfItems.set(copy.getNumOfItems());
	}

	public Level getLevel() {
		return level.get();
	}

	public int getNumOfItems() {
		return numOfItems.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getLevel()).append(getNumOfItems()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ItemInfoSummary310) {
			ItemInfoSummary310 itemInfoSummary310 = (ItemInfoSummary310) obj;
			return new EqualsBuilder().append(getLevel(), itemInfoSummary310.getLevel()).append(getNumOfItems(), itemInfoSummary310.getNumOfItems()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("level", getLevel()).append("numOfItems", getNumOfItems()).toString();
	}
}
