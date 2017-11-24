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

import java.util.Optional;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.v3_10.cim.ItemInfo310;

public class ItemInfo330 extends ItemInfo310 {

	protected final LPSTR imageFileName = new LPSTR();

	protected ItemInfo330() {
		add(imageFileName);
	}

	public ItemInfo330(Pointer p) {
		this();
		assignBuffer(p);
	}

	public ItemInfo330(ItemInfo330 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(ItemInfo330 copy) {
		super.set(copy);
		Optional<String> imageFileName = copy.getImageFileName();
		if (imageFileName.isPresent()) {
			this.imageFileName.set(copy.getImageFileName().get());
		}
	}

	public Optional<String> getImageFileName() {
		return Optional.ofNullable(imageFileName.get());
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().appendSuper(super.hashCode()).append(getImageFileName()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ItemInfo330) {
			ItemInfo330 itemInfo330 = (ItemInfo330) obj;
			return new EqualsBuilder().appendSuper(super.equals(obj)).append(getImageFileName(), itemInfo330.getImageFileName()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).appendSuper(super.toString()).append("imageFileName", getImageFileName()).toString();
	}
}
