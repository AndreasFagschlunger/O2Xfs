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

package at.o2xfs.xfs.v3_00.cim;

import at.o2xfs.win32.Buffer;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Type;
import at.o2xfs.win32.USHORT;
import at.o2xfs.win32.ValueType;

public class NoteIDs extends Type implements ValueType<int[]> {

	private final USHORT[] noteIDs;

	public NoteIDs(int[] noteIDs) {
		this.noteIDs = new USHORT[noteIDs.length];
		allocate();
		set(noteIDs);
	}

	public NoteIDs(Pointer aPointer) {
		if (Pointer.NULL.equals(aPointer)) {
			noteIDs = new USHORT[0];
		} else {
			Buffer buffer = null;
			for (int i = 0;; i++) {
				buffer = aPointer.buffer((i * USHORT.SIZE) + USHORT.SIZE);
				Buffer subBuffer = buffer.subBuffer(i * USHORT.SIZE, USHORT.SIZE);
				USHORT uShort = new USHORT(subBuffer);
				if (uShort.intValue() == 0L) {
					noteIDs = new USHORT[i];
					assignBuffer(buffer);
					break;
				}
			}
		}
	}

	@Override
	protected void assignBuffer(Buffer aBuffer) {
		super.assignBuffer(aBuffer);
		for (int i = 0; i < noteIDs.length; i++) {
			noteIDs[i] = new USHORT(getBuffer().subBuffer(i * USHORT.SIZE, USHORT.SIZE));
		}
	}

	@Override
	public int getSize() {
		return noteIDs.length == 0 ? 0 : USHORT.SIZE + (noteIDs.length * USHORT.SIZE);
	}

	@Override
	public void set(int[] value) {
		for (int i = 0; i < noteIDs.length; i++) {
			noteIDs[i].set(value[i]);
		}
	}

	@Override
	public int[] get() {
		int result[] = new int[noteIDs.length];
		for (int i = 0; i < noteIDs.length; i++) {
			result[i] = noteIDs[i].intValue();
		}
		return result;
	}
}
