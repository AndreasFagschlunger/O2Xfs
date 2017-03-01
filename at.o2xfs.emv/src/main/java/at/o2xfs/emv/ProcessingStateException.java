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

import at.o2xfs.common.Hex;

public class ProcessingStateException extends Exception {

	private final int sw;

	protected ProcessingStateException(int sw) {
		super(Hex.encode(sw));
		this.sw = sw;
	}

	protected ProcessingStateException(int sw, String message) {
		super(Hex.encode(sw) + ": " + message);
		this.sw = sw;
	}

	public int getSW() {
		return sw;
	}

	@Deprecated
	public static final void throwNew(final int sw)
			throws ProcessingStateException {
		switch (sw) {
			case 0x6283:
				throw new SelectedFileInvalidatedException();
			case 0x6A81:
				throw new FunctionNotSupportedException();
			case 0x6A82:
				throw new FileNotFoundException();
			case 0x6A83:
				throw new RecordNotFoundException();
			case 0x6A88:
				throw new ReferencedDataNotFoundException();
			case 0x6983:
				throw new AuthenticationMethodBlockedException();
			case 0x6984:
				throw new ReferencedDataInvalidatedException();
			case 0x6985:
				throw new ConditionsOfUseNotSatisfiedException();
			case 0x9000:
				break;
			default:
				throw new ProcessingStateException(sw);
		}
	}
}