/*
 * Copyright (c) 2018, Andreas Fagschlunger. All rights reserved.
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

package at.o2xfs.xfs.v3_00.ptr;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.common.Bytes;
import at.o2xfs.win32.ByteArray;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.ULONG;
import at.o2xfs.xfs.ptr.InputData;
import at.o2xfs.xfs.win32.XfsWord;

public class RawData3 extends Struct {

	public static class Builder {

		private InputData inputData = InputData.NOINPUTDATA;
		private byte[] data;

		public Builder(byte[] data) {
			this.data = Bytes.copy(data);
		}

		public Builder inputData(InputData inputData) {
			this.inputData = inputData;
			return this;
		}

		public RawData3 build() {
			return new RawData3(this);
		}
	}

	protected final XfsWord<InputData> inputData = new XfsWord<>(InputData.class);
	protected final ULONG size = new ULONG();
	protected final Pointer data = new Pointer();

	protected RawData3() {
		add(inputData);
		add(size);
		add(data);
	}

	public RawData3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public RawData3(RawData3 copy) {
		this();
		allocate();
		set(copy);
	}

	private RawData3(Builder builder) {
		this();
		allocate();
		inputData.set(builder.inputData);
		size.set(builder.data.length);
		data.pointTo(new ByteArray(builder.data));
	}

	protected void set(RawData3 copy) {
		inputData.set(copy.getInputData());
		size.set(copy.size);
		data.pointTo(new ByteArray(copy.getData()));
	}

	public InputData getInputData() {
		return inputData.get();
	}

	public byte[] getData() {
		return data.buffer(size.intValue()).get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getInputData()).append(getSize()).append(getData()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RawData3) {
			RawData3 rawData3 = (RawData3) obj;
			return new EqualsBuilder()
					.append(getInputData(), rawData3.getInputData())
					.append(getSize(), rawData3.getSize())
					.append(getData(), rawData3.getData())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("inputData", getInputData())
				.append("size", getSize())
				.append("data", getData())
				.toString();
	}
}
