package by.training.karpilovich.lowcost.service;

import by.training.karpilovich.lowcost.entity.User;
import by.training.karpilovich.lowcost.exception.ServiceException;

/**
 * Describes the behavior of {@link User} entity.
 * 
 * @author Aliaksei Karpilovich
 *
 */
public interface UserService {

	/**
	 * Signs in and return {@link User} with <tt>email</tt> and <tt>password</tt>.
	 * Throws ServiceExcption if <tt>email</tt> or <tt>password</tt> is null or
	 * empty or if <tt>email</tt> or <tt>password</tt> do not accords to specify
	 * pattern. {@see by.training.karpilovich.lowcost.validator.user.EmailValidator}
	 * and {@see by.training.karpilovich.lowcost.validator.user.PasswordValidator}
	 * or if {@link User} with <tt>email</tt> and <tt>password</tt> do not present
	 * into data source or if an error occurs while searching {@link User} into data
	 * source
	 * 
	 * @param email    {@link User}'s email
	 * @param password {@link User}'s password
	 * @return {@link User} with <tt>email</tt> and <tt>password</tt>
	 * @throws ServiceException if <tt>email</tt> or <tt>password</tt> is null or
	 *                          empty or if <tt>email</tt> or <tt>password</tt> do
	 *                          not accords to specify pattern
	 *                          {@see by.training.karpilovich.lowcost.validator.user.EmailValidator}
	 *                          and
	 *                          {@see by.training.karpilovich.lowcost.validator.user.PasswordValidator}
	 *                          or if {@link User} with <tt>email</tt> and
	 *                          <tt>password</tt> do not present into data source or
	 *                          if an error occurs while searching {@link User} into
	 *                          data source
	 */
	User signIn(String email, String password) throws ServiceException;

	/**
	 * Registers and returns the {@link User} with parameters. Throws
	 * ServiceException if <tt>email</tt> or <tt>password</tt> or <tt>firstName</tt>
	 * is null or empty or if <tt>email</tt> or <tt>password</tt> do not accords to
	 * specify pattern
	 * {@see by.training.karpilovich.lowcost.validator.user.EmailValidator} and
	 * {@see by.training.karpilovich.lowcost.validator.user.PasswordValidator} or if
	 * user with <tt>email</tt> has already registered or if <tt>password</tt> and
	 * <tt>repeatedPassword</tt> do not match or if an error occurs while writing
	 * new {@link User} into data source
	 * 
	 * @param email            {@link User}'s email
	 * @param password         {@link User}'s password
	 * @param repeatedPassword <tt>password</tt> typed again that must be equals to
	 *                         <tt>password</tt>
	 * @param firstName        {@link User}'s first name
	 * @param lastName         {@link User}'s last name (can be null)
	 * @return created from parameters {@link User}
	 * @throws ServiceException if <tt>email</tt> or <tt>password</tt> or
	 *                          <tt>firstName</tt> is null or empty or if
	 *                          <tt>email</tt> or <tt>password</tt> do not accords
	 *                          to specify pattern
	 *                          {@see by.training.karpilovich.lowcost.validator.user.EmailValidator}
	 *                          and
	 *                          {@see by.training.karpilovich.lowcost.validator.user.PasswordValidator}
	 *                          or if user with <tt>email</tt> has already
	 *                          registered or if <tt>password</tt> and
	 *                          <tt>repeatedPassword</tt> do not match or if an
	 *                          error occurs while writing new {@link User} into
	 *                          data source
	 */
	User signUp(String email, String password, String repeatedPassword, String firstName, String lastName)
			throws ServiceException;

	/**
	 * Deletes <tt>user</tt> from data source. Throws ServiceException if
	 * <tt>user</tt> is null or if <tt>user</tt>'s password and
	 * <tt>repeatedPassword</tt> are not equals or if an error occurs while deleting
	 * user from data source.
	 * 
	 * @param user             the {@link User} that is going to be deleted
	 * @param repeatedPassword the <tt>user</tt>'s password typed again and that
	 *                         must be equals to <tt>user</tt>'s password
	 * @throws ServiceException if <tt>user</tt> is null or if <tt>user</tt>'s
	 *                          password and <tt>repeatedPassword</tt> are not
	 *                          equals or if an error occurs while deleting user
	 *                          from data source.
	 */
	void deleteUser(User user, String repeatedPassword) throws ServiceException;

	/**
	 * Counts and returns count of {@link User} with <tt>email</tt> that data source
	 * contains. Throws ServiceException if <tt>email</tt> is null or empty or if
	 * <tt>email</tt> do not match the specified pattern
	 * {@see by.training.karpilovich.lowcost.validator.user.EmailValidator} or if an
	 * error occurs while counting user's
	 * 
	 * @param email the {@link User}'s email that must be count
	 * @return quantity of {@link User} with <tt>email</tt> that is stored into data
	 *         source
	 * @throws ServiceException if <tt>email</tt> is null or empty or if
	 *                          <tt>email</tt> do not match the specified pattern
	 *                          {@see by.training.karpilovich.lowcost.validator.user.EmailValidator}
	 *                          or if an error occurs while counting user's
	 */
	int countUserWithEmail(String email) throws ServiceException;

	/**
	 * Adds <tt>amount</tt> to <tt>user</tt>'s balance. Throws ServiceException if
	 * <tt>user</tt> is null or if <tt>amount</tt> is null or empty or an error
	 * occurs while parsing an amount or <tt>amount</tt> is less than 0 or an error
	 * occurs while writing updating <tt>amount</tt> into data source.
	 * 
	 * @param user   the {@link User} whose balance is deposit
	 * @param amount the amount of money that is adding to current <tt>user</tt>'s
	 *               balance
	 * @return updated <tt>user</tt>
	 * @throws ServiceException if <tt>user</tt> is null or if <tt>amount</tt> is
	 *                          null or empty or an error occurs while parsing an
	 *                          amount or <tt>amount</tt> is less than 0 or an error
	 *                          occurs while writing updating <tt>amount</tt> into
	 *                          data source.
	 */
	User deposit(User user, String amount) throws ServiceException;

	/**
	 * Changes and updates <tt>user</tt>'s password. Returns user with
	 * <tt>newPassword</tt>. Throws ServiceException if <tt>newPassword</tt> is null
	 * or empty or if <tt>newPassword</tt> does not match pattern
	 * {@see by.training.karpilovich.lowcost.validator.user.PasswordValidator} or
	 * <tt>repeatNewPassword</tt> or if an error occurs while updating
	 * <tt>user</tt>'s password into data source.
	 * 
	 * @param user              the {@link User} whose password is changing
	 * @param newPassword       the new <tt>user</tt>'s password
	 * @param repeatNewPassword the analog of <tt>newPassword</tt>
	 * @return {@link User} with <tt>newPassword</tt>
	 * @throws ServiceException if <tt>newPassword</tt> is null or empty or if
	 *                          <tt>newPassword</tt> does not match pattern
	 *                          {@see by.training.karpilovich.lowcost.validator.user.PasswordValidator}
	 *                          or <tt>repeatNewPassword</tt> or if an error occurs
	 *                          while updating <tt>user</tt>'s password into data
	 *                          source.
	 */
	User changePassword(User user, String newPassword, String repeatNewPassword) throws ServiceException;

	/**
	 * Gets and returns {@link User}'s password according to <tt>email</tt>. Throws
	 * ServiceExcepotion if <tt>email</tt> is null or empty or if <tt>email</tt> do
	 * not match the specified pattern
	 * {@see by.training.karpilovich.lowcost.validator.user.EmailValidator} or if
	 * data source does not contain user with <tt>email</tt> or if an error occurs
	 * while working with data source
	 * 
	 * @param email the {@link User} that password is getting email
	 * @return {@link User}'s password whose email is <tt>email</tt>
	 * @throws ServiceException if <tt>email</tt> is null or empty or if
	 *                          <tt>email</tt> do not match the specified pattern
	 *                          {@see by.training.karpilovich.lowcost.validator.user.EmailValidator}
	 *                          or if data source does not contain user with
	 *                          <tt>email</tt> or if an error occurs while working
	 *                          with data source
	 */
	String getPasswordByEmail(String email) throws ServiceException;
}