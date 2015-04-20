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

package at.o2xfs.emv.icc.atr;

import at.o2xfs.common.Hex;

import java.util.LinkedHashMap;
import java.util.Map;

public class ATR {

	private static final byte INVERSE_CONVENTION = 0x3f;

	private static final byte DIRECT_CONVENTION = 0x3b;

	private static final String TA = "TA";

	private static final String TB = "TB";

	private static final String TC = "TC";

	private static final String TD = "TD";

	private byte[] atr = null;

	private byte ts = 0;

	private T0 t0 = null;

	private final Map<String, Byte> interfaceCharacters;

	private byte[] historicalBytes = new byte[0];

	private Byte tck = null;

	public ATR(final byte[] atr) throws ATRException {
		if (atr.length == 0) {
			throw new ATRException("ATR is empty");
		}
		interfaceCharacters = new LinkedHashMap<String, Byte>();
		this.atr = new byte[atr.length];
		System.arraycopy(atr, 0, this.atr, 0, atr.length);
		parse();
	}

	private void parse() throws ATRException {
		parseTS();
		parseT0();
		int index = parseInterfaceCharacters();
		parseHistoricalBytes(index);
		index += historicalBytes.length;
		parseTCK(index);
		if (tck != null) {
			validateATR();
			index++;
		}
		if (index != atr.length) {
			throw new ATRException("ATR too long");
		}
	}

	private void parseTS() throws ATRException {
		ts = atr[0];
		if (ts != INVERSE_CONVENTION && ts != DIRECT_CONVENTION) {
			throw new ATRException("Invalid TS: " + Hex.encode(ts));
		}
	}

	private void parseT0() throws ATRException {
		if (atr.length < 2) {
			throw new ATRException("T0 is missing");
		}
		t0 = new T0(atr[1]);
	}

	private int parseInterfaceCharacters() throws ATRException {
		int i = 2;
		int n = 1;
		TD nextTD = t0;
		while (nextTD.hasInterfaceBytes()) {
			if (nextTD.isTA()) {
				parseInterfaceCharacter(TA + n, i++);
			}
			if (nextTD.isTB()) {
				parseInterfaceCharacter(TB + n, i++);
			}
			if (nextTD.isTC()) {
				parseInterfaceCharacter(TC + n, i++);
			}
			if (nextTD.isTD()) {
				parseInterfaceCharacter(TD + n, i++);
				nextTD = new TD(interfaceCharacters.get(TD + n++).byteValue());
			} else {
				break;
			}
		}
		return i;
	}

	private void parseInterfaceCharacter(final String key, int index) throws ATRException {
		if (index >= atr.length) {
			throw new ATRException("Expected " + key + " at position " + index);
		}
		final Byte value = Byte.valueOf(atr[index]);
		interfaceCharacters.put(key, value);
	}

	private void parseHistoricalBytes(final int i) throws ATRException {
		historicalBytes = new byte[t0.getNumberOfHistoricalBytes()];
		if (i + historicalBytes.length > atr.length) {
			throw new ATRException("Not enough historical bytes");
		}
		System.arraycopy(atr, i, historicalBytes, 0, historicalBytes.length);
	}

	private void parseTCK(int index) {
		if (index == atr.length) {
			return;
		}
		tck = Byte.valueOf(atr[index]);

	}

	private void validateATR() throws ATRException {
		byte b = 0;
		for (int i = 1; i < atr.length; i++) {
			b ^= atr[i];
		}
		if (b != 0) {
			throw new ATRException("Incorrect TCK");
		}
	}

	public byte getTA(final int n) {
		return interfaceCharacters.get(TA + n).byteValue();
	}

	public byte getTB(final int n) {
		return interfaceCharacters.get(TB + n).byteValue();
	}

	public byte getTC(final int n) {
		return interfaceCharacters.get(TC + n).byteValue();
	}

	public byte getTD(final int n) {
		return interfaceCharacters.get(TD + n).byteValue();
	}

	public boolean containsTA(int n) {
		return interfaceCharacters.containsKey(TA + n);
	}

	public boolean containsTB(int n) {
		return interfaceCharacters.containsKey(TB + n);
	}

	public boolean containsTC(int n) {
		return interfaceCharacters.containsKey(TC + n);
	}

	public boolean containsTD(int n) {
		return interfaceCharacters.containsKey(TD + n);
	}

	@Override
	public String toString() {
		return "TS=" + Hex.encode(ts)
				+ ",T0="
				+ t0
				+ ",interfaceCharacters="
				+ toString(interfaceCharacters)
				+ ",historicalBytes="
				+ Hex.encode(historicalBytes)
				+ ",TCK="
				+ (tck == null ? "" : Hex.encode(tck.byteValue()));
	}

	private String toString(final Map<String, Byte> map) {
		final StringBuilder str = new StringBuilder();
		int i = 0;
		int iMax = map.size() - 1;
		for (Map.Entry<String, Byte> entry : map.entrySet()) {
			str.append(entry.getKey() + "=" + Hex.encode(entry.getValue().byteValue()));
			if (iMax == i) {
				break;
			}
			str.append(", ");
			i++;
		}
		return str.toString();
	}
}