/*
 * Copyright (c) 2014, Andreas Fagschlunger. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 * - Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 
 * - Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package at.o2xfs.xfs.siu;

import at.o2xfs.win32.WORD;
import at.o2xfs.win32.WORDArray;
import at.o2xfs.xfs.XfsConstant;

import java.util.Map;

class AbstractPortsBuilder {

	private final WORDArray ports;

	protected AbstractPortsBuilder(final WORDArray ports) {
		this.ports = ports;
	}

	protected <E extends Enum<E> & XfsConstant> void setPort(final SIUPortIndex index, final E state) {
		final WORD port = ports.get(index.intValue());
		port.set((int) state.getValue());
	}

	protected <E extends Enum<E> & XfsConstant> void setPorts(final SIUPortIndex index, final Map<E, Boolean> bitmap) {
		int value = 0;
		for (final Map.Entry<E, Boolean> entry : bitmap.entrySet()) {
			final boolean on = entry.getValue().booleanValue();
			if (on) {
				value |= entry.getKey().getValue();
			}
		}
		final WORD port = ports.get(index.intValue());
		port.set(value);
	}

	protected WORD getPort(final SIUPortIndex index) {
		return ports.get(index.intValue());
	}
}