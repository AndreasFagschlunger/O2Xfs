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

package at.o2xfs.emv.demo.icc;

import at.o2xfs.ctapi.CTAPI;
import at.o2xfs.ctapi.CTException;
import at.o2xfs.emv.icc.AbstractICReader;
import at.o2xfs.emv.icc.CAPDU;
import at.o2xfs.emv.icc.RAPDU;

import java.io.IOException;

public final class CTAPI_ICReader extends AbstractICReader {

	private final CTAPI ctAPI;

	private boolean open = false;

	public CTAPI_ICReader() throws CTException {
		ctAPI = new CTAPI();
		init();
	}

	private void init() throws CTException {
		ctAPI.init(1, 1);
		open = true;
	}

	@Override
	protected byte[] internalReset() throws IOException {
		CAPDU capdu = new CAPDU(0x20, 0x11, 0x01, 0x01, 0x00);
		try {
			RAPDU response = new RAPDU(ctAPI.data(1, 1, 2, capdu.getBytes()));
			return response.getData();
		} catch (CTException e) {
			throw new IOException(e);
		}
	}

	@Override
	protected RAPDU internalTransmit(byte[] ctpdu) throws IOException {
		try {
			return new RAPDU(ctAPI.data(1, 0, 2, ctpdu));
		} catch (CTException e) {
			throw new IOException(e);
		}
	}

	public void close() {
		if (!open) {
			return;
		}
		try {
			ctAPI.close(1);
			open = false;
		} catch (CTException e) {
			e.printStackTrace();
		}
	}
}