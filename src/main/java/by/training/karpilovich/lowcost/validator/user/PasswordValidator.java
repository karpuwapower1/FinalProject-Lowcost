package by.training.karpilovich.lowcost.validator.user;

import java.util.regex.Pattern;

import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.util.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;

public class PasswordValidator extends Validator {
	
	private static final String PASSWORD_REGEX = "(\\w{5,})";
	
	private String password;

	public PasswordValidator(String password) {
		this.password = password;
		pattern = Pattern.compile(PASSWORD_REGEX);
		matcher = pattern.matcher(password);
	}

	@Override
	public void validate() throws ValidatorException {
		if (password == null || password.isEmpty() || !matcher.find()) {
			throw new ValidatorException(MessageType.ILLEGAL_PASSWORD.getType());
		}
		if (hasNext()) {
			next.validate();
		}
	}

}
