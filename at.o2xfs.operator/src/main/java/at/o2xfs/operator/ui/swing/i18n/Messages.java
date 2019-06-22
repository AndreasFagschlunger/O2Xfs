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

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.operator.ui.content.text.ExceptionMessage;
import at.o2xfs.operator.ui.content.text.Label;
import at.o2xfs.xfs.XfsException;

public class Messages {

	private final static Logger LOG = LoggerFactory.getLogger(Messages.class);

	private final static ResourceBundle bundle = ResourceBundle.getBundle("Messages");

	private Messages() {
		return;
	}

	public final static String getText(final String label) {
		try {
			return bundle.getString(label);
		} catch (final MissingResourceException e) {
			if (LOG.isWarnEnabled()) {
				final String method = "getText(String)";
				LOG.warn(method, "Undefined label: " + label);
			}
		}
		return "@" + label;
	}

	public static final String getText(final Enum<?> e) {
		final String className = e.getClass().getSimpleName();
		String key = className + "." + e.name();
		if (bundle.containsKey(key)) {
			return getText(key);
		}
		return getText(e.name());
	}

	public final static String getText(final Label label) {
		final Iterator<String> iterator = new LabelIterator(label);
		String pattern = null;
		String key = null;
		do {
			key = iterator.next();
			if (bundle.containsKey(key)) {
				pattern = bundle.getString(key);
				break;
			}
		} while (iterator.hasNext());
		if (pattern == null) {
			pattern = getText(StringUtils.join(label.getLabel(), '.'));
		}
		return MessageFormat.format(pattern, label.getArguments());
	}

	public static final String getMessage(final ExceptionMessage error) {
		final Throwable cause = error.getCause();
		Label label = new Label(error.getTaskClass(), cause.getClass().getName());
		if (cause instanceof XfsException) {
			final XfsException e = (XfsException) cause;
			label = new Label(error.getTaskClass(), e.getError().name());
			final Iterator<String> iterator = new LabelIterator(label);
			while (iterator.hasNext()) {
				final String key = iterator.next();
				if (bundle.containsKey(key)) {
					return bundle.getString(key);
				}
			}
		}
		return getText(label);
	}

	public final static void main(final String[] args) {
		Messages.getText("");
	}
}