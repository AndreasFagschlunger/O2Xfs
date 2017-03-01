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

package at.o2xfs.operator.ui.swing.i18n;

import java.util.Iterator;
import java.util.NoSuchElementException;

import at.o2xfs.operator.ui.content.text.Label;

public class LabelIterator implements Iterator<String> {

	private int iMax = 0;

	private String[] keys = null;

	public LabelIterator(final Label label) {
		keys = label.getLabel();
		iMax = keys.length - 2;
	}

	@Override
	public boolean hasNext() {
		return iMax >= -1;
	}

	@Override
	public String next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		final StringBuilder label = new StringBuilder();
		for (int i = 0; i <= iMax; i++) {
			label.append(keys[i]);
			label.append('.');
			if (i == iMax) {
				break;
			}
		}
		iMax--;
		label.append(keys[keys.length - 1]);
		return label.toString();
	}

	/**
	 * @exception UnsupportedOperationException
	 *                the remove operation is not supported by this Iterator.
	 */
	@Override
	public void remove() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

}