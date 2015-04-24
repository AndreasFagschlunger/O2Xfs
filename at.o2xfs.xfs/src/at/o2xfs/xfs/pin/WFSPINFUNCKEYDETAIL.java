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

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.PointerArray;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.ULONG;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.util.Bitmask;
import at.o2xfs.xfs.util.XfsConstants;

import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class WFSPINFUNCKEYDETAIL
		extends Struct {

	/**
	 * Specifies the function keys available for this physical device as a
	 * combination of the following flags: {@link PINFK}
	 */
	private final ULONG funcMask = new ULONG();

	/**
	 * This value indicates the number of FDK structures returned. This number
	 * can be less than the number of keys requested, if any keys are not
	 * supported.
	 */
	private final USHORT numberFDKs = new USHORT();

	/**
	 * Pointer to an array of pointers to FDK structures.
	 */
	private final Pointer fdks = new Pointer();

	public WFSPINFUNCKEYDETAIL() {
		add(funcMask);
		add(numberFDKs);
		add(fdks);
	}

	/**
	 * @param p
	 *            Pointer to WFSPINFUNCKEYDETAIL
	 */
	public WFSPINFUNCKEYDETAIL(final Pointer p) {
		this();
		assignBuffer(p);
	}

	/**
	 * Copy constructor.
	 */
	public WFSPINFUNCKEYDETAIL(final WFSPINFUNCKEYDETAIL funcKeyDetail) {
		this();
		allocate();
		funcMask.set(funcKeyDetail.funcMask);
		final WFSPINFDK[] fdks = funcKeyDetail.getFDKs();
		for (int i = 0; i < fdks.length; i++) {
			fdks[i] = new WFSPINFDK(fdks[i]);
		}
		setFDKs(fdks);
	}

	public Set<PINFK> getFunctionKeys() {
		return XfsConstants.of(funcMask, PINFK.class);
	}

	public void setFunctionKeys(final Set<PINFK> funcMask) {
		this.funcMask.set(Bitmask.of(funcMask));
	}

	private int getNumberFDKs() {
		return numberFDKs.intValue();
	}

	private void setNumberFDKs(final int numberFDKs) {
		this.numberFDKs.set(numberFDKs);
	}

	public void setFDKs(final WFSPINFDK[] fdks) {
		setNumberFDKs(fdks.length);
		final PointerArray pFDKs = new PointerArray(fdks.length);
		pFDKs.allocate();
		for (int i = 0; i < fdks.length; i++) {
			pFDKs.get(i).pointTo(fdks[i]);
		}
		this.fdks.pointTo(pFDKs);
	}

	public WFSPINFDK[] getFDKs() {
		final WFSPINFDK[] fdkArray = new WFSPINFDK[getNumberFDKs()];
		if (fdkArray.length > 0) {
			PointerArray pFDKs = new PointerArray(fdks, fdkArray.length);
			int i = 0;
			for (Pointer pFDK : pFDKs) {
				fdkArray[i] = new WFSPINFDK(pFDK);
				i++;
			}
		}
		return fdkArray;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("funcMask", getFunctionKeys())
										.append("numberFDKs", numberFDKs)
										.append("fdks", getFDKs())
										.toString();
	}

}