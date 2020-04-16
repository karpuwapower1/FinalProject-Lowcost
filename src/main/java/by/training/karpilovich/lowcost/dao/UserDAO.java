package by.training.karpilovich.lowcost.dao;

import java.math.BigDecimal;
import java.util.Optional;

import by.training.karpilovich.lowcost.entity.User;
import by.training.karpilovich.lowcost.exception.DAOException;

public interface UserDAO {

	void add(User user) throws DAOException;

	void update(User user) throws DAOException;

	void delete(User user) throws DAOException;
	
	void updateUserBalance(User user, BigDecimal balance) throws DAOException;

	Optional<User> selectUserByEmaiAndPassword(String email, String password) throws DAOException;

	int countUserWithEmail(String email) throws DAOException;
}