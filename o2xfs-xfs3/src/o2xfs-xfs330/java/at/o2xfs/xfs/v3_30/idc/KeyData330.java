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

package at.o2xfs.xfs.v3_30.idc;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.ByteArray;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.WORD;

public class KeyData330 extends Struct {

	protected final Pointer rid = new Pointer();
	protected final WORD cAPublicKeyIndex = new WORD();
	protected final WORD caPublicKeyAlgorithmIndicator = new WORD();
	protected final Pointer cAPublicKeyExponent = new Pointer();
	protected final Pointer cAPublicKeyModulus = new Pointer();
	protected final Pointer cAPublicKeyCheckSum = new Pointer();

	protected KeyData330() {
		add(rid);
		add(cAPublicKeyIndex);
		add(caPublicKeyAlgorithmIndicator);
		add(cAPublicKeyExponent);
		add(cAPublicKeyModulus);
		add(cAPublicKeyCheckSum);
	}

	public KeyData330(Pointer p) {
		this();
		assignBuffer(p);
	}

	public KeyData330(KeyData330 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(KeyData330 copy) {
		rid.pointTo(new HexData330(copy.getRID()));
		cAPublicKeyIndex.set(copy.getCAPublicKeyIndex());
		caPublicKeyAlgorithmIndicator.set(copy.getCAPublicKeyAlgorithmIndicator());
		cAPublicKeyExponent.pointTo(new HexData330(copy.getCAPublicKeyExponent()));
		cAPublicKeyModulus.pointTo(new HexData330(copy.getCAPublicKeyModulus()));
		cAPublicKeyCheckSum.pointTo(new ByteArray(copy.getCAPublicKeyCheckSum()));
	}

	public byte[] getRID() {
		return new HexData330(rid).getData();
	}

	public int getCAPublicKeyIndex() {
		return cAPublicKeyIndex.get();
	}

	public int getCAPublicKeyAlgorithmIndicator() {
		return caPublicKeyAlgorithmIndicator.intValue();
	}

	public byte[] getCAPublicKeyExponent() {
		return new HexData330(cAPublicKeyExponent).getData();
	}

	public byte[] getCAPublicKeyModulus() {
		return new HexData330(cAPublicKeyModulus).getData();
	}

	public byte[] getCAPublicKeyCheckSum() {
		return cAPublicKeyCheckSum.buffer(20).get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(getRID())
				.append(getCAPublicKeyIndex())
				.append(getCAPublicKeyAlgorithmIndicator())
				.append(getCAPublicKeyExponent())
				.append(getCAPublicKeyModulus())
				.append(getCAPublicKeyCheckSum())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof KeyData330) {
			KeyData330 keyData330 = (KeyData330) obj;
			return new EqualsBuilder()
					.append(getRID(), keyData330.getRID())
					.append(getCAPublicKeyIndex(), keyData330.getCAPublicKeyIndex())
					.append(getCAPublicKeyAlgorithmIndicator(), keyData330.getCAPublicKeyAlgorithmIndicator())
					.append(getCAPublicKeyExponent(), keyData330.getCAPublicKeyExponent())
					.append(getCAPublicKeyModulus(), keyData330.getCAPublicKeyModulus())
					.append(getCAPublicKeyCheckSum(), keyData330.getCAPublicKeyCheckSum())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("rid", getRID())
				.append("cAPublicKeyIndex", getCAPublicKeyIndex())
				.append("caPublicKeyAlgorithmIndicator", getCAPublicKeyAlgorithmIndicator())
				.append("cAPublicKeyExponent", getCAPublicKeyExponent())
				.append("cAPublicKeyModulus", getCAPublicKeyModulus())
				.append("cAPublicKeyCheckSum", getCAPublicKeyCheckSum())
				.toString();
	}
}
