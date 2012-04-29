/*
 * Copyright (c) 2012, Andreas Fagschlunger. All rights reserved.
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
package at.o2xfs.xfs.idc;

import at.o2xfs.xfs.XfsConstant;

public enum IDCGuidance implements XfsConstant {

	/**
	 * There is no guidance light control available at this position.
	 * 
	 * @since 3.10
	 */
	WFS_IDC_GUIDANCE_NOT_AVAILABLE(0x00000000L),

	/**
	 * The light can be off.
	 * 
	 * @since 3.10
	 */
	WFS_IDC_GUIDANCE_OFF(0x00000001L),

	/**
	 * @since 3.10
	 */
	WFS_IDC_GUIDANCE_ON(0x00000002L),

	/**
	 * The light can blink slowly.
	 * 
	 * @since 3.10
	 */
	WFS_IDC_GUIDANCE_SLOW_FLASH(0x00000004L),

	/**
	 * The light can blink medium frequency.
	 * 
	 * @since 3.10
	 */
	WFS_IDC_GUIDANCE_MEDIUM_FLASH(0x00000008L),

	/**
	 * The light can blink quickly.
	 * 
	 * @since 3.10
	 */
	WFS_IDC_GUIDANCE_QUICK_FLASH(0x00000010L),

	/**
	 * The light can be continuous (steady).
	 * 
	 * @since 3.10
	 */
	WFS_IDC_GUIDANCE_CONTINUOUS(0x00000080L),

	/**
	 * The light can be red.
	 * 
	 * @since 3.10
	 */
	WFS_IDC_GUIDANCE_RED(0x00000100L),

	/**
	 * The light can be green.
	 * 
	 * @since 3.10
	 */
	WFS_IDC_GUIDANCE_GREEN(0x00000200L),

	/**
	 * The light can be yellow.
	 * 
	 * @since 3.10
	 */
	WFS_IDC_GUIDANCE_YELLOW(0x00000400L),

	/**
	 * The light can be blue.
	 * 
	 * @since 3.10
	 */
	WFS_IDC_GUIDANCE_BLUE(0x00000800L),

	/**
	 * The light can be cyan.
	 * 
	 * @since 3.10
	 */
	WFS_IDC_GUIDANCE_CYAN(0x00001000L),

	/**
	 * The light can be magenta.
	 * 
	 * @since 3.10
	 */
	WFS_IDC_GUIDANCE_MAGENTA(0x00002000L),

	/**
	 * The light can be white.
	 * 
	 * @since 3.10
	 */
	WFS_IDC_GUIDANCE_WHITE(0x00004000L);

	private final long value;

	private IDCGuidance(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}
