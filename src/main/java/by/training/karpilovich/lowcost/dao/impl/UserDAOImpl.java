package by.training.karpilovich.lowcost.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.builder.UserBuilder;
import by.training.karpilovich.lowcost.connection.ConnectionPool;
import by.training.karpilovich.lowcost.dao.UserDAO;
import by.training.karpilovich.lowcost.entity.Role;
import by.training.karpilovich.lowcost.entity.User;
import by.training.karpilovich.lowcost.exception.ConnectionPoolException;
import by.training.karpilovich.lowcost.exception.DAOException;
import by.training.karpilovich.lowcost.util.message.MessageType;

public class UserDAOImpl implements UserDAO {

	private static final String ADD_QUERY = "INSERT INTO airport_user(email, user_password, first_name, last_name, balance_amount) "
			+ " VALUES (?,?,?,?,?)";

	private static final int EMAIL_ADD_QUERY_INDEX = 1;
	private static final int PASSWORD_ADD_QUERY_INDEX = 2;
	private static final int FIRST_NAME_ADD_QUERY_INDEX = 3;
	private static final int LAST_NAME_ADD_QUERY_INDEX = 4;
	private static final int BALANCE_AMOUNT_ADD_QUERY_INDEX = 5;

	private static final String DELETE_QUERY = "DELETE FROM airport_user WHERE email=?";

	private static final int EMAIL_DELETE_QUERY_INDEX = 1;

	private static final String UPDATE_QUERY = "UPDATE airport_user "
			+ "SET user_password=?, first_name=?, last_name=?, user_role_id=?, balance_amount=? WHERE email=?";

	private static final int UPDATE_QUERY_PASSWORD_INDEX = 1;
	private static final int UPDATE_QUERY_FIRST_NAME_INDEX = 2;
	private static final int UPDATE_QUERY_LAST_NAME_INDEX = 3;
	private static final int UPDATE_QUERY_ROLE_INDEX = 4;
	private static final int UPDATE_QUERY_BALANCE_AMOUNT_INDEX = 5;
	private static final int UPDATE_QUERY_CONDITION_EMAIL_INDEX = 6;

	private static final String SELECT_BY_EMAIL_AND_PASSWORD_QUERY = "SELECT email, user_password, first_name, last_name, "
			+ " user_role.name AS role_name, balance_amount " + " FROM airport_user "
			+ " JOIN user_role on airport_user.user_role_id=user_role.id WHERE email=? AND user_password=?";

	private static final int EMAIL_SELECT_BY_EMAIL_AND_PASSWORD_QUERY_INDEX = 1;
	private static final int PASSWORD_SELECT_BY_EMAIL_AND_PASSWORD_QUERY_INDEX = 2;

	private static final String SELECT_USER_PASSWORD_BY_EMAIL = "SELECT user_password FROM airport_user WHERE email=?";

	private static final int SELECT_USER_PASSWORD_BY_EMAIL_EMAIL_INDEX = 1;

	private static final String SELECT_USER_RESULT_EMAIL_ROW = "email";
	private static final String SELECT_USER_RESULT_PASSWORD_ROW = "user_password";
	private static final String SELECT_USER_RESULT_FIRST_NAME_ROW = "first_name";
	private static final String SELECT_USER_RESULT_LAST_NAME_ROW = "last_name";
	private static final String SELECT_USER_RESULT_ROLE_ROW = "role_name";
	private static final String SELECT_USER_RESULT_BALANCE_AMOUNT_ROW = "balance_amount";

	private static final String COUNT_EMAIL_QUERY = "SELECT count(email) FROM airport_user GROUP BY email HAVING email=?";

	private static final int COUNT_EMAIL_QUERY_EMAIL_INDEX = 1;

	private static final int COUNT_EMAIL_QUERY_RESULT_INDEX = 1;

	private static final Logger LOGGER = LogManager.getLogger(UserDAOImpl.class);

	private ConnectionPool pool = ConnectionPool.getInstance();

	private UserDAOImpl() {
	}

	public static UserDAO getInstance() {
		return MySqlUserDaoInstanceHolder.INSTANCE;
	}

	@Override
	public void add(User user) throws DAOException {
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = connection.prepareStatement(ADD_QUERY);) {
			prepareAddStatement(statement, user);
			statement.executeUpdate();
		} catch (SQLException | ConnectionPoolException e) {
			LOGGER.error("error while adding a user" + user.toString(), e);
			throw new DAOException(MessageType.INTERNAL_ERROR.getMessage(), e);
		}
	}

	@Override
	public void update(User user) throws DAOException {
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);) {
			prepareUpdateStatement(statement, user);
			statement.executeUpdate();
		} catch (SQLException | ConnectionPoolException e) {
			LOGGER.error("error while updateng a user" + user.toString(), e);
			throw new DAOException(MessageType.INTERNAL_ERROR.getMessage(), e);
		}
	}

	@Override
	public void delete(User user) throws DAOException {
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);) {
			prepareDeleteStatement(statement, user);
			statement.executeUpdate();
		} catch (SQLException | ConnectionPoolException e) {
			LOGGER.error("error while deleting a user" + user.toString(), e);
			throw new DAOException(MessageType.INTERNAL_ERROR.getMessage(), e);
		}
	}

	@Override
	public Optional<User> selectUserByEmaiAndPassword(String email, String password) throws DAOException {
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = connection.prepareStatement(SELECT_BY_EMAIL_AND_PASSWORD_QUERY);) {
			prepareSelectByEmailAndPasswordStatement(statement, email, password);
			return executeSelectUserStatement(statement);
		} catch (SQLException | ConnectionPoolException e) {
			LOGGER.error("error while select user by email and password email=" + email + " password=" + password, e);
			throw new DAOException(MessageType.INTERNAL_ERROR.getMessage(), e);
		}
	}

	@Override
	public int countUserWithEmail(String email) throws DAOException {
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = connection.prepareStatement(COUNT_EMAIL_QUERY);) {
			prepareCountEmailStatementStatement(statement, email);
			return executeCountUserStatement(statement);
		} catch (SQLException | ConnectionPoolException e) {
			LOGGER.error("error while counting email=" + email, e);
			throw new DAOException(MessageType.INTERNAL_ERROR.getMessage(), e);
		}
	}

	@Override
	public Optional<String> getUserPasswordByEmail(String email) throws DAOException {
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = connection.prepareStatement(SELECT_USER_PASSWORD_BY_EMAIL);) {
			prepareSelectUserPasswordStatement(statement, email);
			return executeSelectPasswordStatement(statement);
		} catch (SQLException | ConnectionPoolException e) {
			LOGGER.error("Error while getting password by email=" + email, e);
			throw new DAOException(MessageType.INTERNAL_ERROR.getMessage(), e);
		}
	}

	private static final class MySqlUserDaoInstanceHolder {
		private static final UserDAOImpl INSTANCE = new UserDAOImpl();
	}

	private void prepareAddStatement(PreparedStatement statement, User user) throws SQLException {
		statement.setString(EMAIL_ADD_QUERY_INDEX, user.getEmail());
		statement.setString(PASSWORD_ADD_QUERY_INDEX, user.getPassword());
		statement.setString(FIRST_NAME_ADD_QUERY_INDEX, user.getFirstName());
		statement.setString(LAST_NAME_ADD_QUERY_INDEX, user.getLastName());
		statement.setBigDecimal(BALANCE_AMOUNT_ADD_QUERY_INDEX, user.getBalanceAmount());
	}

	private void prepareDeleteStatement(PreparedStatement statement, User user) throws SQLException {
		statement.setString(EMAIL_DELETE_QUERY_INDEX, user.getEmail());
	}

	private void prepareUpdateStatement(PreparedStatement statement, User user) throws SQLException {
		statement.setString(UPDATE_QUERY_PASSWORD_INDEX, user.getPassword());
		statement.setString(UPDATE_QUERY_FIRST_NAME_INDEX, user.getFirstName());
		statement.setString(UPDATE_QUERY_LAST_NAME_INDEX, user.getLastName());
		statement.setInt(UPDATE_QUERY_ROLE_INDEX, user.getRole().getId());
		statement.setString(UPDATE_QUERY_CONDITION_EMAIL_INDEX, user.getEmail());
		statement.setBigDecimal(UPDATE_QUERY_BALANCE_AMOUNT_INDEX, user.getBalanceAmount());
	}

	private void prepareSelectByEmailAndPasswordStatement(PreparedStatement statement, String email, String password)
			throws SQLException {
		statement.setString(EMAIL_SELECT_BY_EMAIL_AND_PASSWORD_QUERY_INDEX, email);
		statement.setString(PASSWORD_SELECT_BY_EMAIL_AND_PASSWORD_QUERY_INDEX, password);
	}

	private void prepareCountEmailStatementStatement(PreparedStatement statement, String email) throws SQLException {
		statement.setString(COUNT_EMAIL_QUERY_EMAIL_INDEX, email);
	}

	private void prepareSelectUserPasswordStatement(PreparedStatement statement, String email) throws SQLException {
		statement.setString(SELECT_USER_PASSWORD_BY_EMAIL_EMAIL_INDEX, email);
	}

	private Optional<User> executeSelectUserStatement(PreparedStatement statement) throws SQLException {
		try (ResultSet resultSet = statement.executeQuery()) {
			return getUserFromResultSet(resultSet);
		}
	}

	private Optional<User> getUserFromResultSet(ResultSet resultSet) throws SQLException {
		return resultSet.next() ? Optional.of(buildUser(resultSet)) : Optional.empty();
	}

	private User buildUser(ResultSet resultSet) throws SQLException {
		return new UserBuilder()
				.setUserRole(Role.valueOf(resultSet.getString(SELECT_USER_RESULT_ROLE_ROW).toUpperCase()))
				.setUserEmail(resultSet.getString(SELECT_USER_RESULT_EMAIL_ROW))
				.setUserPassword(resultSet.getString(SELECT_USER_RESULT_PASSWORD_ROW))
				.setUserFirstName(resultSet.getString(SELECT_USER_RESULT_FIRST_NAME_ROW))
				.setUserLastName(resultSet.getString(SELECT_USER_RESULT_LAST_NAME_ROW))
				.setBalanceAmount(resultSet.getBigDecimal(SELECT_USER_RESULT_BALANCE_AMOUNT_ROW)).getUser();
	}

	private int executeCountUserStatement(PreparedStatement statement) throws SQLException {
		try (ResultSet resultSet = statement.executeQuery()) {
			return getCountUserFromResultSet(resultSet);
		}
	}

	private int getCountUserFromResultSet(ResultSet resultSet) throws SQLException {
		return resultSet.next() ? resultSet.getInt(COUNT_EMAIL_QUERY_RESULT_INDEX) : 0;
	}

	private Optional<String> executeSelectPasswordStatement(PreparedStatement statement) throws SQLException {
		try (ResultSet resultSet = statement.executeQuery()) {
			return getPasswordFromResultSet(resultSet);
		}
	}

	private Optional<String> getPasswordFromResultSet(ResultSet resultSet) throws SQLException {
		return resultSet.next() ? Optional.of(resultSet.getString(SELECT_USER_RESULT_PASSWORD_ROW)) : Optional.empty();
	}
}