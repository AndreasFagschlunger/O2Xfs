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

package at.o2xfs.xfs.pin;

import at.o2xfs.win32.BYTE;
import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.XfsStruct;
import at.o2xfs.xfs.win32.XfsWord;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class WfsPinCrypt
		extends XfsStruct {

	private final XfsWord<PinMode> mode = new XfsWord<PinMode>(PinMode.class);
	private final LPSTR key = new LPSTR();
	private final Pointer keyEncKey = new Pointer();
	private final XfsWord<PINAlgorithm> algorithm = new XfsWord<PINAlgorithm>(PINAlgorithm.class);
	private final LPSTR startValueKey = new LPSTR();
	private final Pointer startValue = new Pointer();
	private final BYTE padding = new BYTE();
	private final BYTE compression = new BYTE();
	private final Pointer cryptData = new Pointer();

	public WfsPinCrypt() {
		add(mode).add(key)
					.add(keyEncKey)
					.add(algorithm)
					.add(startValueKey)
					.add(startValue)
					.add(padding)
					.add(compression)
					.add(cryptData);
	}

	public PinMode getMode() {
		return mode.get();
	}

	public void setMode(PinMode mode) {
		this.mode.set(mode);
	}

	public String getKey() {
		return key.toString();
	}

	public void setKey(String key) {
		this.key.put(key);
	}

	public WfsXData getKeyEncKey() {
		WfsXData result = null;
		if (!Pointer.NULL.equals(keyEncKey)) {
			result = new WfsXData(keyEncKey);
		}
		return result;
	}

	public XfsWord<PINAlgorithm> getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(PINAlgorithm algorithm) {
		this.algorithm.set(algorithm);
	}

	public String getStartValueKey() {
		return startValueKey.toString();
	}

	public byte[] getStartValue() {
		byte[] result = null;
		if (!Pointer.NULL.equals(startValue)) {
			result = new WfsXData(startValue).getData();
		}
		return result;
	}

	public void setStartValue(byte[] startValue) {
		this.startValue.pointTo(new WfsXData(startValue));
	}

	public byte getPadding() {
		return padding.byteValue();
	}

	public void setPadding(byte padding) {
		this.padding.set(padding);
	}

	public byte getCompression() {
		return compression.byteValue();
	}

	public void setCompression(byte compression) {
		this.compression.set(compression);
	}

	public byte[] getCryptData() {
		byte[] result = null;
		if (!Pointer.NULL.equals(cryptData)) {
			result = new WfsXData(cryptData).getData();
		}
		return result;
	}

	public void setCryptData(byte[] cryptData) {
		this.cryptData.pointTo(new WfsXData(cryptData));
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("mode", getMode())
										.append("key", getKey())
										.append("keyEncKey", getKeyEncKey())
										.append("algorithm", getAlgorithm())
										.append("startValueKey", getStartValueKey())
										.append("startValue", getStartValue())
										.append("padding", getPadding())
										.append("compression", getCompression())
										.append("cryptData", getCryptData())
										.toString();
	}
}