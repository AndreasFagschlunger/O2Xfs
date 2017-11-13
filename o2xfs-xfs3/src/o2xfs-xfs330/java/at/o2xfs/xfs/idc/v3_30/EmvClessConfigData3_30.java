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

package at.o2xfs.xfs.idc.v3_30;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;

public class EmvClessConfigData3_30 extends Struct {

	protected final Pointer terminalData = new Pointer();
	protected final Pointer aidData = new Pointer();
	protected final Pointer keyData = new Pointer();

	protected EmvClessConfigData3_30() {
		add(terminalData);
		add(aidData);
		add(keyData);
	}

	public EmvClessConfigData3_30(Pointer p) {
		this();
		assignBuffer(p);
	}

	public EmvClessConfigData3_30(EmvClessConfigData3_30 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(EmvClessConfigData3_30 copy) {
		terminalData.pointTo(new HexData3_30(copy.getTerminalData()));
		aidData.pointTo(new AidDataArray3_30(copy.getAIDData()));
		keyData.pointTo(new KeyDataArray3_30(copy.getKeyData()));
	}

	public byte[] getTerminalData() {
		return new HexData3_30(terminalData).getData();
	}

	public AidData3_30[] getAIDData() {
		return new AidDataArray3_30(aidData).get();
	}

	public KeyData3_30[] getKeyData() {
		return new KeyDataArray3_30(keyData).get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getTerminalData()).append(getAIDData()).append(getKeyData()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof EmvClessConfigData3_30) {
			EmvClessConfigData3_30 emvClessConfigData3_30 = (EmvClessConfigData3_30) obj;
			return new EqualsBuilder()
					.append(getTerminalData(), emvClessConfigData3_30.getTerminalData())
					.append(getAIDData(), emvClessConfigData3_30.getAIDData())
					.append(getKeyData(), emvClessConfigData3_30.getKeyData())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("terminalData", getTerminalData())
				.append("aidData", getAIDData())
				.append("keyData", getKeyData())
				.toString();
	}
}