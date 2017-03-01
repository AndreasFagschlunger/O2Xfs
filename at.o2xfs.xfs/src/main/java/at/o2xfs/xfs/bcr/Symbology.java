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

package at.o2xfs.xfs.bcr;

import at.o2xfs.xfs.XfsConstant;

public enum Symbology implements XfsConstant {

	/*
	 * @since v3.10
	 */
	UNKNOWN(0L),

	/*
	 * @since v3.10
	 */
	EAN128(1L),

	/*
	 * @since v3.10
	 */
	EAN8(2L),

	/*
	 * @since v3.10
	 */
	EAN8_2(3L),

	/*
	 * @since v3.10
	 */
	EAN8_5(4L),

	/*
	 * @since v3.10
	 */
	EAN13(5L),

	/*
	 * @since v3.10
	 */
	EAN13_2(6L),

	/*
	 * @since v3.10
	 */
	EAN13_5(7L),

	/*
	 * @since v3.10
	 */
	JAN13(8L),

	/*
	 * @since v3.10
	 */
	UPCA(9L),

	/*
	 * @since v3.10
	 */
	UPCE0(10L),

	/*
	 * @since v3.10
	 */
	UPCE0_2(11L),

	/*
	 * @since v3.10
	 */
	UPCE0_5(12L),

	/*
	 * @since v3.10
	 */
	UPCE1(13L),

	/*
	 * @since v3.10
	 */
	UPCE1_2(14L),

	/*
	 * @since v3.10
	 */
	UPCE1_5(15L),

	/*
	 * @since v3.10
	 */
	UPCA_2(16L),

	/*
	 * @since v3.10
	 */
	UPCA_5(17L),

	/*
	 * @since v3.10
	 */
	CODABAR(18L),

	/*
	 * @since v3.10
	 */
	ITF(19L),

	/*
	 * @since v3.10
	 */
	CODE_11(20L),

	/*
	 * @since v3.10
	 */
	CODE_39(21L),

	/*
	 * @since v3.10
	 */
	CODE_49(22L),

	/*
	 * @since v3.10
	 */
	CODE_93(23L),

	/*
	 * @since v3.10
	 */
	CODE_128(24L),

	/*
	 * @since v3.10
	 */
	MSI(25L),

	/*
	 * @since v3.10
	 */
	PLESSEY(26L),

	/*
	 * @since v3.10
	 */
	STD2OF5(27L),

	/*
	 * @since v3.10
	 */
	STD2OF5_IATA(28L),

	/*
	 * @since v3.10
	 */
	PDF_417(29L),

	/*
	 * @since v3.10
	 */
	MICROPDF_417(30L),

	/*
	 * @since v3.10
	 */
	DATAMATRIX(31L),

	/*
	 * @since v3.10
	 */
	MAXICODE(32L),

	/*
	 * @since v3.10
	 */
	CODEONE(33L),

	/*
	 * @since v3.10
	 */
	CHANNELCODE(34L),

	/*
	 * @since v3.10
	 */
	TELEPEN_ORIGINAL(35L),

	/*
	 * @since v3.10
	 */
	TELEPEN_AIM(36L),

	/*
	 * @since v3.10
	 */
	RSS(37L),

	/*
	 * @since v3.10
	 */
	RSS_EXPANDED(38L),

	/*
	 * @since v3.10
	 */
	RSS_RESTRICTED(39L),

	/*
	 * @since v3.10
	 */
	COMPOSITE_CODE_A(40L),

	/*
	 * @since v3.10
	 */
	COMPOSITE_CODE_B(41L),

	/*
	 * @since v3.10
	 */
	COMPOSITE_CODE_C(42L),

	/*
	 * @since v3.10
	 */
	POSICODE_A(43L),

	/*
	 * @since v3.10
	 */
	POSICODE_B(44L),

	/*
	 * @since v3.10
	 */
	TRIOPTIC_CODE_39(45L),

	/*
	 * @since v3.10
	 */
	CODABLOCK_F(46L),

	/*
	 * @since v3.10
	 */
	CODE_16K(47L),

	/*
	 * @since v3.10
	 */
	QRCODE(48L),

	/*
	 * @since v3.10
	 */
	AZTEC(49L),

	/*
	 * @since v3.10
	 */
	UKPOST(50L),

	/*
	 * @since v3.10
	 */
	PLANET(51L),

	/*
	 * @since v3.10
	 */
	POSTNET(52L),

	/*
	 * @since v3.10
	 */
	CANADIANPOST(53L),

	/*
	 * @since v3.10
	 */
	NETHERLANDSPOST(54L),

	/*
	 * @since v3.10
	 */
	AUSTRALIANPOST(55L),

	/*
	 * @since v3.10
	 */
	JAPANESEPOST(56L),

	/*
	 * @since v3.10
	 */
	CHINESEPOST(57L),

	/*
	 * @since v3.10
	 */
	KOREANPOST(58L);

	private final long value;

	private Symbology(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}