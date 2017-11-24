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

package at.o2xfs.xfs.v3_30.cdm;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.cdm.ItemInfoType;
import at.o2xfs.xfs.cdm.Level;
import at.o2xfs.xfs.win32.XfsDWord;
import at.o2xfs.xfs.win32.XfsWord;

public class GetItemInfo330 extends Struct {

	protected final XfsWord<Level> level = new XfsWord<>(Level.class);
	protected final USHORT index = new USHORT();
	protected final XfsDWord<ItemInfoType> itemInfoType = new XfsDWord<>(ItemInfoType.class);

	protected GetItemInfo330() {
		add(level);
		add(index);
		add(itemInfoType);
	}

	public GetItemInfo330(Level level, int index, ItemInfoType itemInfoType) {
		this();
		allocate();
		this.level.set(level);
		this.index.set(index);
		this.itemInfoType.set(itemInfoType);
	}

	public GetItemInfo330(Pointer p) {
		this();
		assignBuffer(p);
	}

	public GetItemInfo330(GetItemInfo330 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(GetItemInfo330 copy) {
		level.set(copy.getLevel());
		index.set(copy.getIndex());
		itemInfoType.set(copy.getItemInfoType());
	}

	public Level getLevel() {
		return level.get();
	}

	public int getIndex() {
		return index.get();
	}

	public ItemInfoType getItemInfoType() {
		return itemInfoType.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getLevel()).append(getIndex()).append(getItemInfoType()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GetItemInfo330) {
			GetItemInfo330 getItemInfo = (GetItemInfo330) obj;
			return new EqualsBuilder().append(getLevel(), getItemInfo.getLevel()).append(getIndex(), getItemInfo.getIndex())
					.append(getItemInfoType(), getItemInfo.getItemInfoType()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("level", getLevel()).append("index", getIndex()).append("itemInfoType", getItemInfoType()).toString();
	}
}
