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

import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.PointerArray;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;
import at.o2xfs.win32.WORD;
import at.o2xfs.xfs.XfsVersion;
import at.o2xfs.xfs.util.XfsConstants;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class WfsPINData
		extends Struct {

	/**
	 * Pointer to the data entered by the user. This pointer is set to NULL if
	 * usMaxLen is set to 0.
	 */
	private LPSTR data = new LPSTR();

	/**
	 * Number of keys entered by the user (i.e. number of following WFSPINKEY
	 * structures).
	 */
	private USHORT keys = new USHORT();

	/**
	 * Pointer to an array of pointers to WFSPINKEY structures that contain the
	 * keys entered by the user (for a description of the WFSPINKEY structure
	 * see the definition of the WFS_EXEE_PIN_KEY event).
	 */
	private Pointer pinKeys = new Pointer();

	/**
	 * Specifies the reason for completion of the entry. Possible values are: {@link PINCompletion}
	 */
	private WORD completion = new WORD();

	public WfsPINData(final XfsVersion xfsVersion) {
		if (xfsVersion.isLT(XfsVersion.V3_00)) {
			add(data);
			keys.allocate();
			pinKeys.allocate();
		} else {
			data.allocate();
			add(keys);
			add(pinKeys);
		}
		add(completion);
	}

	public WfsPINData(final XfsVersion xfsVersion, final Pointer p) {
		this(xfsVersion);
		assignBuffer(p);
	}

	public WfsPINData(XfsVersion xfsVersion, final WfsPINData pinData) {
		this(xfsVersion);
		data.put(pinData.getData());
		setPINKeys(pinData.getPINKeys());
		setCompletion(pinData.getCompletion());
	}

	public String getData() {
		return data.toString();
	}

	public WfsPINKey[] getPINKeys() {
		final int numberOfKeys = keys.intValue();
		final WfsPINKey[] pinKeyArray = new WfsPINKey[numberOfKeys];
		if (numberOfKeys > 0) {
			final PointerArray pointers = new PointerArray(pinKeys, numberOfKeys);
			for (int i = 0; i < pinKeyArray.length; i++) {
				final Pointer p = pointers.get(i);
				pinKeyArray[i] = new WfsPINKey(p);
			}
		}
		return pinKeyArray;
	}

	public void setPINKeys(final WfsPINKey[] pinKeys) {
		final int numberOfKeys = (pinKeys == null ? 0 : pinKeys.length);
		keys.set(numberOfKeys);
		final PointerArray pointers = new PointerArray(numberOfKeys);
		pointers.allocate();
		for (int i = 0; i < numberOfKeys; i++) {
			final Pointer p = pointers.get(i);
			p.pointTo(pinKeys[i]);
		}
		this.pinKeys.pointTo(pointers);
	}

	public PINCompletion getCompletion() {
		return XfsConstants.valueOf(completion, PINCompletion.class);
	}

	public void setCompletion(PINCompletion completion) {
		this.completion.set((int) completion.getValue());
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("data", getData())
										.append("keys", keys)
										.append("pinKeys", getPINKeys())
										.append("completion", getCompletion())
										.toString();
	}
}