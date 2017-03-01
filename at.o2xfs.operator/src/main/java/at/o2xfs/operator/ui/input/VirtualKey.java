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

package at.o2xfs.operator.ui.input;

public enum VirtualKey {

	/**
	 * Control-break processing
	 */
	VK_CANCEL(0x03),

	/**
	 * BACKSPACE key
	 */
	VK_BACK(0x08),
	VK_TAB(0x09),

	/**
	 * CLEAR key
	 */
	VK_CLEAR(0x0C),

	/**
	 * ENTER key
	 */
	VK_RETURN(0x0D),
	VK_SHIFT(0x10),
	VK_CONTROL(0x11),
	VK_MENU(0x12),
	VK_PAUSE(0x13),
	VK_CAPITAL(0x14),
	VK_ESCAPE(0x1B),
	VK_SPACE(0x20),
	VK_PRIOR(0x21),
	VK_NEXT(0x22),
	VK_END(0x23),
	VK_HOME(0x24),
	VK_LEFT(0x25),
	VK_UP(0x26),
	VK_RIGHT(0x27),
	VK_DOWN(0x28),
	VK_SELECT(0x29),
	VK_PRINT(0x2A),
	VK_EXECUTE(0x2B),
	VK_SNAPSHOT(0x2C),
	VK_INSERT(0x2D),
	VK_DELETE(0x2E),

	/**
	 * HELP key
	 */
	VK_HELP(0x2F),
	VK_0(0x30),
	VK_1(0x31),
	VK_2(0x32),
	VK_3(0x33),
	VK_4(0x34),
	VK_5(0x35),
	VK_6(0x36),
	VK_7(0x37),
	VK_8(0x38),
	VK_9(0x39),
	VK_A(0x41),
	VK_B(0x42),
	VK_C(0x43),
	VK_D(0x44),
	VK_E(0x45),
	VK_F(0x46),
	VK_G(0x47),
	VK_H(0x48),
	VK_I(0x49),
	VK_J(0x4A),
	VK_K(0x4B),
	VK_L(0x4C),
	VK_M(0x4D),
	VK_N(0x4E),
	VK_O(0x4F),
	VK_P(0x50),
	VK_Q(0x51),
	VK_R(0x52),
	VK_S(0x53),
	VK_T(0x54),
	VK_U(0x55),
	VK_V(0x56),
	VK_W(0x57),
	VK_X(0x58),
	VK_Y(0x59),
	VK_Z(0x5A),

	VK_NUMPAD0(0x60),
	VK_NUMPAD1(0x61),
	VK_NUMPAD2(0x62),
	VK_NUMPAD3(0x63),
	VK_NUMPAD4(0x64),
	VK_NUMPAD5(0x65),
	VK_NUMPAD6(0x66),
	VK_NUMPAD7(0x67),
	VK_NUMPAD8(0x68),
	VK_NUMPAD9(0x69),
	VK_MULTIPLY(0x6A),
	VK_ADD(0x6B),
	VK_SEPARATOR(0x6C),
	VK_SUBTRACT(0x6D),

	/**
	 * Decimal key
	 */
	VK_DECIMAL(0x6E, '.'),
	VK_DIVIDE(0x6F),

	/**
	 * F1 key
	 */
	VK_F1(0x70),

	/**
	 * F2 key
	 */
	VK_F2(0x71),

	/**
	 * F3 key
	 */
	VK_F3(0x72),

	/**
	 * F4 key
	 */
	VK_F4(0x73),

	/**
	 * F5 key
	 */
	VK_F5(0x74),

	/**
	 * F6 key
	 */
	VK_F6(0x75),

	/**
	 * F7 key
	 */
	VK_F7(0x76),

	/**
	 * F8 key
	 */
	VK_F8(0x77),

	/**
	 * F9 key
	 */
	VK_F9(0x78),

	/**
	 * F10 key
	 */
	VK_F10(0x79),

	/**
	 * F11 key
	 */
	VK_F11(0x7A),

	/**
	 * F12 key
	 */
	VK_F12(0x7B),

	/**
	 * F13 key
	 */
	VK_F13(0x7C),

	/**
	 * F14 key
	 */
	VK_F14(0x7D),

	/**
	 * F15 key
	 */
	VK_F15(0x7E),

	/**
	 * F16 key
	 */
	VK_F16(0x7F),

	/**
	 * F17 key
	 */
	VK_F17(0x80),

	/**
	 * F18 key
	 */
	VK_F18(0x81),

	/**
	 * F19 key
	 */
	VK_F19(0x82),

	/**
	 * F20 key
	 */
	VK_F20(0x83),

	/**
	 * F21 key
	 */
	VK_F21(0x84),

	/**
	 * F22 key
	 */
	VK_F22(0x85),

	/**
	 * F23 key
	 */
	VK_F23(0x86),

	/**
	 * F24 key
	 */
	VK_F24(0x87);

	private int keyCode = 0;

	private char c = 0;

	private VirtualKey(final int keyCode) {
		this.keyCode = keyCode;
	}

	private VirtualKey(final int keyCode, final char c) {
		this(keyCode);
		this.c = c;
	}

	public int getKeyCode() {
		return keyCode;
	}

	public final static VirtualKey getForKeyCode(final int keyCode) {
		for (VirtualKey key : values()) {
			if (key.getKeyCode() == keyCode) {
				return key;
			}
		}
		return null;
	}

	public final static VirtualKey getForChar(final char c) {
		for (final VirtualKey key : values()) {
			if (key.c == c) {
				return key;
			}
		}
		return getForKeyCode(c);
	}

}