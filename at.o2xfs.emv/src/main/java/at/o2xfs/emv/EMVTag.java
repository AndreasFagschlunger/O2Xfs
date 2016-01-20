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

import at.o2xfs.emv.tlv.Tag;

public enum EMVTag {

	/**
	 * Application Dedicated File (ADF) Name
	 */
	ADF(0x4F, Format.BINARY),

	/**
	 * Application Label
	 */
	APPLICATION_LABEL(0x50, Format.ALPHANUMERIC_SPECIAL),

	/**
	 * Track 2 Equivalent Data
	 */
	TRACK_2_EQUIVALENT_DATA(0x57, Format.BINARY),

	/**
	 * Application Primary Account Number (PAN)
	 */
	APPLICATION_PRIMARY_ACCOUNT_NUMBER(0x5A, Format.COMPRESSED_NUMERIC),

	/**
	 * Application Expiration Date
	 */
	APPLICATION_EXPIRATION_DATE(0x5F24, Format.NUMERIC),

	/**
	 * Application Effective Date
	 */
	APPLICATION_EFFECTIVE_DATE(0x5F25, Format.NUMERIC),

	/**
	 * Issuer Country Code
	 */
	ISSUER_COUNTRY_CODE(0x5F28, Format.NUMERIC),

	/**
	 * Language Preference
	 */
	LANGUAGE_PREFERENCE(0x5F2D, Format.ALPHANUMERIC),

	/**
	 * Transaction Currency Code
	 */
	TRANSACTION_CURRENCY_CODE(0x5F2A, Format.NUMERIC),

	/**
	 * Application Primary Account Number (PAN) Sequence Number
	 */
	APPLICATION_PRIMARY_ACCOUNT_NUMBER_SEQUENCE_NUMBER(0x5F34, Format.NUMERIC),

	/**
	 * Transaction Currency Exponent
	 */
	TRANSACTION_CURRENCY_EXPONENT(0x5F36, Format.NUMERIC),

	/**
	 * File Control Information (FCI) Template
	 */
	FCI_TEMPLATE(0x6F, Format.VARIABLE),

	/**
	 * Application Template
	 */
	APPLICATION_TEMPLATE(0x61, Format.BINARY),

	/**
	 * READ RECORD Response Message Template
	 */
	READ_RECORD(0x70, Format.VARIABLE),

	/**
	 * Issuer Script Template 1
	 */
	ISSUER_SCRIPT_TEMPLATE_1(0x71, Format.BINARY),

	/**
	 * Issuer Script Template 2
	 */
	ISSUER_SCRIPT_TEMPLATE_2(0x72, Format.BINARY),

	/**
	 * Response Message Template Format 2
	 */
	RESPONSE_MESSAGE_TEMPLATE_FORMAT_2(0x77, Format.VARIABLE),

	/**
	 * Response Message Template Format 1
	 */
	RESPONSE_MESSAGE_TEMPLATE_FORMAT_1(0x80, Format.VARIABLE),

	/**
	 * Amount, Authorised (Binary)
	 */
	AMOUNT_AUTHORISED_BINARY(0x81, Format.BINARY),

	/**
	 * Application Interchange Profile
	 */
	APPLICATION_INTERCHANGE_PROFILE(0x82, Format.BINARY),

	/**
	 * Command Template
	 */
	COMMAND_TEMPLATE(0x83, Format.BINARY),

	/**
	 * Dedicated File (DF) Name
	 */
	DEDICATED_FILE_NAME(0x84, Format.BINARY),

	/**
	 * Issuer Script Command
	 */
	ISSUER_SCRIPT_COMMAND(0x86, Format.BINARY),

	/**
	 * Application Priority Indicator
	 */
	APPLICATION_PRIORITY_INDICATOR(0x87, Format.BINARY),

	/**
	 * Short File Identifier (SFI)
	 */
	SFI(0x88, Format.BINARY),

	/**
	 * Authorisation Response Code
	 */
	AUTHORISATION_RESPONSE_CODE(0x8A, Format.ALPHANUMERIC),

	/**
	 * Card Risk Management Data Object List 1 (CDOL1)
	 */
	CARD_RISK_MANAGEMENT_DATA_OBJECT_LIST_1(0x8C, Format.BINARY),

	/**
	 * Card Risk Management Data Object List 2 (CDOL2)
	 */
	CARD_RISK_MANAGEMENT_DATA_OBJECT_LIST_2(0x8D, Format.BINARY),

	/**
	 * Cardholder Verification Method (CVM) List
	 */
	CARDHOLDER_VERIFICATION_METHOD_LIST(0x8E, Format.BINARY),

	/**
	 * Certification Authority Public Key Index
	 */
	CERTIFICATION_AUTHORITY_PUBLIC_KEY_INDEX(0x8F, Format.BINARY),

	/**
	 * Issuer Public Key Certificate
	 */
	ISSUER_PUBLIC_KEY_CERTIFICATE(0x90, Format.BINARY),

	/**
	 * Issuer Authentication Data
	 */
	ISSUER_AUTHENTICATION_DATA(0x91, Format.BINARY),

	/**
	 * Issuer Public Key Remainder
	 */
	ISSUER_PUBLIC_KEY_REMAINDER(0x92, Format.BINARY),

	/**
	 * Signed Static Application Data
	 */
	SIGNED_STATIC_APPLICATION_DATA(0x93, Format.BINARY),

	/**
	 * Application File Locator (AFL)
	 */
	APPLICATION_FILE_LOCATOR(0x94, Format.VARIABLE),

	/**
	 * Terminal Verification Results
	 */
	TERMINAL_VERIFICATION_RESULTS(0x95, Format.BINARY),

	/**
	 * Transaction Certificate Data Object List (TDOL)
	 */
	TRANSACTION_CERTIFICATE_DATA_OBJECT_LIST(0x97, Format.BINARY),

	/**
	 * Transaction Certificate (TC) Hash Value
	 */
	TRANSACTION_CERTIFICATE_HASH_VALUE(0x98, Format.BINARY),

	/**
	 * Transaction Date
	 */
	TRANSACTION_DATE(0x9A, Format.NUMERIC),

	/**
	 * Transaction Type
	 */
	TRANSACTION_TYPE(0x9C, Format.NUMERIC),

	/**
	 * Transaction Status Information
	 */
	TRANSACTION_STATUS_INFORMATION(0x9B, Format.BINARY),

	/**
	 * File Control Information (FCI) Proprietary Template
	 */
	FCI_PROPRIETARY_TEMPLATE(0xA5, Format.VARIABLE),

	/**
	 * Acquirer Identifier
	 */
	ACQUIRER_IDENTIFIER(0x9F01, Format.NUMERIC),

	/**
	 * Amount, Authorised (Numeric)
	 */
	AMOUNT_AUTHORISED_NUMERIC(0x9F02, Format.NUMERIC),

	/**
	 * Amount, Other (Numeric)
	 */
	AMOUNT_OTHER_NUMERIC(0x9F03, Format.NUMERIC),

	/**
	 * Amount, Other (Binary)
	 */
	AMOUNT_OTHER_BINARY(0x9F04, Format.BINARY),

	/**
	 * Application Identifier (AID) – terminal
	 */
	APPLICATION_IDENTIFIER_TERMINAL(0x9F06, Format.BINARY),

	/**
	 * Application Usage Control
	 */
	APPLICATION_USAGE_CONTROL(0x9F07, Format.BINARY),

	/**
	 * Application Version Number (ICC)
	 */
	APPLICATION_VERSION_NUMBER_ICC(0x9F08, Format.BINARY),

	/**
	 * Application Version Number (Terminal)
	 */
	APPLICATION_VERSION_NUMBER_TERMINAL(0x9F09, Format.BINARY),

	/**
	 * Issuer Action Code - Default
	 */
	ISSUER_ACTION_CODE_DEFAULT(0x9F0D, Format.BINARY),

	/**
	 * Issuer Action Code - Denial
	 */
	ISSUER_ACTION_CODE_DENIAL(0x9F0E, Format.BINARY),

	/**
	 * Issuer Action Code - Online
	 */
	ISSUER_ACTION_CODE_ONLINE(0x9F0F, Format.BINARY),

	/**
	 * Issuer Application Data
	 */
	ISSUER_APPLICATION_DATA(0x9F10, Format.BINARY),

	/**
	 * Issuer Code Table Index
	 */
	ISSUER_CODE_TABLE_INDEX(0x9F11, Format.NUMERIC),

	/**
	 * Application Preferred Name
	 */
	APPLICATION_PREFERRED_NAME(0x9F12, Format.ALPHANUMERIC_SPECIAL),

	/**
	 * Last Online Application Transaction Counter (ATC) Register
	 */
	LAST_ONLINE_ATC_REGISTER(0x9F13, Format.BINARY),

	/**
	 * Lower Consecutive Offline Limit
	 */
	LOWER_CONSECUTIVE_OFFLINE_LIMIT(0x9F14, Format.BINARY),

	/**
	 * Merchant Identifier
	 */
	MERCHANT_IDENTIFIER(0x9F16, Format.ALPHANUMERIC_SPECIAL),

	/**
	 * Personal Identification Number (PIN) Try Counter
	 */
	PIN_TRY_COUNTER(0x9F17, Format.BINARY),

	/**
	 * Issuer Script Identifier
	 */
	ISSUER_SCRIPT_IDENTIFIER(0x9F18, Format.BINARY),

	/**
	 * Terminal Country Code
	 */
	TERMINAL_COUNTRY_CODE(0x9F1A, Format.NUMERIC),

	/**
	 * Terminal Floor Limit
	 */
	TERMINAL_FLOOR_LIMIT(0x9F1B, Format.NUMERIC),

	/**
	 * Terminal Identification
	 */
	TERMINAL_IDENTIFICATION(0x9F1C, Format.ALPHANUMERIC),

	/**
	 * Terminal Risk Management Data
	 */
	TERMINAL_RISK_MANAGEMENT_DATA(0x9F1D, Format.BINARY),

	/**
	 * Interface Device (IFD) Serial Number
	 */
	INTERFACE_DEVICE_SERIAL_NUMBER(0x9F1E, Format.ALPHANUMERIC),

	/**
	 * Track 1 Discretionary Data
	 */
	TRACK_1_DISCRETIONARY_DATA(0x9F1F, Format.VARIABLE),

	/**
	 * Transaction Time
	 */
	TRANSACTION_TIME(0x9F21, Format.NUMERIC),

	/**
	 * Upper Consecutive Offline Limit
	 */
	UPPER_CONSECUTIVE_OFFLINE_LIMIT(0x9F23, Format.BINARY),

	/**
	 * Application Cryptogram
	 */
	APPLICATION_CRYPTOGRAM(0x9F26, Format.BINARY),

	/**
	 * Cryptogram Information Data
	 */
	CRYPTOGRAM_INFORMATION_DATA(0x9F27, Format.BINARY),

	/**
	 * Integrated Circuit Card (ICC) PIN Encipherment Public Key Certificate
	 */
	ICC_PIN_ENCIPHERMENT_PUBLIC_KEY_CERTIFICATE(0x9F2D, Format.BINARY),

	/**
	 * Integrated Circuit Card (ICC) PIN Encipherment Public Key Exponent
	 */
	ICC_PIN_ENCIPHERMENT_PUBLIC_KEY_EXPONENT(0x9F2E, Format.BINARY),

	/**
	 * Integrated Circuit Card (ICC) PIN Encipherment Public Key Remainder
	 */
	ICC_PIN_ENCIPHERMENT_PUBLIC_KEY_REMAINDER(0x9F2F, Format.BINARY),

	/**
	 * Issuer Public Key Exponent
	 */
	ISSUER_PUBLIC_KEY_EXPONENT(0x9F32, Format.BINARY),

	/**
	 * Terminal Capabilities
	 */
	TERMINAL_CAPABILITIES(0x9F33, Format.BINARY),

	/**
	 * Cardholder Verification Method (CVM) Results
	 */
	CARDHOLDER_VERIFICATION_METHOD_RESULTS(0x9F34, Format.BINARY),

	/**
	 * Terminal Type
	 */
	TERMINAL_TYPE(0x9F35, Format.BINARY),

	/**
	 * Application Transaction Counter (ATC)
	 */
	APPLICATION_TRANSACTION_COUNTER(0x9F36, Format.BINARY),

	/**
	 * Unpredictable Number
	 */
	UNPREDICTABLE_NUMBER(0x9F37, Format.BINARY),

	/**
	 * Processing Options Data Object List (PDOL)
	 */
	PROCESSING_OPTIONS_DATA_OBJECT_LIST(0x9F38, Format.BINARY),

	/**
	 * Additional Terminal Capabilities
	 */
	ADDITIONAL_TERMINAL_CAPABILITIES(0x9F40, Format.BINARY),

	/**
	 * Transaction Sequence Counter
	 */
	TRANSACTION_SEQUENCE_COUNTER(0x9F41, Format.NUMERIC),

	/**
	 * Application Currency Code
	 */
	APPLICATION_CURRENCY_CODE(0x9F42, Format.NUMERIC),

	/**
	 * Application Currency Exponent
	 */
	APPLICATION_CURRENCY_EXPONENT(0x9F44, Format.NUMERIC),

	/**
	 * Data Authentication Code
	 */
	DATA_AUTHENTICATION_CODE(0x9F45, Format.BINARY),

	/**
	 * Integrated Circuit Card (ICC) Public Key Certificate
	 */
	ICC_PUBLIC_KEY_CERTIFICATE(0x9F46, Format.BINARY),

	/**
	 * Integrated Circuit Card (ICC) Public Key Exponent
	 */
	ICC_PUBLIC_KEY_EXPONENT(0x9F47, Format.BINARY),

	/**
	 * Integrated Circuit Card (ICC) Public Key Remainder
	 */
	ICC_PUBLIC_KEY_REMAINDER(0x9F48, Format.BINARY),

	/**
	 * Dynamic Data Authentication Data Object List (DDOL)
	 */
	DYNAMIC_DATA_AUTHENTICATION_DATA_OBJECT_LIST(0x9F49, Format.BINARY),

	/**
	 * Static Data Authentication Tag List
	 */
	STATIC_DATA_AUTHENTICATION_TAG_LIST(0x9F4A, Format.VARIABLE),

	/**
	 * Signed Dynamic Application Data
	 */
	SIGNED_DYNAMIC_APPLICATION_DATA(0x9F4B, Format.BINARY),

	/**
	 * ICC Dynamic Number
	 */
	ICC_DYNAMIC_NUMBER(0x9F4C, Format.BINARY),

	/**
	 * Log Format
	 */
	LOG_FORMAT(0x9F4F, Format.BINARY);

	private final Tag tag;

	private final Format format;

	private EMVTag(int tag) {
		this(tag, Format.VARIABLE);
	}

	private EMVTag(int tag, Format format) {
		this.tag = Tag.create(tag);
		this.format = format;
	}

	public Tag getTag() {
		return tag;
	}

	public Format getFormat() {
		return format;
	}

	public static final EMVTag getByTag(final Tag tag) {
		for (EMVTag emvTag : values()) {
			if (emvTag.tag.equals(tag)) {
				return emvTag;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return name() + "(" + tag + ")";
	}

}