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

package at.o2xfs.xfs.v3_20.cdm;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;

public class ItemNumberList320 extends Struct {

	protected final USHORT numOfItemNumbers = new USHORT();
	protected final Pointer itemNumber = new Pointer();

	public ItemNumberList320() {
		add(numOfItemNumbers);
		add(itemNumber);
	}

	public ItemNumberList320(Pointer p) {
		this();
		assignBuffer(p);
	}

	public ItemNumberList320(ItemNumberList320 copy) {
		this();
		allocate();
		set(copy);
	}

	public void set(ItemNumberList320 copy) {
		numOfItemNumbers.set(copy.getNumOfItemNumbers());
		itemNumber.pointTo(new ItemNumbers320(copy.getItemNumber()));
	}

	public int getNumOfItemNumbers() {
		return numOfItemNumbers.get();
	}

	public ItemNumber320[] getItemNumber() {
		return new ItemNumbers320(itemNumber, getNumOfItemNumbers()).get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getNumOfItemNumbers()).append(getItemNumber()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ItemNumberList320) {
			ItemNumberList320 itemNumberList = (ItemNumberList320) obj;
			return new EqualsBuilder().append(getNumOfItemNumbers(), itemNumberList.getNumOfItemNumbers()).append(getItemNumber(), itemNumberList.getItemNumber()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("numOfItemNumbers", getNumOfItemNumbers()).append("itemNumber", getItemNumber()).toString();
	}
}
