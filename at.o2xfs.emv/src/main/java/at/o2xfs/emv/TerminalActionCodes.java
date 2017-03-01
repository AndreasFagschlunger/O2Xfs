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

public final class TerminalActionCodes {

	public static class TerminalActionCodesBuilder {

		private byte[] tacDenial = new byte[TVR.LENGTH];

		private byte[] tacOnline = new byte[TVR.LENGTH];

		private byte[] tacDefault = new byte[TVR.LENGTH];

		public TerminalActionCodesBuilder setTACDenial(byte[] tacDenial) {
			verifyTAC(tacDenial);
			this.tacDenial = Bytes.copy(tacDenial);
			return this;
		}

		public TerminalActionCodesBuilder setTACOnline(byte[] tacOnline) {
			verifyTAC(tacOnline);
			this.tacOnline = Bytes.copy(tacOnline);
			return this;
		}

		public TerminalActionCodesBuilder setTACDefault(byte[] tacDefault) {
			verifyTAC(tacDefault);
			this.tacDefault = Bytes.copy(tacDefault);
			return this;
		}

		private void verifyTAC(byte[] tac) {
			if (tac.length != TVR.LENGTH) {
				throw new IllegalArgumentException("Illegal TAC: " + tac);
			}
		}

		public TerminalActionCodes build() {
			return new TerminalActionCodes(this);
		}

	}

	private final byte[] tacDenial;

	private final byte[] tacOnline;

	private final byte[] tacDefault;

	private TerminalActionCodes(TerminalActionCodesBuilder builder) {
		tacDenial = builder.tacDenial;
		tacOnline = builder.tacOnline;
		tacDefault = builder.tacDefault;
	}

	public byte[] getDenial() {
		return Bytes.copy(tacDenial);
	}

	public byte[] getOnline() {
		return Bytes.copy(tacOnline);
	}

	public byte[] getDefault() {
		return Bytes.copy(tacDefault);
	}
}