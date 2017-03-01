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

public final class IssuerScriptResult {

	private static final int SCRIPT_NOT_PERFORMED = 0;

	private static final int SCRIPT_PROCESSING_FAILED = 1;

	private static final int SCRIPT_PROCESSING_SUCCESSFUL = 2;

	private static final int LENGTH = 5;

	private final int result;

	private final int sequenceNumber;

	private final byte[] scriptID;

	private IssuerScriptResult(int result, int sequenceNumber, byte[] scriptID) {
		this.result = result;
		this.sequenceNumber = sequenceNumber;
		this.scriptID = Bytes.copy(scriptID);
	}

	private byte[] encode() {
		byte[] encoded = new byte[LENGTH];
		encoded[0] = (byte) (result << 4);
		encoded[0] |= encodeSequenceNumber() & 0xF;
		System.arraycopy(scriptID, 0, encoded, 1, encoded.length - 1);
		return encoded;
	}

	private int encodeSequenceNumber() {
		if (sequenceNumber > 0xF) {
			return 0xF;
		}
		return sequenceNumber;
	}

	public static final byte[] notPerformed(int sequenceNumber, byte[] scriptID) {
		return new IssuerScriptResult(SCRIPT_NOT_PERFORMED, sequenceNumber,
				scriptID).encode();
	}

	public static final byte[] processingFailed(int sequenceNumber,
			byte[] scriptID) {
		return new IssuerScriptResult(SCRIPT_PROCESSING_FAILED, sequenceNumber,
				scriptID).encode();
	}

	public static final byte[] processingSuccessful(int sequenceNumber,
			byte[] scriptID) {
		return new IssuerScriptResult(SCRIPT_PROCESSING_SUCCESSFUL,
				sequenceNumber, scriptID).encode();
	}
}