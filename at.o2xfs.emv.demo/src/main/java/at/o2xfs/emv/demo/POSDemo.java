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

package at.o2xfs.emv.demo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;

import at.o2xfs.common.Hex;
import at.o2xfs.emv.Amount;
import at.o2xfs.emv.AuthorisationResponse;
import at.o2xfs.emv.Candidate;
import at.o2xfs.emv.CryptogramInformationData;
import at.o2xfs.emv.Currency;
import at.o2xfs.emv.EMVTransaction;
import at.o2xfs.emv.EMVTransactionCallback;
import at.o2xfs.emv.SelectResult;
import at.o2xfs.emv.Terminal;
import at.o2xfs.emv.TerminateSessionException;
import at.o2xfs.emv.TransactionData;
import at.o2xfs.emv.TransactionType;
import at.o2xfs.emv.UnableToGoOnlineException;
import at.o2xfs.emv.demo.pinpad.SoftwarePINPad;
import at.o2xfs.emv.demo.ui.TerminalGUI;
import at.o2xfs.emv.icc.ICReader;
import at.o2xfs.emv.impl.PropertiesTerminalFactory;
import at.o2xfs.emv.util.StandardMessage;
import at.o2xfs.emv.util.StandardMessages;
import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;

public class POSDemo implements EMVTransactionCallback {

	private static final Logger LOG = LoggerFactory.getLogger(POSDemo.class);

	private final SoftwarePINPad pinPad;

	private final TerminalGUI terminalUI;

	private Terminal terminal;

	public POSDemo() {
		loadTerminalConfiguration();
		terminalUI = new TerminalGUI();
		pinPad = new SoftwarePINPad(terminalUI);
	}

	private void doDemo() {
		ICReader icReader = createIcReader();
		final String method = "demo()";
		loadTerminalConfiguration();
		terminalUI.show();
		EMVTransaction transaction = new EMVTransaction(terminal, this, icReader, pinPad);
		TransactionData transactionData = new TransactionData(new TransactionType(Hex.decode("00")), new Currency(Hex.decode("0978"), Hex.decode("02")),
				new Amount(new BigInteger("10")), null);
		try {
			List<Candidate> candidates = transaction.buildCandidateList();
			do {
				Candidate candidate = terminalUI.selectCandidate(candidates);
				if (candidate == null) {
					break;
				}
				if (SelectResult.NOK == transaction.processTransaction(candidate, transactionData)) {
					candidates.remove(candidate);
				}
				break;
			} while (!candidates.isEmpty());
		} catch (TerminateSessionException e) {
			e.printStackTrace();
		} catch (IOException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "I/O exception", e);
			}
			terminalUI.print(StandardMessages.PROCESSING_ERROR);
		}
	}

	private void loadTerminalConfiguration() {
		final String method = "loadTerminalConfiguration()";
		InputStream inStream = null;
		try {
			inStream = new FileInputStream("pos.properties");
			terminal = new PropertiesTerminalFactory(inStream).newInstance();
		} catch (IOException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Error reading file", e);
			}
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					if (LOG.isErrorEnabled()) {
						LOG.error(method, "Error closing stream", e);
					}
				}
			}
		}
	}

	private ICReader createIcReader() {
		try {
			Class<ICReader> icReaderClass = (Class<ICReader>) Class.forName(System.getProperty("ICReader.Class", "at.o2xfs.emv.demo.icc.SmartCardIoReader"));
			return icReaderClass.newInstance();
		} catch (Exception e) {
			throw new DemoException(e);
		}
	}

	@Override
	public AuthorisationResponse doSendAuthorisationRequest() throws UnableToGoOnlineException {
		throw new UnableToGoOnlineException("Not supported");
	}

	@Override
	public void onTransactionDeclined(CryptogramInformationData cid) {
		StandardMessage message = StandardMessages.DECLINED;
		if (CryptogramInformationData.SERVICE_NOT_ALLOWED == cid.getAdviceCode()) {
			message = StandardMessages.NOT_ACCEPTED;
		}
		terminalUI.terminate(message);
	}

	@Override
	public void onTransactionApproved() {
		terminalUI.terminate(StandardMessages.APPROVED);
	}

	public static void main(String[] args) {
		new POSDemo().doDemo();
	}
}