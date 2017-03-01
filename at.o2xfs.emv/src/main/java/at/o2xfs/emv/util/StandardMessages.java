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

package at.o2xfs.emv.util;

import java.util.ResourceBundle;

import at.o2xfs.common.Hex;

public final class StandardMessages {

	/**
	 * Indicates the transaction amount to both the cardholder and attendant.
	 */
	public static final StandardMessage AMOUNT = new StandardMessage(0x01);

	/**
	 * Invites a response from the cardholder indicating agreement or
	 * disagreement with the displayed transaction amount. Agreement or
	 * disagreement should be denoted by pressing the 'Enter' or 'Cancel' keys,
	 * respectively.
	 */
	public static final StandardMessage AMOUNT_OK = new StandardMessage(0x02);

	/**
	 * Indicates to the cardholder and attendant that the transaction has been
	 * approved.
	 */
	public static final StandardMessage APPROVED = new StandardMessage(0x03);

	/**
	 * Indicates to the cardholder or attendant to contact the issuer or
	 * acquirer, as appropriate, such as for voice referrals.
	 */
	public static final StandardMessage CALL_YOUR_BANK = new StandardMessage(
			0x04);

	/**
	 * When used with the ‘ENTER PIN’ message, instructs the cardholder to
	 * validate PIN entry by pressing the 'Enter' key or to cancel PIN entry by
	 * pressing the 'Cancel' key.
	 */
	public static final StandardMessage CANCEL_OR_ENTER = new StandardMessage(
			0x05);

	/**
	 * Indicates to the cardholder or attendant a malfunction of the card or a
	 * non-conformance to answer-to-reset.
	 */
	public static final StandardMessage CARD_ERROR = new StandardMessage(0x06);

	/**
	 * Indicates to the cardholder and attendant that the online or offline
	 * authorisation has not been approved.
	 */
	public static final StandardMessage DECLINED = new StandardMessage(0x07);

	/**
	 * Instructs the cardholder at an unattended terminal or the attendant at an
	 * attended terminal to enter the amount of the transaction. Confirmation or
	 * cancellation of amount entry should be denoted by pressing the 'Enter' or
	 * 'Cancel' keys, respectively.
	 */
	public static final StandardMessage ENTER_AMOUNT = new StandardMessage(0x08);

	/**
	 * Invites the cardholder to enter the PIN for the first and subsequent PIN
	 * tries. An asterisk is displayed for each digit of the PIN entered.
	 */
	public static final StandardMessage ENTER_PIN = new StandardMessage(0x09);

	/**
	 * Indicates that the PIN entered by the cardholder does not match the
	 * reference PIN.
	 */
	public static final StandardMessage INCORRECT_PIN = new StandardMessage(
			0x0A);

	/**
	 * Instructs to insert the ICC into the IFD. Correct insertion should be
	 * noted by displaying the message ‘PLEASE WAIT’ to reassure the cardholder
	 * or attendant that the transaction is being processed.
	 */
	public static final StandardMessage INSERT_CARD = new StandardMessage(0x0B);

	/**
	 * Indicates to the cardholder and attendant that the application is not
	 * supported or there is a restriction on the use of the application; for
	 * example, the card has expired.
	 */
	public static final StandardMessage NOT_ACCEPTED = new StandardMessage(0x0C);

	/**
	 * Indicates that offline PIN verification was successful.
	 */
	public static final StandardMessage PIN_OK = new StandardMessage(0x0D);

	/**
	 * Indicates to the cardholder and attendant that the transaction is being
	 * processed.
	 */
	public static final StandardMessage PLEASE_WAIT = new StandardMessage(0x0E);

	/**
	 * Displayed to the cardholder or attendant when the card is removed before
	 * the processing of a transaction is complete, or when the transaction is
	 * aborted because of a power failure, or when the system or terminal has
	 * malfunctioned, such as communication errors or time-outs.
	 */
	public static final StandardMessage PROCESSING_ERROR = new StandardMessage(
			0x0F);

	/**
	 * Instructs to remove the ICC from the IFD.
	 */
	public static final StandardMessage REMOVE_CARD = new StandardMessage(0x10);

	/**
	 * Instructs to insert ICC into the IC reader of the IFD, when the IC and
	 * magnetic stripe readers are not combined.
	 */
	public static final StandardMessage USE_CHIP_READER = new StandardMessage(
			0x11);

	/**
	 * Instructs to insert ICC into the magnetic stripe reader of the terminal
	 * after IC reading fails, when the IC and magnetic stripe readers are not
	 * combined.
	 */
	public static final StandardMessage USE_MAG_STRIPE = new StandardMessage(
			0x12);

	/**
	 * Invites the cardholder to re-execute the last action performed.
	 */
	public static final StandardMessage TRY_AGAIN = new StandardMessage(0x13);

	private static final ResourceBundle MESSAGES = ResourceBundle
			.getBundle(StandardMessages.class.getCanonicalName());

	private StandardMessages() {
		throw new AssertionError();
	}

	public static final String getMessage(StandardMessage standardMessage) {
		return MESSAGES.getString(Hex.encode(standardMessage.getID()));
	}
}