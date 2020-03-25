package by.training.karpilovich.lowcost.service;

import by.training.karpilovich.lowcost.entity.User;
import by.training.karpilovich.lowcost.exception.ServiceException;

public interface InitializatorService {

	public User signin(String email, String password) throws ServiceException;

	public User signup(String email, String password, String repeatedPassword, String firstName, String lastName) throws ServiceException;

	public void delete(User user, String repeatedPassword) throws ServiceException;

}
