package at.o2xfs.operator.ui.convert;

public interface Converter {

	Object getAsObject(String value) throws ConverterException;
}