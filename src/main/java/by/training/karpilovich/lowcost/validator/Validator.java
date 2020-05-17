package by.training.karpilovich.lowcost.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.exception.ValidatorException;

public abstract class Validator {

	protected static final Logger LOGGER = LogManager.getLogger(Validator.class);

	protected Validator next;
	protected Pattern pattern;
	protected Matcher matcher;

	public Validator setNext(Validator validator) {
		Validator last = getLastValidator();
		last.next = validator;
		return this;
	}

	protected boolean hasNext() {
		return next != null;
	}

	protected void continueValidate() throws ValidatorException {
		if (hasNext()) {
			next.validate();
		}
	}

	private Validator getLastValidator() {
		Validator validator = this;
		while (validator.hasNext()) {
			validator = validator.next;
		}
		return validator;
	}

	public abstract void validate() throws ValidatorException;

}