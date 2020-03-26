package by.training.karpilovich.lowcost.service.impl;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.builder.UserBuilder;
import by.training.karpilovich.lowcost.dao.UserDao;
import by.training.karpilovich.lowcost.entity.Role;
import by.training.karpilovich.lowcost.entity.User;
import by.training.karpilovich.lowcost.exception.DaoException;
import by.training.karpilovich.lowcost.exception.ServiceException;
import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.factory.DaoFactory;
import by.training.karpilovich.lowcost.service.InitializatorService;
import by.training.karpilovich.lowcost.util.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;
import by.training.karpilovich.lowcost.validator.user.EmailPresenceValidator;
import by.training.karpilovich.lowcost.validator.user.EmailValidator;
import by.training.karpilovich.lowcost.validator.user.NameValidator;
import by.training.karpilovich.lowcost.validator.user.PasswordMatchValidator;
import by.training.karpilovich.lowcost.validator.user.PasswordValidator;

public class InitializatorServiceImpl implements InitializatorService {

	private static final Logger LOGGER = LogManager.getLogger(InitializatorServiceImpl.class);

	private InitializatorServiceImpl() {
	}

	private static final class InitializatorServiceInstanceHolder {
		private static final InitializatorServiceImpl INSTANCE = new InitializatorServiceImpl();
	}

	public static InitializatorService getInstance() {
		return InitializatorServiceInstanceHolder.INSTANCE;
	}

	@Override
	public User signin(String email, String password) throws ServiceException {
		Validator validator = new EmailValidator(email);
		Validator passwordValidator = new PasswordValidator(password);
		validator.setNext(passwordValidator);
		DaoFactory factory = DaoFactory.getInstance();
		UserDao userDao = factory.getUserDao();
		try {
			validator.validate();
			Optional<User> optional = userDao.selectUserByEmaiAndPassword(email, password);
			if (optional.isPresent()) {
				return optional.get();
			}
			throw new ServiceException(MessageType.ILLEGAL_EMAIL_OR_PASSWORD_MESSAGE.getType());
		} catch (ValidatorException | DaoException e) {
			LOGGER.warn(e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public User signup(String email, String password, String repeatPassword, String firstName, String lastName) throws ServiceException {
		DaoFactory factory = DaoFactory.getInstance();
		UserDao userDao = factory.getUserDao();
		Validator validator = getUserValidator(email, password, repeatPassword, firstName);
		try {
			validator.validate();
			User user = buildUser(email, password, firstName, lastName);
			userDao.add(user);
			return user;
		} catch (ValidatorException | DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(User user, String repeatedPassword) throws ServiceException {
		Validator passwordValidator = new PasswordMatchValidator(user.getPassword(), repeatedPassword);
		DaoFactory factory = DaoFactory.getInstance();
		UserDao userDao = factory.getUserDao();
		try {
			passwordValidator.validate();
			userDao.delete(user);
		} catch (ValidatorException | DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
	
	@Override
	public int countUserWithEmail(String email) throws ServiceException {
		Validator validator = new EmailValidator(email);
		DaoFactory factory = DaoFactory.getInstance();
		UserDao userDao = factory.getUserDao();
		try {
			validator.validate();
			return userDao.countUserWithEmail(email);
		} catch (ValidatorException | DaoException e) {
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
	
	private User buildUser(String email, String password, String firstName, String lastName) {
		UserBuilder builder = new UserBuilder();
		builder.setUserEmail(email);
		builder.setUserPassword(password);
		builder.setUserFirstName(firstName);
		builder.setUserLastName(lastName);
		builder.setUserRole(Role.USER);
		return builder.getUser();
	}

}
