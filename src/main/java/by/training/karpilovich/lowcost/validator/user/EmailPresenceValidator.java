package by.training.karpilovich.lowcost.validator.user;

import by.training.karpilovich.lowcost.exception.ServiceException;
import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.factory.ServiceFactory;
import by.training.karpilovich.lowcost.service.UserService;
import by.training.karpilovich.lowcost.util.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;

public class EmailPresenceValidator extends Validator {

	private String email;

	public EmailPresenceValidator(String email) {
		this.email = email;
	}

	@Override
	public void validate() throws ValidatorException {
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService initService = serviceFactory.getUserService();
		try {
			if (initService.countUserWithEmail(email) != 0) {
				throw new ValidatorException(MessageType.EMAIL_ALREADY_PRESENT.getType());
			}
		} catch (ServiceException e) {
			throw new ValidatorException(e.getMessage(), e);
		}

		if (hasNext()) {
			next.validate();
		}
	}

}
