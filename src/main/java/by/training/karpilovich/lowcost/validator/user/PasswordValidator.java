package by.training.karpilovich.lowcost.validator.user;

import java.util.regex.Pattern;

import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.util.message.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;

public class PasswordValidator extends Validator {

	private static final String PASSWORD_REGEX = "(\\w{5,})";

	private String password;

	public PasswordValidator(String password) {
		this.password = password;

	}

	@Override
	public void validate() throws ValidatorException {
		isPasswordPresent();
		isPasswordMatchPattern();
		continueValidate();
	}

	private void isPasswordPresent() throws ValidatorException {
		if (password == null || password.isEmpty()) {
			throw new ValidatorException(MessageType.ILLEGAL_PASSWORD.getMessage());
		}
	}

	private void isPasswordMatchPattern() throws ValidatorException {
		createMatcher();
		if (!matcher.find()) {
			throw new ValidatorException(MessageType.ILLEGAL_PASSWORD.getMessage());
		}
	}

	private void createMatcher() {
		pattern = Pattern.compile(PASSWORD_REGEX);
		matcher = pattern.matcher(password);
	}
}