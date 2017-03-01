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

package at.o2xfs.emv;

import at.o2xfs.common.Bytes;
import at.o2xfs.common.Hex;

public class TerminalCapabilities {

	private final byte[] bytes;

	private TerminalCapabilities(byte[] bytes) {
		this.bytes = bytes;
	}

	public byte[] getBytes() {
		return Bytes.copy(bytes);
	}

	public TerminalCardDataInputCapabilities getCardDataInputCapabilities() {
		return new TerminalCardDataInputCapabilities(bytes[0]);
	}

	public TerminalCVMCapabilities getCVMCapabilities() {
		return new TerminalCVMCapabilities(bytes[1]);
	}

	public TerminalSecurityCapabilities getSecurityCapabilities() {
		return new TerminalSecurityCapabilities(bytes[2]);
	}

	public static final TerminalCapabilities create(byte[] bytes)
			throws TerminalException {
		if (bytes == null) {
			throw new NullPointerException("bytes must not be null");
		} else if (bytes.length != 3) {
			throw new TerminalException("Illegal TerminalCapabilities: "
					+ Hex.encode(bytes));
		}
		return new TerminalCapabilities(Bytes.copy(bytes));
	}
}