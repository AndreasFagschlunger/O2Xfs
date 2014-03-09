/*
 * Copyright (c) 2014, Andreas Fagschlunger. All rights reserved.
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

package at.o2xfs.xfs.ptr;

import org.apache.commons.lang.builder.ToStringBuilder;

import at.o2xfs.win32.ByteArray;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.ULONG;
import at.o2xfs.win32.WORD;
import at.o2xfs.xfs.util.XfsConstants;

public class WFSPTRRAWDATA extends Struct {

	private final WORD inputData = new WORD();
	private final ULONG size = new ULONG();
	private final Pointer data = new Pointer();

	public WFSPTRRAWDATA() {
		add(inputData);
		add(size);
		add(data);
	}

	public PTRInputData getInputData() {
		return XfsConstants.valueOf(inputData, PTRInputData.class);
	}

	public void setInputData(final PTRInputData inputData) {
		this.inputData.put(inputData.getValue());
	}

	public byte[] getData() {
		byte[] result = null;
		if (!Pointer.NULL.equals(data)) {
			result = data.buffer((int) size.longValue()).get();
		}
		return result;
	}

	public void setData(final byte[] data) {
		size.put(data.length);
		this.data.pointTo(new ByteArray(data));
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("inputData", getInputData())
				.append("data", getData()).toString();
	}
}