package by.training.karpilovich.lowcost.service;

import by.training.karpilovich.lowcost.entity.User;
import by.training.karpilovich.lowcost.exception.ServiceException;

public interface InitializatorService {

	User signin(String email, String password) throws ServiceException;

	User signup(String email, String password, String repeatedPassword, String firstName, String lastName) throws ServiceException;

	void delete(User user, String repeatedPassword) throws ServiceException;
	
	int countUserWithEmail(String email) throws ServiceException;

}
