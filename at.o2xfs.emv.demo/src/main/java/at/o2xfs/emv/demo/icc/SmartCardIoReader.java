/*
 * Copyright (c) 2014, Andreas Fagschlunger. All rights reserved.
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

package at.o2xfs.emv.demo.icc;

import at.o2xfs.emv.demo.DemoException;
import at.o2xfs.emv.icc.CAPDU;
import at.o2xfs.emv.icc.ICReader;
import at.o2xfs.emv.icc.RAPDU;
import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;

import java.io.IOException;
import java.util.List;

import javax.smartcardio.Card;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.TerminalFactory;

public class SmartCardIoReader implements ICReader {

	private static final Logger LOG = LoggerFactory.getLogger(SmartCardIoReader.class);

	private final Card card;

	public SmartCardIoReader() {
		card = init();
	}

	private Card init() {
		final String method = "init()";
		try {
			List<CardTerminal> terminals = TerminalFactory.getDefault().terminals().list();
			for (CardTerminal terminal : terminals) {
				if (!terminal.isCardPresent()) {
					if (LOG.isInfoEnabled()) {
						LOG.info(method, "Terminal (" + terminal.getName() + ") without card.");
					}
					continue;
				}
				return terminal.connect("*");
			}
		} catch (CardException e) {
			throw new DemoException(e);
		}
		throw new DemoException("No card found.");
	}

	@Override
	public byte[] reset() throws IOException {
		return card.getATR().getBytes();
	}

	@Override
	public RAPDU transmit(CAPDU command) throws IOException {
		try {
			byte[] response = card.getBasicChannel().transmit(new CommandAPDU(command.getBytes())).getBytes();
			return new RAPDU(response);
		} catch (CardException e) {
			throw new IOException(e);
		}
	}
}