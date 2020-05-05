package by.training.karpilovich.lowcost.dao;

import java.util.Optional;

import by.training.karpilovich.lowcost.entity.User;
import by.training.karpilovich.lowcost.exception.DAOException;

/**
 * Interface describes the opportunity that data source provide to store and
 * restore {@link User} entity
 * 
 * @author Aliaksei Karpilovich
 *
 */
public interface UserDAO {

	/**
	 * Saves the <tt>user</tt> into data source. Throws DAOException if an error
	 * occurs while writing a <tt>user</tt>
	 * 
	 * @param user the {@link User} that must be added to data source
	 * @throws DAOException if an error occurs while writing a <tt>user</tt>
	 */
	void add(User user) throws DAOException;

	/**
	 * Updates information about {@link User} in a data source that email equals to
	 * <tt>user.getEmail()</tt> method returned value. Throws DAOException if an
	 * error occurs while writing a <tt>user</tt>
	 * 
	 * @param user {@link User} that information is updating
	 * @throws DAOException if an error occurs while writing a <tt>user</tt>
	 */
	void update(User user) throws DAOException;

	/**
	 * Deletes {@link User} from data source that email is equals to
	 * <tt>user.getEamil()</tt> method returned value. Throws DAOException if an
	 * error occurs while deleting a <tt>city</tt>
	 * 
	 * @param user {@link User} that must be deleted
	 * @throws DAOException if an error occurs while deleting a <tt>user</tt>
	 */
	void delete(User user) throws DAOException;

	/**
	 * Retrieves and returns {@link User} with <tt>email</tt> and <tt>password</tt>.
	 * If no such user contains into data source returns Optional.empty(). Throws
	 * DAOException if an error occurs while getting a <tt>user</tt>
	 * 
	 * @param email    {@link User}'s email
	 * @param password {@link User}'s password
	 * @return an {@link User} that getEmail() and getPassword() methods returned
	 *         values equal to <tt>email</tt> and to <tt>password</tt>
	 * @throws DAOException if an error occurs while getting a <tt>user</tt>
	 */
	Optional<User> selectUserByEmaiAndPassword(String email, String password) throws DAOException;

	/**
	 * Counts and returns quantity of {@link User} that getEmail() method returned
	 * value equals to <tt>email</tt>. Throws DAOException if an error occurs while
	 * counting a <tt>user</tt>
	 * 
	 * @param email the {@link User}'s email
	 * @return user's quantity that getEmail() method returned value equals to
	 *         <tt>email</tt>
	 * @throws DAOException if an error occurs while counting a <tt>user</tt>
	 */
	int countUserWithEmail(String email) throws DAOException;

	/**
	 * Returns {@link User}'s that getEmail() method returned value equals to
	 * <tt>email</tt> password. If no such {@link User} contains into data source
	 * return <tt>Optional.empty()</tt>. DAOException if an error occurs while
	 * working with data source
	 * 
	 * @param email {@link User}'s email
	 * @return {@link User}'s that getEmail() method returned value equals to
	 *         <tt>email</tt> password
	 * @throws DAOException if an error occurs while working with data source
	 */
	Optional<String> getUserPasswordByEmail(String email) throws DAOException;
}