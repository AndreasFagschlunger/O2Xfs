package at.o2xfs.operator.ui.convert;

import at.o2xfs.operator.ui.UIMessage;
import at.o2xfs.operator.ui.UIMessage.Severity;
import at.o2xfs.operator.ui.content.text.Label;

public enum LongConverter implements Converter {

	INSTANCE;

	private static final String LONG_ID = "at.o2xfs.operator.ui.convert.LongConverter.LONG";

	@Override
	public Object getAsObject(String value) throws ConverterException {
		Long result = null;
		if (value != null && value.trim().length() > 0) {
			try {
				result = Long.valueOf(value.trim());
			} catch (NumberFormatException e) {
				throw new ConverterException(new UIMessage(Severity.ERROR, Label.valueOf(LONG_ID), value), e);
			}
		}
		return result;
	}
}