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

import at.o2xfs.win32.BOOL;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.ULONG;

public class AidData330 extends Struct {

	protected final Pointer aid = new Pointer();
	protected final BOOL partialSelection = new BOOL();
	protected final ULONG transactionType = new ULONG();
	protected final Pointer kernelIdentifier = new Pointer();
	protected final Pointer configData = new Pointer();

	protected AidData330() {
		add(aid);
		add(partialSelection);
		add(transactionType);
		add(kernelIdentifier);
		add(configData);
	}

	public AidData330(Pointer p) {
		this();
		assignBuffer(p);
	}

	public AidData330(AidData330 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(AidData330 copy) {
		aid.pointTo(new HexData330(copy.getAID()));
		partialSelection.set(copy.isPartialSelection());
		transactionType.set(copy.getTransactionType());
		kernelIdentifier.pointTo(new HexData330(copy.getKernelIdentifier()));
		configData.pointTo(new HexData330(copy.getConfigData()));
	}

	public byte[] getAID() {
		return new HexData330(aid).getData();
	}

	public boolean isPartialSelection() {
		return partialSelection.get();
	}

	public long getTransactionType() {
		return transactionType.get();
	}

	public byte[] getKernelIdentifier() {
		return new HexData330(kernelIdentifier).getData();
	}

	public byte[] getConfigData() {
		return new HexData330(configData).getData();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(getAID())
				.append(isPartialSelection())
				.append(getTransactionType())
				.append(getKernelIdentifier())
				.append(getConfigData())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AidData330) {
			AidData330 aidData330 = (AidData330) obj;
			return new EqualsBuilder()
					.append(getAID(), aidData330.getAID())
					.append(isPartialSelection(), aidData330.isPartialSelection())
					.append(getTransactionType(), aidData330.getTransactionType())
					.append(getKernelIdentifier(), aidData330.getKernelIdentifier())
					.append(getConfigData(), aidData330.getConfigData())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("aid", getAID())
				.append("partialSelection", isPartialSelection())
				.append("transactionType", getTransactionType())
				.append("kernelIdentifier", getKernelIdentifier())
				.append("configData", getConfigData())
				.toString();
	}
}
