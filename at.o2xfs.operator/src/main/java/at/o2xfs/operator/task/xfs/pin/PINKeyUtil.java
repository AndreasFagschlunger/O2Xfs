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

package at.o2xfs.operator.task.xfs.pin;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.operator.ui.input.VirtualKey;
import at.o2xfs.xfs.XfsVersion;
import at.o2xfs.xfs.pin.PINFDK;
import at.o2xfs.xfs.pin.PINFK;
import at.o2xfs.xfs.pin.WfsPINKey;
import at.o2xfs.xfs.util.XfsConstants;

public final class PINKeyUtil {

	private final static Logger LOG = LoggerFactory.getLogger(PINKeyUtil.class);

	private PINKeyUtil() {
		throw new AssertionError();
	}

	public final static VirtualKey getVirtualKey(final WfsPINKey pinKey,
			final XfsVersion xfsVersion) {
		final String method = "getVirtualKey(WFSPINKEY, XfsVersion)";
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "pinKey=" + pinKey + ",xfsVersion=" + xfsVersion);
		}
		switch (pinKey.getCompletion()) {
			case BACKSPACE:
				return VirtualKey.VK_BACK;
			case CANCEL:
				return VirtualKey.VK_CANCEL;
			case CLEAR:
				return VirtualKey.VK_CLEAR;
			case ENTER:
				return VirtualKey.VK_RETURN;
			case HELP:
				return VirtualKey.VK_HELP;
			case CONTFDK:
			case FDK:
				if (xfsVersion.isGE(XfsVersion.V3_00)) {
					final PINFDK fdk = XfsConstants.valueOf(pinKey.getDigit(),
							PINFDK.class);
					return getVirtualKey(fdk);
				}
				return VirtualKey.getForKeyCode((int) pinKey.getDigit());
			case FK:
			case CONTINUE:
				if (xfsVersion.isGE(XfsVersion.V3_00)) {
					final PINFK fk = XfsConstants.valueOf(pinKey.getDigit(),
							PINFK.class);
					return getVirtualKey(fk);
				}
				return VirtualKey.getForChar((char) pinKey.getDigit());
			case AUTO:
				if (xfsVersion.isLT(XfsVersion.V3_00)) {
					return VirtualKey.getForChar((char) pinKey.getDigit());
				}
			default:
				if (LOG.isErrorEnabled()) {
					LOG.error(method, "Could not map WFSPINKEY: " + pinKey);
				}
				return null;
		}
	}

	public final static VirtualKey getVirtualKey(final PINFDK fdk) {
		switch (fdk) {
			case WFS_PIN_FK_FDK01:
				return VirtualKey.VK_F1;
			case WFS_PIN_FK_FDK02:
				return VirtualKey.VK_F2;
			case WFS_PIN_FK_FDK03:
				return VirtualKey.VK_F3;
			case WFS_PIN_FK_FDK04:
				return VirtualKey.VK_F4;
			case WFS_PIN_FK_FDK05:
				return VirtualKey.VK_F5;
			case WFS_PIN_FK_FDK06:
				return VirtualKey.VK_F6;
			case WFS_PIN_FK_FDK07:
				return VirtualKey.VK_F7;
			case WFS_PIN_FK_FDK08:
				return VirtualKey.VK_F8;
			case WFS_PIN_FK_FDK09:
				return VirtualKey.VK_F9;
			case WFS_PIN_FK_FDK10:
				return VirtualKey.VK_F10;
			case WFS_PIN_FK_FDK11:
				return VirtualKey.VK_F11;
			case WFS_PIN_FK_FDK12:
				return VirtualKey.VK_F12;
			case WFS_PIN_FK_FDK13:
				return VirtualKey.VK_F13;
			case WFS_PIN_FK_FDK14:
				return VirtualKey.VK_F14;
			case WFS_PIN_FK_FDK15:
				return VirtualKey.VK_F15;
			case WFS_PIN_FK_FDK16:
				return VirtualKey.VK_F16;
			case WFS_PIN_FK_FDK17:
				return VirtualKey.VK_F17;
			case WFS_PIN_FK_FDK18:
				return VirtualKey.VK_F18;
			case WFS_PIN_FK_FDK19:
				return VirtualKey.VK_F19;
			case WFS_PIN_FK_FDK20:
				return VirtualKey.VK_F20;
			case WFS_PIN_FK_FDK21:
				return VirtualKey.VK_F21;
			case WFS_PIN_FK_FDK22:
				return VirtualKey.VK_F22;
			case WFS_PIN_FK_FDK23:
				return VirtualKey.VK_F23;
			case WFS_PIN_FK_FDK24:
				return VirtualKey.VK_F24;
			default:
				if (LOG.isErrorEnabled()) {
					final String method = "getVirtualKey(PINFDK)";
					LOG.error(method, "Could not map PINFDK: " + fdk);
				}
				return null;
		}
	}

	public final static VirtualKey getVirtualKey(final PINFK fk) {
		switch (fk) {
			case FK_0:
				return VirtualKey.VK_0;
			case FK_1:
				return VirtualKey.VK_1;
			case FK_2:
				return VirtualKey.VK_2;
			case FK_3:
				return VirtualKey.VK_3;
			case FK_4:
				return VirtualKey.VK_4;
			case FK_5:
				return VirtualKey.VK_5;
			case FK_6:
				return VirtualKey.VK_6;
			case FK_7:
				return VirtualKey.VK_7;
			case FK_8:
				return VirtualKey.VK_8;
			case FK_9:
				return VirtualKey.VK_9;
			case FK_BACKSPACE:
				return VirtualKey.VK_BACK;
			case FK_CANCEL:
				return VirtualKey.VK_CANCEL;
			case FK_CLEAR:
				return VirtualKey.VK_CLEAR;
			case FK_DECPOINT:
				return VirtualKey.VK_DECIMAL;
			case FK_ENTER:
				return VirtualKey.VK_RETURN;
			case FK_HELP:
				return VirtualKey.VK_HELP;
			default:
				if (LOG.isErrorEnabled()) {
					final String method = "getVirtualKey(PINFK)";
					LOG.error(method, "Could not map PINFK: " + fk);
				}
		}
		return null;
	}
}