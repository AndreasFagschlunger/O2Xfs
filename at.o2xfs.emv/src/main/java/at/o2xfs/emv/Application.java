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

import java.util.Arrays;

import at.o2xfs.common.Bytes;

public final class Application {

	private final byte[] aid;

	private final byte[] floorLimit;

	private final byte[] defaultTDOL;

	private final TerminalActionCodes terminalActionCodes;

	private final RandomTransactionSelectionData randomTransactionSelectionData;

	public Application(byte[] aid, byte[] floorLimit, byte[] defaultTDOL,
			TerminalActionCodes terminalActionCodes,
			RandomTransactionSelectionData randomTransactionSelectionData) {
		this.aid = Bytes.copy(aid);
		this.floorLimit = Bytes.copy(floorLimit);
		this.defaultTDOL = Bytes.copy(defaultTDOL);
		if (terminalActionCodes == null) {
			throw new NullPointerException(
					"terminalActionCodes must not be null");
		}
		this.terminalActionCodes = terminalActionCodes;
		this.randomTransactionSelectionData = randomTransactionSelectionData;
	}

	public byte[] getAID() {
		return Bytes.copy(aid);
	}

	public byte[] getFloorLimit() {
		return Bytes.copy(floorLimit);
	}

	public byte[] getDefaultTDOL() {
		return Bytes.copy(defaultTDOL);
	}

	public TerminalActionCodes getTerminalActionCodes() {
		return terminalActionCodes;
	}

	public RandomTransactionSelectionData getRandomTransactionSelectionData() {
		return randomTransactionSelectionData;
	}

	public boolean matches(byte[] aid) {
		return Arrays.equals(this.aid, aid) || partialMatch(aid);
	}

	private boolean partialMatch(byte[] aid) {
		return Arrays.equals(this.aid, Bytes.left(aid, this.aid.length));
	}
}