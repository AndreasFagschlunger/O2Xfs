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

package at.o2xfs.xfs.v3_10.idc;

import at.o2xfs.win32.Buffer;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Type;
import at.o2xfs.win32.ValueType;
import at.o2xfs.win32.WORD;
import at.o2xfs.xfs.idc.MemoryChipProtocol;
import at.o2xfs.xfs.util.XfsConstants;

public class MemoryChipProtocols extends Type implements ValueType<MemoryChipProtocol[]> {

	private final WORD[] memoryChipProtocols;

	public MemoryChipProtocols(MemoryChipProtocol[] memoryChipProtocols) {
		this.memoryChipProtocols = new WORD[memoryChipProtocols.length];
		allocate();
		set(memoryChipProtocols);
	}

	public MemoryChipProtocols(Pointer aPointer) {
		Buffer buffer = null;
		for (int i = 0;; i++) {
			buffer = aPointer.buffer((i * WORD.SIZE) + WORD.SIZE);
			Buffer subBuffer = buffer.subBuffer(i * WORD.SIZE, WORD.SIZE);
			WORD word = new WORD(subBuffer);
			if (word.intValue() == 0L) {
				memoryChipProtocols = new WORD[i];
				assignBuffer(buffer);
				break;
			}
		}
	}

	@Override
	public int getSize() {
		return memoryChipProtocols.length == 0 ? 0 : WORD.SIZE + (memoryChipProtocols.length * WORD.SIZE);
	}

	@Override
	protected void assignBuffer(Buffer aBuffer) {
		super.assignBuffer(aBuffer);
		for (int i = 0; i < memoryChipProtocols.length; i++) {
			memoryChipProtocols[i] = new WORD(getBuffer().subBuffer(i * WORD.SIZE, WORD.SIZE));
		}
	}

	@Override
	public void set(MemoryChipProtocol[] value) {
		for (int i = 0; i < value.length; i++) {
			memoryChipProtocols[i].set((int) value[i].getValue());
		}
	}

	@Override
	public MemoryChipProtocol[] get() {
		MemoryChipProtocol[] result = new MemoryChipProtocol[memoryChipProtocols.length];
		for (int i = 0; i < memoryChipProtocols.length; i++) {
			result[i] = XfsConstants.valueOf(memoryChipProtocols[i].longValue(), MemoryChipProtocol.class);
		}
		return result;
	}
}
