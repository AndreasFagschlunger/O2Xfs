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

package at.o2xfs.xfs.util;

import at.o2xfs.common.Assert;
import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.win32.NumberType;
import at.o2xfs.xfs.XfsConstant;

import java.util.EnumSet;
import java.util.Set;

public class XfsConstants {

	private static final Logger LOG = LoggerFactory.getLogger(XfsConstants.class);

	public static final <E extends Enum<E> & XfsConstant> E valueOf(final NumberType value,
																	final Class<E> xfsConstantType) {
		if (value == null) {
			throw new IllegalArgumentException("value must not be null");
		}
		return XfsConstants.valueOf(value.longValue(), xfsConstantType);
	}

	public static final <E extends Enum<E> & XfsConstant> E valueOf(final long value, final Class<E> xfsConstantType) {
		if (xfsConstantType == null) {
			throw new IllegalArgumentException("xfsConstantType must not be null");
		}
		for (final E e : xfsConstantType.getEnumConstants()) {
			if (e.getValue() == value) {
				return e;
			}
		}
		if (LOG.isDebugEnabled()) {
			final String method = "valueOf(long, Class<E>)";
			LOG.debug(method, "Undefined " + xfsConstantType.getName() + " constant: " + value);
		}
		return null;
	}

	@Deprecated
	public static final <E extends Enum<E> & XfsConstant> Set<E> of(final NumberType value,
																	final Class<E> xfsConstantType) {
		if (value == null) {
			throw new IllegalArgumentException("value must not be null");
		}
		return of(value.longValue(), xfsConstantType);
	}

	@Deprecated
	public static final <E extends Enum<E> & XfsConstant> Set<E> of(final long value, final Class<E> type) {
		Assert.notNull(type);
		final EnumSet<E> enumSet = EnumSet.noneOf(type);
		for (final E e : type.getEnumConstants()) {
			final long eValue = e.getValue();
			if (eValue == 0L) {
				continue;
			}
			if ((eValue & value) == eValue) {
				enumSet.add(e);
			}
		}
		return enumSet;
	}
}