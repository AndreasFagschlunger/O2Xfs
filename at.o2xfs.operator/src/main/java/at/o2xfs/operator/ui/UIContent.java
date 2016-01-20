/*
 * Copyright (c) 2014, Andreas Fagschlunger. All rights reserved.
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

package at.o2xfs.operator.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.operator.ui.content.text.Label;
import at.o2xfs.operator.ui.content.text.Text;

public class UIContent {

	private final List<UIContentListener> listeners;

	private final List<UIElement> uiElements;

	private Text title;

	public UIContent(Label label) {
		title = new Text.Builder(label).build();
		listeners = new ArrayList<UIContentListener>();
		uiElements = new ArrayList<UIElement>();
	}

	public void addUIContentListener(UIContentListener l) {
		listeners.add(l);
	}

	public void removeUIContentListener(UIContentListener l) {
		listeners.remove(l);
	}

	public List<UIElement> getUIElements() {
		return Collections.unmodifiableList(uiElements);
	}

	public void addUIElement(UIElement uiElement) {
		uiElements.add(uiElement);
	}

	public Text getTitle() {
		return title;
	}

	public void setTitle(Text title) {
		this.title = title;
		notifyContentChanged();
	}

	public void setUIElement(UIElement uiElement) {
		uiElements.clear();
		addUIElement(uiElement);
		notifyContentChanged();
	}

	private void notifyContentChanged() {
		for (UIContentListener l : listeners) {
			l.contentChanged();
		}
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("title", title)
				.append("uiElements", uiElements).toString();
	}
}