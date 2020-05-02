package by.training.karpilovich.lowcost.validator.user;

import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.util.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;

public class PasswordMatchValidator extends Validator {

	private String password;
	private String repeatPassword;

	public PasswordMatchValidator(String password, String repeatPassword) {
		this.password = password;
		this.repeatPassword = repeatPassword;
	}

	@Override
	public void validate() throws ValidatorException {
		if (password == null || !password.equals(repeatPassword)) {
			throw new ValidatorException(MessageType.PASSWORDS_NOT_MATCH.getMessage());
		}
		continueValidate();
	}
}