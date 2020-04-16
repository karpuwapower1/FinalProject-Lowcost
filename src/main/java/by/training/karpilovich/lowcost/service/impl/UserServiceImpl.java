package by.training.karpilovich.lowcost.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.builder.UserBuilder;
import by.training.karpilovich.lowcost.dao.UserDAO;
import by.training.karpilovich.lowcost.entity.Role;
import by.training.karpilovich.lowcost.entity.User;
import by.training.karpilovich.lowcost.exception.DAOException;
import by.training.karpilovich.lowcost.exception.ServiceException;
import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.factory.DAOFactory;
import by.training.karpilovich.lowcost.service.UserService;
import by.training.karpilovich.lowcost.util.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;
import by.training.karpilovich.lowcost.validator.user.AmountValidator;
import by.training.karpilovich.lowcost.validator.user.EmailPresenceValidator;
import by.training.karpilovich.lowcost.validator.user.EmailValidator;
import by.training.karpilovich.lowcost.validator.user.FundsValidator;
import by.training.karpilovich.lowcost.validator.user.NameValidator;
import by.training.karpilovich.lowcost.validator.user.PasswordMatchValidator;
import by.training.karpilovich.lowcost.validator.user.PasswordValidator;

public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

	private UserDAO userDAO = DAOFactory.getInstance().getUserDAO();

	private UserServiceImpl() {
	}

	private static final class InitializatorServiceInstanceHolder {
		private static final UserServiceImpl INSTANCE = new UserServiceImpl();
	}

	public static UserService getInstance() {
		return InitializatorServiceInstanceHolder.INSTANCE;
	}

	@Override
	public User signIn(String email, String password) throws ServiceException {
		Validator validator = getEmailAndPasswordValidator(email, password);
		try {
			validator.validate();
			Optional<User> optional = userDAO.selectUserByEmaiAndPassword(email, password);
			if (optional.isPresent()) {
				return optional.get();
			}
			throw new ServiceException(MessageType.ILLEGAL_EMAIL_OR_PASSWORD_MESSAGE.getMessage());
		} catch (ValidatorException | DAOException e) {
			LOGGER.warn(e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public User signUp(String email, String password, String repeatPassword, String firstName, String lastName)
			throws ServiceException {
		Validator validator = getUserValidator(email, password, repeatPassword, firstName);
		try {
			validator.validate();
			User user = buildUser(email, password, firstName, lastName, BigDecimal.ZERO);
			userDAO.add(user);
			return user;
		} catch (ValidatorException | DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(User user, String repeatedPassword) throws ServiceException {
		Validator passwordValidator = new PasswordMatchValidator(user.getPassword(), repeatedPassword);
		try {
			passwordValidator.validate();
			userDAO.delete(user);
		} catch (ValidatorException | DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public int countUserWithEmail(String email) throws ServiceException {
		Validator validator = new EmailValidator(email);
		try {
			validator.validate();
			return userDAO.countUserWithEmail(email);
		} catch (ValidatorException | DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public User deposit(User user, String amount) throws ServiceException {
		if (user == null) {
			throw new ServiceException(MessageType.USER_IS_NULL.getMessage());
		}
		BigDecimal additionalAmount = takeBigDecimalFromString(amount);
		Validator validator = new AmountValidator(additionalAmount);
		try {
			validator.validate();
			BigDecimal newBalance = user.getBalanceAmount().add(additionalAmount);
			userDAO.updateUserBalance(user, newBalance);
			user.setBalanceAmount(newBalance);
			return user;
		} catch (ValidatorException | DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public User withdrow(User user, String amount) throws ServiceException {
		BigDecimal substracted = takeBigDecimalFromString(amount);
		Validator validator = new AmountValidator(substracted);
		Validator fundsValidator = new FundsValidator(user.getBalanceAmount(), substracted);
		validator.setNext(fundsValidator);
		try {
			validator.validate();
			BigDecimal newBalance = user.getBalanceAmount().subtract(substracted);
			userDAO.updateUserBalance(user, newBalance);
			user.setBalanceAmount(newBalance);
			return user;
		} catch (ValidatorException | DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	private Validator getUserValidator(String email, String password, String repeatPassword, String firstName) {
		Validator validator = new EmailValidator(email);
		Validator emailPresenceValidator = new EmailPresenceValidator(email);
		Validator passwordValidator = new PasswordValidator(password);
		Validator passwordMatchValidator = new PasswordMatchValidator(password, repeatPassword);
		Validator nameValidator = new NameValidator(firstName);
		validator.setNext(emailPresenceValidator);
		emailPresenceValidator.setNext(passwordValidator);
		passwordValidator.setNext(passwordMatchValidator);
		passwordMatchValidator.setNext(nameValidator);
		return validator;
	}

	private Validator getEmailAndPasswordValidator(String email, String password) {
		Validator validator = new EmailValidator(email);
		Validator passwordValidator = new PasswordValidator(password);
		validator.setNext(passwordValidator);
		return validator;
	}

	private User buildUser(String email, String password, String firstName, String lastName, BigDecimal amount) {
		UserBuilder builder = new UserBuilder();
		builder.setUserEmail(email);
		builder.setUserPassword(password);
		builder.setUserFirstName(firstName);
		builder.setUserLastName(lastName);
		builder.setUserRole(Role.USER);
		builder.setBalanceAmount(amount);
		return builder.getUser();
	}

	private BigDecimal takeBigDecimalFromString(String value) throws ServiceException {
		try {
			return new BigDecimal(value);
		} catch (NumberFormatException e) {
			throw new ServiceException(MessageType.INVALID_NUMBER_FORMAT.getMessage());
		}
	}
}