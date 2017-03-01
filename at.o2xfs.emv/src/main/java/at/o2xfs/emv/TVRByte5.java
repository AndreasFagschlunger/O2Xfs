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

import at.o2xfs.common.Bit;

public class TVRByte5 extends TVRByte {

	TVRByte5(TVR tvr) {
		super(tvr, 4);
	}

	/**
	 * Default TDOL used
	 */
	public void defaultTDOLUsed() {
		setBit(Bit.B8, "Default TDOL used");
	}

	/**
	 * Issuer authentication failed
	 */
	public void issuerAuthenticationFailed() {
		setBit(Bit.B7, "Issuer authentication failed");
	}

	/**
	 * Script processing failed before final GENERATE AC
	 */
	public void scriptProcessingFailedBeforeFinalGenerateAC() {
		setBit(Bit.B6, "Script processing failed before final GENERATE AC");
	}

	/**
	 * Script processing failed after final GENERATE AC
	 */
	public void scriptProcessingFailedAfterFinalGenerateAC() {
		setBit(Bit.B5, "Script processing failed after final GENERATE AC");
	}
}