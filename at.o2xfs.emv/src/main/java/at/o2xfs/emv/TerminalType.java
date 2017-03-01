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

public class TerminalType {

	private final int terminalType;

	public TerminalType(int terminalType) {
		this.terminalType = terminalType;
	}

	protected byte[] getBytes() {
		return Hex.decode(Integer.toString(terminalType));
	}

	public boolean isAttended() {
		switch (terminalType % 10) {
			case 1:
			case 2:
			case 3:
				return true;
		}
		return false;
	}

	public boolean isUnattended() {
		switch (terminalType % 10) {
			case 4:
			case 5:
			case 6:
				return true;
		}
		return false;
	}

	public boolean isOnlineOnly() {
		switch (terminalType % 10) {
			case 1:
			case 4:
				return true;
		}
		return false;
	}

	public boolean isOfflineWithOnlineCapability() {
		switch (terminalType % 10) {
			case 2:
			case 5:
				return true;
		}
		return false;
	}

	public boolean isOfflineOnly() {
		switch (terminalType % 10) {
			case 3:
			case 6:
				return true;
		}
		return false;
	}

	public boolean isFinancialInstitution() {
		return terminalType >= 11 && terminalType <= 16;
	}

	public boolean isMerchant() {
		return terminalType >= 21 && terminalType <= 26;
	}

	public boolean isCardholder() {
		return terminalType >= 34 && terminalType <= 36;
	}
}