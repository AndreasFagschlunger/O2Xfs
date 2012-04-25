/*
 * Copyright (c) 2012, Andreas Fagschlunger. All rights reserved.
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

package at.o2xfs.win32;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <code>ZList</code>
 * 
 * A class representing a null-terminated list of pointers.
 * 
 * @author Andreas Fagschlunger
 */
public class ZList extends Type implements Iterable<Pointer> {

	private List<Pointer> pointers = null;

	private List<? extends Type> types = null;

	public ZList(List<? extends Type> types) {
		this.types = types;
		createPointerList();
		allocate();
		setAddresses();
	}

	public ZList(Pointer pointer) {
		pointers = new ArrayList<Pointer>();
		Pointer p = null;
		int capacity = 0;
		while (true) {
			p = new Pointer();
			capacity += p.getSize();
			p.useBuffer(pointer.get(capacity), capacity - p.getSize());
			if (Pointer.NULL.equals(p)) {
				break;
			}
			pointers.add(p);
		}
	}

	private void createPointerList() {
		pointers = new ArrayList<Pointer>(types.size());
		for (int i = 0; i < types.size(); i++) {
			pointers.add(new Pointer());
		}
	}

	private void setAddresses() {
		for (int i = 0; i < types.size(); i++) {
			Pointer p = pointers.get(i);
			p.pointTo(types.get(i));
		}
	}

	@Override
	public void allocate() {
		super.allocate();
		int offset = getOffset();
		for (int i = 0; i < pointers.size(); i++) {
			Pointer p = pointers.get(i);
			p.useBuffer(buffer(), offset);
			offset += p.getSize();
		}
		pointers.get(pointers.size() - 1).useBuffer(buffer(), offset);
	}

	@Override
	public int getSize() {
		int size = Pointer.NULL.getSize();
		for (Pointer p : pointers) {
			size += p.getSize();
		}
		return size;
	}

	@Override
	public Iterator<Pointer> iterator() {
		return pointers.iterator();
	}

}
