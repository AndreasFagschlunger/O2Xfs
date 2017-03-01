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

package at.o2xfs.emv.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import at.o2xfs.common.Bytes;
import at.o2xfs.common.Hex;
import at.o2xfs.emv.AdditionalTerminalCapabilities;
import at.o2xfs.emv.Application;
import at.o2xfs.emv.CountryCode;
import at.o2xfs.emv.MerchantIdentifier;
import at.o2xfs.emv.RandomTransactionSelectionData;
import at.o2xfs.emv.Terminal;
import at.o2xfs.emv.TerminalActionCodes;
import at.o2xfs.emv.TerminalActionCodes.TerminalActionCodesBuilder;
import at.o2xfs.emv.TerminalCapabilities;
import at.o2xfs.emv.TerminalException;
import at.o2xfs.emv.TerminalFactory;
import at.o2xfs.emv.TerminalIdentification;
import at.o2xfs.emv.TerminalType;
import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;

public class PropertiesTerminalFactory implements TerminalFactory {

	private static final Logger LOG = LoggerFactory
			.getLogger(PropertiesTerminalFactory.class);

	private static class Key {

		private final String key;

		private Key(String key) {
			this.key = key;
		}

		public Key subKey(Key key) {
			return subKey(key.getKey());
		}

		public Key subKey(String key) {
			return new Key(this.key + "." + key);
		}

		private String getKey() {
			return key;
		}
	}

	private static final Key KEY_TERMINAL_IDENTIFICATION = new Key(
			"TerminalIdentification");

	private static final Key KEY_TERMINAL_TYPE = new Key("TerminalType");

	private static final Key KEY_TERMINAL_CAPABILITIES = new Key(
			"TerminalCapabilities");

	private static final Key KEY_ADDITIONAL_TERMINAL_CAPABILITIES = new Key(
			"AdditionalTerminalCapabilities");

	private static final Key KEY_TERMINAL_COUNTRY_CODE = new Key(
			"TerminalCountryCode");

	private static final Key KEY_MERCHANT_IDENTIFIER = new Key(
			"MerchantIdentifier");

	private static final Key KEY_APPLICATIONS = new Key("Applications");

	private static final Key KEY_TERMINAL_ACTION_CODE = new Key(
			"TerminalActionCode");

	private static final Key KEY_TERMINAL_FLOOR_LIMIT = new Key(
			"TerminalFloorLimit");

	private static final Key KEY_TDOL = new Key("TDOL");

	private final Properties properties;

	public PropertiesTerminalFactory(final InputStream inStream)
			throws IOException {
		this.properties = new Properties();
		loadProperties(inStream);
	}

	private void loadProperties(InputStream inStream) throws IOException {
		final String method = "loadProperties(InputStream)";
		try {
			properties.load(inStream);
		} catch (IOException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Error loading Properties", e);
			}
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					if (LOG.isErrorEnabled()) {
						LOG.error(method, "Error closing InputStream", e);
					}
				}
			}
		}
	}

	@Override
	public Terminal newInstance() {
		TerminalIdentification terminalIdentification = new TerminalIdentification(
				getString(KEY_TERMINAL_IDENTIFICATION));
		TerminalType terminalType = getTerminalType();
		TerminalCapabilities terminalCapabilities = getTerminalCapabilities();
		AdditionalTerminalCapabilities additionalCapabilities = getAdditionalTerminalCapabilities();
		CountryCode countryCode = getCountryCode();
		MerchantIdentifier merchantIdentifier = new MerchantIdentifier(
				getString(KEY_MERCHANT_IDENTIFIER));
		List<Application> applications = getApplications();
		return new Terminal(terminalIdentification, terminalType,
				terminalCapabilities, additionalCapabilities, countryCode,
				merchantIdentifier, applications);
	}

	private TerminalType getTerminalType() {
		String s = getString(KEY_TERMINAL_TYPE);
		try {
			return new TerminalType(Integer.parseInt(s));
		} catch (NumberFormatException e) {
			throw new TerminalException("Illegal TerminalType: " + s, e);
		}
	}

	private TerminalCapabilities getTerminalCapabilities()
			throws TerminalException {
		byte[] bytes = getBytes(KEY_TERMINAL_CAPABILITIES);
		return TerminalCapabilities.create(bytes);
	}

	private AdditionalTerminalCapabilities getAdditionalTerminalCapabilities()
			throws TerminalException {
		byte[] bytes = getBytes(KEY_ADDITIONAL_TERMINAL_CAPABILITIES);
		return AdditionalTerminalCapabilities.create(bytes);
	}

	private List<Application> getApplications() {
		final List<Application> applications = new ArrayList<Application>();
		String[] aids = getString(KEY_APPLICATIONS).split(",");
		for (String aid : aids) {
			Application application = getApplication(Hex.decode(aid));
			applications.add(application);
		}
		return applications;
	}

	private Application getApplication(byte[] aid) {
		byte[] floorLimit = getTerminalFloorLimit(aid);
		byte[] tdol = getBytes(aid, KEY_TDOL);
		if (tdol == null) {
			tdol = Bytes.EMPTY;
		}
		TerminalActionCodes terminalActionCodes = getTerminalActionCodes(aid);
		RandomTransactionSelectionData randomTransactionSelectionData = getRandomTransactionSelectionData(aid);
		return new Application(aid, floorLimit, tdol, terminalActionCodes,
				randomTransactionSelectionData);
	}

	private byte[] getBytes(byte[] aid, Key key) {
		return getBytes(new Key(Hex.encode(aid)).subKey(key));
	}

	private TerminalActionCodes getTerminalActionCodes(byte[] aid) {
		Key key = new Key(Hex.encode(aid)).subKey(KEY_TERMINAL_ACTION_CODE);
		byte[] tacDenial = getBytes(key.subKey("Denial"));
		byte[] tacOnline = getBytes(key.subKey("Online"));
		byte[] tacDefault = getBytes(key.subKey("Default"));
		TerminalActionCodesBuilder builder = new TerminalActionCodesBuilder();
		if (tacDenial != null) {
			builder.setTACDenial(tacDenial);
		}
		if (tacOnline != null) {
			builder.setTACOnline(tacOnline);
		}
		if (tacDefault != null) {
			builder.setTACDefault(tacDefault);
		}
		return builder.build();
	}

	private byte[] getTerminalFloorLimit(byte[] aid) {
		return getBytes(new Key(Hex.encode(aid))
				.subKey(KEY_TERMINAL_FLOOR_LIMIT));
	}

	private RandomTransactionSelectionData getRandomTransactionSelectionData(
			byte[] aid) {
		Key key = new Key(Hex.encode(aid)).subKey("RandomTransactionSelection");
		int targetPercentage = getInt(key.subKey("TargetPercentage"));
		int maxTargetPercentage = getInt(key.subKey("MaximumTargetPercentage"));
		byte[] thresholdValue = getBytes(key.subKey("ThresholdValue"));
		return new RandomTransactionSelectionData(targetPercentage,
				maxTargetPercentage, thresholdValue);
	}

	private String getString(Key key) {
		final String method = "getString(String)";
		String s = properties.getProperty(key.getKey());
		if (s == null) {
			if (LOG.isDebugEnabled()) {
				LOG.debug(method, "Property " + key.getKey() + " not set");
			}
			return null;
		}
		return s.trim();
	}

	private int getInt(Key key) {
		return Integer.parseInt(getString(key));
	}

	private byte[] getBytes(Key key) {
		String hex = getString(key);
		if (hex != null) {
			return Hex.decode(hex);
		}
		return null;
	}

	private CountryCode getCountryCode() {
		byte[] countryCode = Hex.decode("0"
				+ getString(KEY_TERMINAL_COUNTRY_CODE));
		return CountryCode.create(countryCode);
	}
}