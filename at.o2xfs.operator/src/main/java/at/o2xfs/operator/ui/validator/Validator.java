package at.o2xfs.operator.ui.validator;

public interface Validator {

	public void validate(Object aValue) throws ValidatorException;
}