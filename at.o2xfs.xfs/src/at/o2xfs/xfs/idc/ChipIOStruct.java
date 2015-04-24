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

package at.o2xfs.xfs.idc;

import at.o2xfs.win32.ByteArray;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.ULONG;
import at.o2xfs.xfs.XfsWord;
import at.o2xfs.xfs.util.XfsConstants;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ChipIOStruct
		extends Struct {

	private final XfsWord<IDCChipProtocol> chipProtocol = new XfsWord<>(IDCChipProtocol.class);
	private final ULONG chipDataLength = new ULONG();
	private final Pointer chipData = new Pointer();

	public ChipIOStruct() {
		add(chipProtocol);
		add(chipDataLength);
		add(chipData);
	}

	public ChipIOStruct(final Pointer p) {
		this();
		assignBuffer(p);
	}

	public ChipIOStruct(final byte[] data) {
		this();
		allocate();
		setChipData(data);
	}

	public ChipIOStruct(final ChipIOStruct chipIO) {
		this();
		allocate();
		chipProtocol.set(chipIO.getChipProtocol());
		chipDataLength.set(chipIO.chipDataLength);
		final byte[] data = chipIO.getChipData();
		if (data != null) {
			chipData.pointTo(new ByteArray(data));
		}

	}

	public IDCChipProtocol getChipProtocol() {
		return XfsConstants.valueOf(chipProtocol, IDCChipProtocol.class);
	}

	public void setChipProtocol(final IDCChipProtocol chipProtocol) {
		this.chipProtocol.set(chipProtocol);
	}

	private long getChipDataLength() {
		return chipDataLength.longValue();
	}

	private void setChipDataLength(final long chipDataLength) {
		this.chipDataLength.set(chipDataLength);
	}

	public byte[] getChipData() {
		byte[] result = null;
		if (!Pointer.NULL.equals(chipData)) {
			result = chipData.buffer((int) getChipDataLength()).get();
		}
		return result;
	}

	public void setChipData(final byte[] chipData) {
		setChipDataLength(chipData.length);
		this.chipData.pointTo(new ByteArray(chipData));
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("chipProtocol", getChipProtocol())
										.append("chipDataLength", getChipDataLength())
										.append("chipData", getChipData())
										.toString();
	}
}