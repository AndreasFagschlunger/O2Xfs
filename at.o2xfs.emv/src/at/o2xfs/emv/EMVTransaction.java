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
import at.o2xfs.common.Bytes;
import at.o2xfs.common.Hex;
import at.o2xfs.emv.cvm.CardholderVerification;
import at.o2xfs.emv.icc.ICReader;
import at.o2xfs.emv.pinpad.PINPad;
import at.o2xfs.emv.tlv.TLV;
import at.o2xfs.emv.tlv.Tag;
import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;

import java.io.IOException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class EMVTransaction {

	private static final Logger LOG = LoggerFactory.getLogger(EMVTransaction.class);

	private static final String DATE_PATTERN = "yyMMdd";

	private static final String TIME_PATTERN = "HHmmss";

	private final Terminal terminal;

	private final EMVTransactionCallback transactionCallback;

	private final ICReader icReader;

	private final PINPad pinPad;

	private final Map<Tag, byte[]> dataObjects;

	private final List<DataAuthenticationRecord> dataAuthenticationRecords;

	private byte[] issuerScriptResults = null;

	private Candidate candidate = null;

	public EMVTransaction(	Terminal terminal,
							EMVTransactionCallback transactionCallback,
							ICReader icReader,
							PINPad pinPad) {
		this.terminal = terminal;
		this.transactionCallback = transactionCallback;
		this.icReader = icReader;
		this.pinPad = pinPad;
		dataObjects = new HashMap<Tag, byte[]>();
		dataAuthenticationRecords = new ArrayList<DataAuthenticationRecord>();
	}

	public List<Candidate> buildCandidateList() throws TerminateSessionException, IOException {
		return new CandidateList(terminal, icReader).build();
	}

	public boolean processTransaction(Candidate aCandidate, TransactionData transactionData) throws TerminateSessionException,
																							IOException {
		this.candidate = aCandidate;
		resetTransaction(transactionData);
		InitiateApplicationProcessing initiateApplicationProcessing = new InitiateApplicationProcessing(this, candidate);
		if (!initiateApplicationProcessing.perform()) {
			return false;
		}
		readApplicationData();
		checkMandatoryDataObjects();
		new OfflineDataAuthentication(this).perform();
		new ProcessingRestrictions(this).perform();
		new CardholderVerification(this).perform();
		new TerminalRiskManagement(this).perform();
		CryptogramType cryptogramType = new TerminalActionAnalysis(this).perform(false);
		CryptogramInformationData cid = new CardActionAnalysis(this).firstGenerateAC(cryptogramType);
		switch (cid.getCryptogramType()) {
			case AAC:
				transactionCallback.onTransactionDeclined(cid);
				break;
			case ARQC:
				performOnlineProcessing();
				break;
			case TC:
				transactionCallback.onTransactionApproved();
				break;
		}
		return true;
	}

	private void performOnlineProcessing() throws TerminateSessionException, IOException {
		final String method = "performOnlineProcessing()";
		CryptogramType cryptogramType = null;
		TLV issuerScriptTemplate = null;
		ByteArrayBuilder issuerScriptResultBuilder = new ByteArrayBuilder();
		try {
			resetAuthorisationResponseCode();
			AuthorisationResponse authorisationResponse = transactionCallback.doSendAuthorisationRequest();
			putData(EMVTag.AUTHORISATION_RESPONSE_CODE, authorisationResponse.getAuthorisationResponseCode());
			if (authorisationResponse.getICCSystemRelatedData().length != 0) {
				Template template = new Template(TLV.parse(authorisationResponse.getICCSystemRelatedData()));
				if (template.containsTag(EMVTag.ISSUER_AUTHENTICATION_DATA)) {
					putData(EMVTag.ISSUER_AUTHENTICATION_DATA, template.getValue(EMVTag.ISSUER_AUTHENTICATION_DATA));
					new IssuerAuthentication(this).perform(template.getValue(EMVTag.ISSUER_AUTHENTICATION_DATA));
				}
				if (template.containsTag(EMVTag.ISSUER_SCRIPT_TEMPLATE_1)) {
					issuerScriptResultBuilder.append((new IssuerToCardScriptProcessing(icReader, getTVR(), getTSI()).process(template.findTag(EMVTag.ISSUER_SCRIPT_TEMPLATE_1))));
				}
				issuerScriptTemplate = template.findTag(EMVTag.ISSUER_SCRIPT_TEMPLATE_2);
			}
			cryptogramType = authorisationResponse.isOnlineApproved() ? CryptogramType.TC : CryptogramType.AAC;
		} catch (UnableToGoOnlineException e) {
			if (LOG.isInfoEnabled()) {
				LOG.info(method, "Unable to Go Online", e);
			}
			cryptogramType = new TerminalActionAnalysis(this).perform(true);
		}
		CryptogramInformationData cid = new CardActionAnalysis(this).secondGenerateAC(cryptogramType);
		if (issuerScriptTemplate != null) {
			issuerScriptResultBuilder.append((new IssuerToCardScriptProcessing(icReader, getTVR(), getTSI()).process(issuerScriptTemplate)));
		}
		issuerScriptResults = issuerScriptResultBuilder.build();
		switch (cid.getCryptogramType()) {
			case AAC:
				transactionCallback.onTransactionDeclined(cid);
				break;
			case TC:
				transactionCallback.onTransactionApproved();
				break;
		}
	}

	private void resetAuthorisationResponseCode() {
		final String method = "resetAuthorisationResponseCode()";
		if (!dataObjects.containsKey(EMVTag.AUTHORISATION_RESPONSE_CODE)) {
			if (LOG.isDebugEnabled()) {
				LOG.debug(method, "No ARPC set.");
			}
			return;
		}
		byte[] arpc = dataObjects.remove(EMVTag.AUTHORISATION_RESPONSE_CODE.getTag());
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "Removed ARPC: " + new String(arpc));
		}
	}

	private void resetTransaction(TransactionData transactionData) {
		dataObjects.clear();
		dataAuthenticationRecords.clear();
		issuerScriptResults = null;
		putData(EMVTag.TERMINAL_TYPE, terminal.getTerminalType().getBytes());
		putData(EMVTag.TERMINAL_CAPABILITIES, terminal.getTerminalCapabilities().getBytes());
		putData(EMVTag.ADDITIONAL_TERMINAL_CAPABILITIES, terminal.getAdditionalCapabilities().getBytes());
		putData(EMVTag.TERMINAL_COUNTRY_CODE, terminal.getCountryCode().getCode());
		putData(EMVTag.MERCHANT_IDENTIFIER, terminal.getMerchantIdentifier().getBytes());
		putData(EMVTag.TERMINAL_VERIFICATION_RESULTS, new byte[TVR.LENGTH]);
		putData(EMVTag.APPLICATION_IDENTIFIER_TERMINAL, candidate.getDFName());
		putData(EMVTag.TERMINAL_FLOOR_LIMIT, getApplication().getFloorLimit());
		putData(EMVTag.UNPREDICTABLE_NUMBER, unpredictableNumber());

		putData(EMVTag.TRANSACTION_DATE, newTransactionDate());
		putData(EMVTag.TRANSACTION_STATUS_INFORMATION, new byte[TSI.TSI_LENGTH]);
		putData(EMVTag.TRANSACTION_TIME, newTransactionTime());
		putData(EMVTag.TERMINAL_IDENTIFICATION, terminal.getTerminalIdentification().getTerminalIdentification());
		putData(EMVTag.TRANSACTION_TYPE, transactionData.getTransactionType().getTransactionType());
		putData(EMVTag.TRANSACTION_CURRENCY_CODE, transactionData.getCurrency().getCode());
		putData(EMVTag.TRANSACTION_CURRENCY_EXPONENT, transactionData.getCurrency().getExponent());
		putData(EMVTag.AMOUNT_AUTHORISED_BINARY, transactionData.getAmountAuthorised().toBinary());
		putData(EMVTag.AMOUNT_AUTHORISED_NUMERIC, transactionData.getAmountAuthorised().toNumeric());
		if (transactionData.getAmountOther() != null) {
			putData(EMVTag.AMOUNT_OTHER_BINARY, transactionData.getAmountOther().toBinary());
			putData(EMVTag.AMOUNT_OTHER_NUMERIC, transactionData.getAmountOther().toNumeric());
		}
	}

	private byte[] newTransactionDate() {
		String hex = new SimpleDateFormat(DATE_PATTERN).format(new Date());
		return Hex.decode(hex);
	}

	private byte[] newTransactionTime() {
		String hex = new SimpleDateFormat(TIME_PATTERN).format(new Date());
		return Hex.decode(hex);
	}

	private byte[] unpredictableNumber() {
		byte[] unpredictableNumber = new byte[4];
		new SecureRandom().nextBytes(unpredictableNumber);
		return unpredictableNumber;
	}

	Candidate getCandidate() {
		return candidate;
	}

	Application getApplication() {
		byte[] aid = getMandatoryData(EMVTag.APPLICATION_IDENTIFIER_TERMINAL);
		for (Application application : terminal.getApplications()) {
			if (application.matches(aid)) {
				return application;
			}
		}
		throw new RuntimeException("Unsupported Application: " + Hex.encode(aid));
	}

	private void readApplicationData() throws TerminateSessionException, IOException {
		ApplicationData applicationData = new ReadApplicationData(this).perform();
		for (Map.Entry<Tag, byte[]> data : applicationData.getDataObjects().entrySet()) {
			dataObjects.put(data.getKey(), data.getValue());
		}
		dataAuthenticationRecords.addAll(applicationData.getDataAuthenticationRecords());
	}

	private void checkMandatoryDataObjects() throws TerminateSessionException {
		try {
			MandatoryData mandatoryData = new MandatoryData(this);
			mandatoryData.assertPresent(EMVTag.APPLICATION_EXPIRATION_DATE);
			mandatoryData.assertPresent(EMVTag.APPLICATION_PRIMARY_ACCOUNT_NUMBER);
			mandatoryData.assertPresent(EMVTag.CARD_RISK_MANAGEMENT_DATA_OBJECT_LIST_1);
			mandatoryData.assertPresent(EMVTag.CARD_RISK_MANAGEMENT_DATA_OBJECT_LIST_2);
		} catch (MissingMandatoryDataException e) {
			throw new TerminateSessionException(e);
		}
	}

	public EMVTransactionCallback getTransactionCallback() {
		return transactionCallback;
	}

	public TVR getTVR() {
		return new TVR(dataObjects.get(EMVTag.TERMINAL_VERIFICATION_RESULTS.getTag()));
	}

	/**
	 * Transaction Status Information
	 *
	 * @return the Transaction Status Information
	 */
	public TSI getTSI() {
		return new TSI(dataObjects.get(EMVTag.TRANSACTION_STATUS_INFORMATION.getTag()));
	}

	public ICReader getICReader() {
		return icReader;
	}

	public PINPad getPINPad() {
		return pinPad;
	}

	public List<DataAuthenticationRecord> getDataAuthenticationRecords() {
		return new ArrayList<DataAuthenticationRecord>(dataAuthenticationRecords);
	}

	public Terminal getTerminal() {
		return terminal;
	}

	public AIP getAIP() {
		return new AIP(getMandatoryData(EMVTag.APPLICATION_INTERCHANGE_PROFILE));
	}

	public byte[] getIssuerScriptResults() {
		if (issuerScriptResults != null) {
			return Bytes.copy(issuerScriptResults);
		}
		return null;
	}

	public void putData(EMVTag emvTag, byte[] value) {
		if (dataObjects.containsKey(emvTag.getTag())) {
			throw new IllegalStateException(emvTag + " already assigned");
		} else if (value == null) {
			throw new NullPointerException("value must not be null");
		} else if (value.length == 0) {
			throw new IllegalArgumentException("value must not be empty");
		}
		dataObjects.put(emvTag.getTag(), Bytes.copy(value));
	}

	public boolean containsData(EMVTag emvTag) {
		return dataObjects.containsKey(emvTag.getTag());
	}

	public byte[] getMandatoryData(EMVTag emvTag) {
		try {
			return getData(emvTag);
		} catch (DataNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public byte[] getData(EMVTag emvTag) throws DataNotFoundException {
		byte[] data = dataObjects.get(emvTag.getTag());
		if (data == null) {
			throw new DataNotFoundException(emvTag);
		}
		return Bytes.copy(data);
	}

}