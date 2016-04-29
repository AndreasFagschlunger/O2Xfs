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