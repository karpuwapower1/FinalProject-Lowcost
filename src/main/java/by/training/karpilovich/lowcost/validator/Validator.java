package by.training.karpilovich.lowcost.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.training.karpilovich.lowcost.exception.ValidatorException;

public abstract class Validator {

	protected Validator next;
	protected Pattern pattern;
	protected Matcher matcher;

	public void setNext(Validator validator) {
		next = validator;
	}

	public boolean hasNext() {
		return next != null;
	}

	public abstract void validate() throws ValidatorException;

}
