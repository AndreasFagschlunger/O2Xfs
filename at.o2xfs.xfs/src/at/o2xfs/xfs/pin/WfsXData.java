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

package at.o2xfs.xfs.pin;

import at.o2xfs.win32.ByteArray;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class WfsXData
		extends Struct {

	/**
	 * @since 3.10
	 */
	private final USHORT length = new USHORT();

	/**
	 * @since 3.10
	 */
	private final Pointer data = new Pointer();

	public WfsXData() {
		add(length);
		add(data);
	}

	public WfsXData(final Pointer p) {
		this();
		assignBuffer(p);
	}

	public WfsXData(final WfsXData xData) {
		this();
		allocate();
		setData(xData.getData());
	}

	public WfsXData(final byte[] xData) {
		this();
		allocate();
		setData(xData);
	}

	/**
	 * {@link #length}
	 */
	private int getLength() {
		return length.intValue();
	}

	/**
	 * {@link #length}
	 */
	private void setLength(final int length) {
		this.length.set(length);
	}

	/**
	 * {@link #data}
	 */
	public byte[] getData() {
		byte[] result = null;
		if (!Pointer.NULL.equals(data)) {
			result = data.buffer(getLength()).get();
		}
		return result;
	}

	/**
	 * {@link #data}
	 */
	public void setData(final byte[] data) {
		setLength(data.length);
		ByteArray array = new ByteArray(data);
		this.data.pointTo(array);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("length", getLength()).append("data", getData()).toString();
	}
}