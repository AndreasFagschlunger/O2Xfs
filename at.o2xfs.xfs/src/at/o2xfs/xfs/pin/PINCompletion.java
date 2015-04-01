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

import at.o2xfs.xfs.XfsConstant;

public enum PINCompletion implements XfsConstant {

	/**
	 * The command terminated automatically, because maximum PIN length was
	 * reached.
	 */
	AUTO(0L),

	/**
	 * The ENTER Function Key was pressed.
	 */
	ENTER(1L),

	/**
	 * The CANCEL Function Key was pressed.
	 */
	CANCEL(2L),

	/**
	 * Input continues (this value is only used in the execute event {@link PINMessage#WFS_EXEE_PIN_KEY}).
	 */
	CONTINUE(6L),

	/**
	 * The CLEAR Function Key was pressed and the previous input is cleared
	 * (this value is only used in the execute event {@link PINMessage#WFS_EXEE_PIN_KEY}).
	 */
	CLEAR(7L),

	/**
	 * The last input digit was cleared (this value is only used in the execute
	 * event {@link PINMessage#WFS_EXEE_PIN_KEY}).
	 */
	BACKSPACE(8L),

	/**
	 * An FDK was pressed.
	 */
	FDK(9L),

	/**
	 * The HELP Function Key was pressed.
	 */
	HELP(10L),

	/**
	 * A Function Key (FK) other than ENTER, CLEAR, CANCEL, BACKSPACE, HELP was
	 * pressed.
	 */
	FK(11L),

	/**
	 * Input continues, FDK was pressed (this value is only used in the execute
	 * event {@link PINMessage#WFS_EXEE_PIN_KEY}).
	 * 
	 * @since 3.00
	 */
	CONTFDK(12L);

	private final long value;

	private PINCompletion(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}