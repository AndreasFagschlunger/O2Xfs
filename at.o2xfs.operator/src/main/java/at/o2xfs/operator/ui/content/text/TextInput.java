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

package at.o2xfs.operator.ui.content.text;

import java.util.ArrayList;
import java.util.List;

import at.o2xfs.operator.ui.UIElement;

@Deprecated
public class TextInput implements UIElement {

	private List<TextChangedListener> listeners = null;

	private StringBuilder text = null;

	public TextInput() {
		listeners = new ArrayList<TextChangedListener>();
		text = new StringBuilder();
	}

	private void notifyTextChanged() {
		for (final TextChangedListener listener : listeners) {
			listener.textChanged();
		}
	}

	public void addTextChangedListener(final TextChangedListener listener) {
		listeners.add(listener);
	}

	public void removeTextChangedListener(final TextChangedListener listener) {
		listeners.remove(listener);
	}

	public String getText() {
		return text.toString();
	}

	public void setText(final String str) {
		text.setLength(0);
		text.append(str);
		notifyTextChanged();
	}
}