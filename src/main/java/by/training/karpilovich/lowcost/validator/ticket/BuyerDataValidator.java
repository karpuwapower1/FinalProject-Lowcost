package by.training.karpilovich.lowcost.validator.ticket;

import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.validator.Validator;
import by.training.karpilovich.lowcost.validator.flight.LuggageValidator;
import by.training.karpilovich.lowcost.validator.user.EmailValidator;
import by.training.karpilovich.lowcost.validator.user.NameValidator;

public class UserDataValidator extends Validator {
	
	private String email;
	private String firstName;
	private String lastName;
	private String passportNumber;
	int luggageQuantity;
	
	public UserDataValidator(String email, String firstName, String lastName, String passportNumber, int luggageQuantity) {
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.passportNumber = passportNumber;
		this.luggageQuantity = luggageQuantity;
	}
	@Override
	public void validate() throws ValidatorException {
		Validator validator = new EmailValidator(email);
		Validator firstNameValidator = new NameValidator(firstName);
		Validator lastNameValidator = new NameValidator(lastName);
		Validator passportValidator = new PassportNumberValidator(passportNumber);
		Validator luggageValidator = new LuggageValidator(luggageQuantity);
		validator.setNext(firstNameValidator);
		firstNameValidator.setNext(lastNameValidator);
		lastNameValidator.setNext(passportValidator);
		passportValidator.setNext(luggageValidator);
		validator.validate();
		continueValidate();
	}
	
	

}
