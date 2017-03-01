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

package at.o2xfs.operator.ui.validator;

import at.o2xfs.operator.ui.UIMessage;
import at.o2xfs.operator.ui.UIMessage.Severity;
import at.o2xfs.operator.ui.content.text.Label;

public final class LongRangeValidator implements Validator {

	private static final String MAXIMUM_MESSAGE_ID = "at.o2xfs.operator.ui.validator.LongRangeValidator.MAXIMUM";
	private static final String MINIMUM_MESSAGE_ID = "at.o2xfs.operator.ui.validator.LongRangeValidator.MINIMUM";
	private static final String NOT_IN_RANGE_MESSAGE_ID = "at.o2xfs.operator.ui.validator.LongRangeValidator.NOT_IN_RANGE";

	private final Long minimum;
	private final Long maximum;

	public LongRangeValidator(long minimum, long maximum) {
		this.minimum = Long.valueOf(minimum);
		this.maximum = Long.valueOf(maximum);
	}

	@Override
	public void validate(Object aValue) throws ValidatorException {
		if (aValue == null) {
			return;
		}
		long value = parseLongValue(aValue);
		if (minimum != null && maximum != null) {
			if (value < minimum || value > maximum) {
				throw new ValidatorException(new UIMessage(Severity.ERROR, Label.valueOf(NOT_IN_RANGE_MESSAGE_ID), minimum, maximum));
			}
		} else if (minimum != null) {
			if (value < minimum) {
				throw new ValidatorException(new UIMessage(Severity.ERROR, Label.valueOf(MINIMUM_MESSAGE_ID), minimum));
			}
		} else if (maximum != null) {
			if (value < maximum) {
				throw new ValidatorException(new UIMessage(Severity.ERROR, Label.valueOf(MAXIMUM_MESSAGE_ID), maximum));
			}
		}
	}

	private long parseLongValue(Object value) throws ValidatorException {
		if (value instanceof Number) {
			return ((Number) value).longValue();
		} else {
			return Long.parseLong(value.toString());
		}
	}
}