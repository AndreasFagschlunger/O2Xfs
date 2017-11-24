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

package at.o2xfs.xfs.v3_30.idc;

import at.o2xfs.win32.Buffer;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Type;
import at.o2xfs.win32.UINT;
import at.o2xfs.win32.ValueType;
import at.o2xfs.xfs.idc.IdcExecuteCommand;
import at.o2xfs.xfs.util.XfsConstants;

public class SynchronizableCommands extends Type implements ValueType<IdcExecuteCommand[]> {

	private final UINT synchronizableCommands[];

	public SynchronizableCommands(IdcExecuteCommand[] commands) {
		synchronizableCommands = new UINT[commands.length];
		allocate();
		set(commands);
	}

	public SynchronizableCommands(Pointer aPointer) {
		if (Pointer.NULL.equals(aPointer)) {
			synchronizableCommands = new UINT[0];
		} else {
			Buffer buffer = null;
			for (int i = 0;; i++) {
				buffer = aPointer.buffer((i * UINT.SIZE) + UINT.SIZE);
				Buffer subBuffer = buffer.subBuffer(i * UINT.SIZE, UINT.SIZE);
				UINT uInt = new UINT(subBuffer);
				if (uInt.longValue() == 0L) {
					synchronizableCommands = new UINT[i];
					assignBuffer(buffer);
					break;
				}
			}
		}
	}

	@Override
	public int getSize() {
		return synchronizableCommands.length == 0 ? 0 : UINT.SIZE + (synchronizableCommands.length * UINT.SIZE);
	}

	@Override
	protected void assignBuffer(Buffer aBuffer) {
		super.assignBuffer(aBuffer);
		for (int i = 0; i < synchronizableCommands.length; i++) {
			synchronizableCommands[i] = new UINT(getBuffer().subBuffer(i * UINT.SIZE, UINT.SIZE));
		}
	}

	@Override
	public void set(IdcExecuteCommand[] value) {
		for (int i = 0; i < synchronizableCommands.length; i++) {
			synchronizableCommands[i].set(value[i].getValue());
		}
	}

	@Override
	public IdcExecuteCommand[] get() {
		IdcExecuteCommand result[] = new IdcExecuteCommand[synchronizableCommands.length];
		for (int i = 0; i < synchronizableCommands.length; i++) {
			result[i] = XfsConstants.valueOf(synchronizableCommands[i], IdcExecuteCommand.class);
		}
		return result;
	}
}
