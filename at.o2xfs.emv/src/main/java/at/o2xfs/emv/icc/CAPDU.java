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

package at.o2xfs.emv.icc;

import at.o2xfs.common.Bytes;
import at.o2xfs.common.Hex;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class CAPDU {

	/**
	 * Class of instruction
	 */
	private final int cla;

	/**
	 * Instruction code
	 */
	private final int ins;

	/**
	 * Instruction parameter 1
	 */
	private final int p1;

	/**
	 * Instruction parameter 2
	 */
	private final int p2;

	/**
	 * String of data bytes sent in command
	 */
	private final byte[] data;

	/**
	 * Maximum number of data bytes expected in data field of response
	 */
	private final byte[] le;

	/**
	 * Case 1
	 *
	 * @param cla
	 * @param ins
	 * @param p1
	 * @param p2
	 */
	public CAPDU(int cla, int ins, int p1, int p2) {
		this(cla, ins, p1, p2, Bytes.EMPTY, Bytes.EMPTY);
	}

	/**
	 * Case 2
	 *
	 * @param cla
	 * @param ins
	 * @param p1
	 * @param p2
	 * @param le
	 */
	public CAPDU(int cla, int ins, int p1, int p2, int le) {
		this(cla, ins, p1, p2, Bytes.EMPTY, le);
	}

	/**
	 * Case 3
	 *
	 * @param cla
	 * @param ins
	 * @param p1
	 * @param p2
	 * @param data
	 */
	public CAPDU(int cla, int ins, int p1, int p2, byte[] data) {
		this(cla, ins, p1, p2, data, Bytes.EMPTY);
	}

	/**
	 * Case 4
	 *
	 * @param cla
	 * @param ins
	 * @param p1
	 * @param p2
	 * @param data
	 * @param le
	 */
	public CAPDU(int cla, int ins, int p1, int p2, byte[] data, int le) {
		this(cla, ins, p1, p2, data, new byte[] { (byte) le });
	}

	private CAPDU(int cla, int ins, int p1, int p2, byte[] data, byte[] le) {
		this.cla = cla;
		this.ins = ins;
		this.p1 = p1;
		this.p2 = p2;
		this.data = Bytes.copy(data);
		this.le = le;
	}

	public int getCla() {
		return cla;
	}

	public int getIns() {
		return ins;
	}

	public int getP1() {
		return p1;
	}

	public int getP2() {
		return p2;
	}

	public byte[] getData() {
		return Bytes.copy(data);
	}

	public byte[] getLe() {
		return Bytes.copy(le);
	}

	public boolean isCase1() {
		return data.length == 0 && le.length == 0;
	}

	public boolean isCase2() {
		return data.length == 0 && le.length != 0;
	}

	public boolean isCase3() {
		return data.length != 0 && le.length == 0;
	}

	public boolean isCase4() {
		return data.length != 0 && le.length != 0;
	}

	public byte[] getBytes() {
		final int size = size();
		final byte[] capdu = new byte[size];
		int i = 0;
		capdu[i++] = (byte) cla;
		capdu[i++] = (byte) ins;
		capdu[i++] = (byte) p1;
		capdu[i++] = (byte) p2;
		if (data.length > 0) {
			capdu[i++] = (byte) data.length;
			System.arraycopy(data, 0, capdu, i, data.length);
			i += data.length;
		}
		System.arraycopy(le, 0, capdu, i, le.length);
		return capdu;
	}

	private int size() {
		return 4 + (data.length > 0 ? data.length + 1 : 0) + le.length;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(cla).append(ins).append(p1).append(p2).append(data).append(le).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CAPDU) {
			CAPDU capdu = CAPDU.class.cast(obj);
			return new EqualsBuilder().append(cla, capdu.cla)
										.append(ins, capdu.ins)
										.append(p1, capdu.p1)
										.append(p2, capdu.p2)
										.append(data, capdu.data)
										.append(le, capdu.le)
										.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("CLA: " + Hex.encode(cla)
											+ ", INS: "
											+ Hex.encode(ins)
											+ ", P1: "
											+ Hex.encode(p1)
											+ ", P2: "
											+ Hex.encode(p2));
		if (data.length > 0) {
			s.append(", Lc: " + data.length).append(", Data: " + Hex.encode(data));
		}
		if (le.length > 0) {
			s.append(", Le: " + Bytes.toInt(le[0]));
		}
		return s.toString();
	}
}