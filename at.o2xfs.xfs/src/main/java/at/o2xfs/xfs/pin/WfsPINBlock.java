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

import at.o2xfs.win32.BYTE;
import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.Struct;
import at.o2xfs.xfs.XfsWord;
import at.o2xfs.xfs.util.XfsConstants;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class WfsPINBlock
		extends Struct {

	private LPSTR customerData = new LPSTR();
	private LPSTR xorData = new LPSTR();
	private BYTE padding = new BYTE();
	private XfsWord<PINFormat> format = new XfsWord<>(PINFormat.class);
	private LPSTR key = new LPSTR();
	private LPSTR keyEncKey = new LPSTR();

	public WfsPINBlock() {
		add(customerData);
		add(xorData);
		add(padding);
		add(format);
		add(key);
		add(keyEncKey);
	}

	public String getCustomerData() {
		return customerData.toString();
	}

	public void setCustomerData(String customerData) {
		this.customerData.put(customerData);
	}

	public LPSTR getXORData() {
		return xorData;
	}

	public void setXORData(String xorData) {
		this.xorData.put(xorData);
	}

	public byte getPadding() {
		return padding.byteValue();
	}

	public void setPadding(byte padding) {
		this.padding.set(padding);
	}

	public PINFormat getFormat() {
		return XfsConstants.valueOf(format, PINFormat.class);
	}

	public void setFormat(PINFormat format) {
		this.format.set(format);
	}

	public String getKey() {
		return key.toString();
	}

	public void setKey(String key) {
		this.key.put(key);
	}

	public String getKeyEncKey() {
		return keyEncKey.toString();
	}

	public void setKeyEncKey(String keyEncKey) {
		this.keyEncKey.put(keyEncKey);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("customerData", getCustomerData())
										.append("xorData", getXORData())
										.append("padding", getPadding())
										.append("format", getFormat())
										.append("key", getKey())
										.append("keyEncKey", getKeyEncKey())
										.toString();
	}
}