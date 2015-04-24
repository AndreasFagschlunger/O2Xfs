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

import at.o2xfs.win32.BOOL;
import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.WORD;
import at.o2xfs.xfs.XfsVersion;
import at.o2xfs.xfs.util.XfsConstants;

import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class WFSPINKEYDETAIL
		extends Struct {

	/**
	 * Specifies the name of the key.
	 */
	private final LPSTR keyName = new LPSTR();

	/**
	 * Specifies the type of access for which the key is used as a combination
	 * of the following flags: {@link PINUse}
	 */
	private final WORD use = new WORD();

	/**
	 * Specifies whether the key has been loaded (imported from Application or
	 * locally from Operator) and is either TRUE or FALSE.
	 */
	private final BOOL loaded = new BOOL();

	/**
	 * Contains the key block header of keys imported within an ANS TR-31
	 * keyblock. This data is encoded in the same format that it was imported
	 * in, and contains all mandatory and optional header fields.
	 * lpxKeyBlockHeader is NULL if the key was not imported within a key block
	 * or has not been loaded yet. The fwUse field provides a summary of the key
	 * use.
	 * 
	 * @since 3.10
	 */
	private Pointer xKeyBlockHeader = new Pointer();

	public WFSPINKEYDETAIL(final XfsVersion xfsVersion) {
		add(keyName);
		add(use);
		add(loaded);
		if (xfsVersion.isGE(XfsVersion.V3_10)) {
			add(xKeyBlockHeader);
		} else {
			xKeyBlockHeader.allocate();
		}
	}

	/**
	 * @param pKeyDetail
	 *            pointer to key detail structure.
	 */
	public WFSPINKEYDETAIL(final XfsVersion xfsVersion, final Pointer pKeyDetail) {
		this(xfsVersion);
		assignBuffer(pKeyDetail);
	}

	/**
	 * {@link #keyName}
	 */
	public String getKeyName() {
		return keyName.toString();
	}

	/**
	 * {@link #use}
	 */
	public Set<PINUse> getUse() {
		return XfsConstants.of(use, PINUse.class);
	}

	/**
	 * {@link #loaded}
	 */
	public boolean isLoaded() {
		return loaded.booleanValue();
	}

	/**
	 * {@link #xKeyBlockHeader}
	 */
	public WfsXData getXKeyBlockHeader() {
		if (Pointer.NULL.equals(xKeyBlockHeader)) {
			return null;
		}
		return new WfsXData(xKeyBlockHeader);
	}

	/**
	 * {@link #xKeyBlockHeader}
	 */
	public void setXKeyBlockHeader(final WfsXData xData) {
		this.xKeyBlockHeader.pointTo(xData);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("keyName", getKeyName())
										.append("use", getUse())
										.append("loaded", isLoaded())
										.append("xKeyBlockHeader", getXKeyBlockHeader())
										.toString();
	}

}