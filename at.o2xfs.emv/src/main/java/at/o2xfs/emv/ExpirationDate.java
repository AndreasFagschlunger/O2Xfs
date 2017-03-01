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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import at.o2xfs.common.Hex;

public final class ExpirationDate {

	public class ExpirationDateException extends Exception {

		private ExpirationDateException(String message) {
			super(message);
		}

		private ExpirationDateException(Throwable cause) {
			super(cause);
		}
	}

	protected static final String TWO_BYTE_DATE_PATTERN = "MMyy";

	protected static final String THREE_BYTE_DATE_PATTERN = "yyMMdd";

	private final Calendar expirationDate;

	public ExpirationDate(byte[] n) throws ExpirationDateException {
		if (n == null) {
			throw new ExpirationDateException(new NullPointerException(
					"n must not be null"));
		}
		switch (n.length) {
			case 2:
				expirationDate = parse2ByteFormat(n);
				break;
			case 3:
				expirationDate = parse3ByteFormat(n);
				break;
			default:
				throw new ExpirationDateException("Illegal Expiration Date: "
						+ Hex.encode(n));
		}
	}

	private Calendar parse2ByteFormat(byte[] n) throws ExpirationDateException {
		try {
			DateFormat dateFormat = new SimpleDateFormat(TWO_BYTE_DATE_PATTERN);
			Date date = dateFormat.parse(Hex.encode(n));
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, 1);
			calendar.add(Calendar.MILLISECOND, -1);
			return calendar;
		} catch (ParseException e) {
			throw new ExpirationDateException(e);
		}
	}

	private Calendar parse3ByteFormat(byte[] n) throws ExpirationDateException {
		try {
			DateFormat dateFormat = new SimpleDateFormat(
					THREE_BYTE_DATE_PATTERN);
			Date date = dateFormat.parse(Hex.encode(n));
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			calendar.add(Calendar.MILLISECOND, -1);
			return calendar;
		} catch (ParseException e) {
			throw new ExpirationDateException(e);
		}
	}

	public boolean hasExpired() {
		Calendar currentDate = Calendar.getInstance();
		setField(currentDate, Calendar.HOUR_OF_DAY);
		setField(currentDate, Calendar.MINUTE);
		setField(currentDate, Calendar.SECOND);
		setField(currentDate, Calendar.MILLISECOND);
		return expirationDate.compareTo(currentDate) < 0;
	}

	private void setField(Calendar currentDate, int field) {
		currentDate.set(field, expirationDate.get(field));
	}
}