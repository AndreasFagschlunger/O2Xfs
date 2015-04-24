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
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.ULONG;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.util.XfsConstants;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class WFSPINFDK
		extends Struct {

	/**
	 * Specifies the code returned by this FDK, defined as one of the following
	 * values: {@link PINFDK}
	 */
	private final ULONG fdk = new ULONG();

	/**
	 * For FDKs, specifies the FDK position relative to the Left Hand side of
	 * the screen expressed as a percentage of the width of the screen.
	 */
	private final USHORT xPosition = new USHORT();

	/**
	 * For FDKs, specifies the FDK position relative to the top of the screen
	 * expressed as a percentage of the height of the screen.
	 */
	private final USHORT yPosition = new USHORT();

	public WFSPINFDK() {
		add(fdk);
		add(xPosition);
		add(yPosition);
	}

	public WFSPINFDK(final Pointer pFDK) {
		this();
		assignBuffer(pFDK);
	}

	/**
	 * Copy constructor.
	 */
	public WFSPINFDK(final WFSPINFDK pinFDK) {
		this();
		allocate();
		fdk.set(pinFDK.fdk);
		xPosition.set(pinFDK.xPosition);
		yPosition.set(pinFDK.yPosition);
	}

	/**
	 * @see #xPosition
	 */
	public int getXPosition() {
		return xPosition.intValue();
	}

	/**
	 * @see #xPosition
	 */
	public void setXPosition(final int xPosition) {
		this.xPosition.set(xPosition);
	}

	/**
	 * @see #yPosition
	 */
	public int getYPosition() {
		return yPosition.intValue();
	}

	/**
	 * @see #yPosition
	 */
	public void setYPosition(final int yPosition) {
		this.yPosition.set(yPosition);
	}

	public PINFDK getFDK() {
		return XfsConstants.valueOf(fdk, PINFDK.class);
	}

	public void setFDK(final PINFDK fdk) {
		this.fdk.set(fdk.getValue());
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("fdk", getFDK())
										.append("xPosition", getXPosition())
										.append("yPosition", getYPosition())
										.toString();
	}
}