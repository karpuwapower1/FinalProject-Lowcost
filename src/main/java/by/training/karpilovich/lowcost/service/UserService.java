package by.training.karpilovich.lowcost.service;

import by.training.karpilovich.lowcost.entity.User;
import by.training.karpilovich.lowcost.exception.ServiceException;

public interface UserService {

	User signIn(String email, String password) throws ServiceException;

	User signUp(String email, String password, String repeatedPassword, String firstName, String lastName) throws ServiceException;

	void delete(User user, String repeatedPassword) throws ServiceException;
	
	int countUserWithEmail(String email) throws ServiceException;
	
	User deposit(User user, String amount) throws ServiceException;
	
	User withdrow(User user, String amount) throws ServiceException;
}