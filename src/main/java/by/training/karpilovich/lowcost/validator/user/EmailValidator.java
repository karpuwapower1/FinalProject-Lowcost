package by.training.karpilovich.lowcost.validator.user;

import java.util.regex.Pattern;

import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.util.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;

public class EmailValidator extends Validator {

	private static final String EMAIL_REGEX = "(\\w{1,}@([a-z]{3,7})\\.(ru|com|by|net))";
	private String email;

	public EmailValidator(String email) {
		this.email = email;
	}

	@Override
	public void validate() throws ValidatorException {
		isEmailPresent();
		isEmailMatchMatcher();
		continueValidate();
	}

	private void isEmailPresent() throws ValidatorException {
		if (email == null || email.isEmpty()) {
			throw new ValidatorException(MessageType.ILLEGAL_EMAIL.getMessage());
		}
	}

	private void isEmailMatchMatcher() throws ValidatorException {
		createMatcher();
		if (!matcher.find()) {
			throw new ValidatorException(MessageType.ILLEGAL_EMAIL.getMessage());
		}
	}

	private void createMatcher() {
		pattern = Pattern.compile(EMAIL_REGEX);
		matcher = pattern.matcher(email);
	}
}