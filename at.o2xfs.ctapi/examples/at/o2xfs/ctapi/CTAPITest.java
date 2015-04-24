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

package at.o2xfs.ctapi;

import at.o2xfs.common.Hex;

public class CTAPITest {

	private final CTAPI ctAPI;

	public CTAPITest() {
		ctAPI = new CTAPI();
	}

	public void test() {
		try {
			final int pn = 1;
			ctAPI.init(1, pn);
			transmit(1, 1, 2, new byte[] { 0x20, 0x11, 0x01, 0x01, 0x00 });
			ctAPI.close(1);
		} catch (final CTException e) {
			e.printStackTrace();
		}
	}

	private void transmit(int ctn, int dad, int sad, byte[] command) throws CTException {
		System.out.println(">> " + ctn + ", " + dad + "," + sad + " " + Hex.encode(command));
		System.out.println("<< " + Hex.encode(ctAPI.data(ctn, dad, sad, command)));
	}

	public static final void main(final String[] args) {
		new CTAPITest().test();
	}
}