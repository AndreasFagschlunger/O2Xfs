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

package at.o2xfs.xfs.ttu;

import at.o2xfs.win32.LPZZSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.WORD;
import at.o2xfs.win32.WORDArray;
import at.o2xfs.xfs.util.KeyValueMap;
import at.o2xfs.xfs.util.XfsConstants;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class WFSTTUSTATUS
		extends Struct {

	private final static int WFS_TTU_LEDS_MAX = 8;

	/**
	 * Specifies the state of the text terminal unit as one of the following
	 * flags: {@link TTUDevice}
	 */
	private final WORD device = new WORD();

	/**
	 * Specifies the state of the keyboard in the text terminal unit as one of
	 * the following flags: {@link TTUKeyboard}
	 */
	private final WORD keyboard = new WORD();

	/**
	 * Specifies the state of the keyboard lock of the text terminal unit as one
	 * of the following flags: {@link TTUKeyLock}
	 */
	private final WORD keyLock = new WORD();

	/**
	 * Specifies the state of the LEDs. The maximum guidance light index is
	 * WFS_TTU_LEDS_MAX. The number of available LEDs can be retrieved with the
	 * WFS_INF_TTU_CAPABILITIES info command. All member elements in this array
	 * are specified as one of the following flags: {@link TTULED}
	 */
	private final WORDArray leds = new WORDArray(WFS_TTU_LEDS_MAX);

	/**
	 * Specifies the horizontal size of the display of the text terminal unit
	 * (the number of columns that can be displayed).
	 */
	private final WORD displaySizeX = new WORD();

	/**
	 * Specifies the vertical size of the display of the text terminal unit (the
	 * number of rows that can be displayed).
	 */
	private final WORD displaySizeY = new WORD();

	/**
	 * Specifies a list of vendor-specific, or any other extended, information.
	 * The information is returned as a series of "key=value" strings so that it
	 * is easily extensible by service providers. Each string will be
	 * null-terminated, with the final string terminating with two null
	 * characters.
	 */
	private final LPZZSTR extra = new LPZZSTR();

	private WFSTTUSTATUS() {
		add(device);
		add(keyboard);
		add(keyLock);
		add(leds);
		add(displaySizeX);
		add(displaySizeY);
		add(extra);
	}

	public WFSTTUSTATUS(final Pointer pStatus) {
		this();
		assignBuffer(pStatus);
	}

	public TTUDevice getDevice() {
		return XfsConstants.valueOf(device, TTUDevice.class);
	}

	public TTUKeyboard getKeyboard() {
		return XfsConstants.valueOf(keyboard, TTUKeyboard.class);
	}

	public TTUKeyLock getKeyLock() {
		return XfsConstants.valueOf(keyLock, TTUKeyLock.class);
	}

	public TTULED[] getLEDs() {
		TTULED[] ttuLEDs = new TTULED[WFS_TTU_LEDS_MAX];
		int i = 0;
		for (WORD led : leds) {
			ttuLEDs[i++] = XfsConstants.valueOf(led, TTULED.class);
		}
		return ttuLEDs;
	}

	public int getDisplaySizeX() {
		return displaySizeX.intValue();
	}

	public int getDisplaySizeY() {
		return displaySizeY.intValue();
	}

	public Map<String, String> getExtra() {
		return KeyValueMap.from(extra);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("device", getDevice())
										.append("keyboard", getKeyboard())
										.append("keyLock", getKeyLock())
										.append("leds", getLEDs())
										.append("displaySizeX", getDisplaySizeX())
										.append("displaySizeY", getDisplaySizeY())
										.append("extra", getExtra())
										.toString();
	}
}