package by.training.karpilovich.lowcost.dao;

import java.util.Optional;

import by.training.karpilovich.lowcost.entity.User;
import by.training.karpilovich.lowcost.exception.DAOException;

public interface UserDAO {

	int add(User user) throws DAOException;

	int update(User user) throws DAOException;

	int delete(User user) throws DAOException;

	Optional<User> selectUserByEmaiAndPassword(String email, String password) throws DAOException;

	int countUserWithEmail(String email) throws DAOException;

}
