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

package at.o2xfs.emv;

import at.o2xfs.common.ByteArrayBuilder;
import at.o2xfs.common.Hex;
import at.o2xfs.emv.icc.CAPDU;
import at.o2xfs.emv.icc.CAPDUParser;
import at.o2xfs.emv.icc.CAPDUParserException;
import at.o2xfs.emv.icc.ICReader;
import at.o2xfs.emv.icc.RAPDU;
import at.o2xfs.emv.tlv.TLV;
import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;

import java.io.IOException;
import java.util.List;

class IssuerToCardScriptProcessing {

	private static final Logger LOG = LoggerFactory.getLogger(IssuerToCardScriptProcessing.class);

	private static final int SCRIPT_ID_LENGTH = 4;

	private final ICReader icReader;

	private final TVR tvr;

	private final TSI tsi;

	private byte[] scriptID = null;

	private boolean beforeFinalGenerateAC = false;

	public IssuerToCardScriptProcessing(ICReader icReader, TVR tvr, TSI tsi) {
		this.icReader = icReader;
		this.tvr = tvr;
		this.tsi = tsi;
	}

	public byte[] process(TLV issuerScript) throws IOException {
		final String method = "processScript(TLV)";
		beforeFinalGenerateAC = EMVTag.ISSUER_SCRIPT_TEMPLATE_1.getTag().equals(issuerScript.getTag());
		List<TLV> commands = issuerScript.getChildren();
		scriptID = extractScriptID(issuerScript.getChildren());
		if (LOG.isInfoEnabled()) {
			LOG.info(method, "Script ID: " + Hex.encode(scriptID));
		}
		return processIssuerScripts(commands);
	}

	private byte[] extractScriptID(List<TLV> commands) {
		final String method = "extractScriptID(List<TLV>)";
		for (int i = 0; i < commands.size(); i++) {
			if (EMVTag.ISSUER_SCRIPT_IDENTIFIER.getTag().equals(commands.get(i).getTag())) {
				return commands.remove(i).getValue();
			}
		}
		if (LOG.isWarnEnabled()) {
			LOG.warn(method, "No Script ID present");
		}
		return new byte[SCRIPT_ID_LENGTH];
	}

	private byte[] processIssuerScripts(List<TLV> commands) throws IOException {
		final String method = "processIssuerScripts(List<TLV>)";
		ByteArrayBuilder issuerScriptResults = new ByteArrayBuilder();
		int sequenceNumber = 0;
		for (TLV command : commands) {
			if (!EMVTag.ISSUER_SCRIPT_COMMAND.getTag().equals(command.getTag())) {
				if (LOG.isErrorEnabled()) {
					LOG.error(method, "Illegal Template: " + Hex.encode(command.getBytes()));
				}
				continue;
			}
			sequenceNumber++;
			ProcessingState result = null;
			try {
				result = processScript(CAPDUParser.parse(command.getValue()));
			} catch (CAPDUParserException e) {
				if (LOG.isErrorEnabled()) {
					LOG.error(method, "Error parsing CAPDU", e);
				}
			}
			if (sequenceNumber == 1) {
				tsi.getByte1().scriptProcessingWasPerformed();
			}
			if (result == null || result.isErrorCondition()) {
				issuerScriptResults.append(IssuerScriptResult.processingFailed(sequenceNumber, scriptID));
				if (beforeFinalGenerateAC) {
					tvr.getByte5().scriptProcessingFailedBeforeFinalGenerateAC();
				} else {
					tvr.getByte5().scriptProcessingFailedAfterFinalGenerateAC();
				}
				break;
			}
			issuerScriptResults.append(IssuerScriptResult.processingSuccessful(sequenceNumber, scriptID));

		}
		return issuerScriptResults.build();
	}

	private ProcessingState processScript(CAPDU command) throws IOException {
		RAPDU response = icReader.transmit(command);
		return new ProcessingState(response.getSW());
	}
}