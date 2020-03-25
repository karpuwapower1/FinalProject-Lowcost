package by.training.karpilovich.lowcost.dao;

import java.util.Optional;

import by.training.karpilovich.lowcost.entity.User;
import by.training.karpilovich.lowcost.exception.DaoException;

public interface UserDao {

	int add(User user) throws DaoException;

	int update(User user) throws DaoException;

	int delete(User user) throws DaoException;

	Optional<User> selectUserByEmaiAndPassword(String email, String password) throws DaoException;

	int countUserWithEmail(String email) throws DaoException;

}
