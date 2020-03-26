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
import by.training.karpilovich.lowcost.dao.UserDao;
import by.training.karpilovich.lowcost.entity.Role;
import by.training.karpilovich.lowcost.entity.User;
import by.training.karpilovich.lowcost.exception.ConnectionPoolException;
import by.training.karpilovich.lowcost.exception.DaoException;
import by.training.karpilovich.lowcost.util.MessageType;

public class MySqlUserDao implements UserDao {

	private static final String ADD_QUERY = "INSERT INTO airport_user(email, user_password, first_name, last_name) "
			+ " VALUES (?,?,?,?)";

	private static final int EMAIL_ADD_QUERY_INDEX = 1;
	private static final int PASSWORD_ADD_QUERY_INDEX = 2;
	private static final int FIRST_NAME_ADD_QUERY_INDEX = 3;
	private static final int LAST_NAME_ADD_QUERY_INDEX = 4;

	private static final String DELETE_QUERY = "DELETE FROM airport_user WHERE email=?";

	private static final int EMAIL_DELETE_QUERY_INDEX = 1;

	private static final String UPDATE_QUERY = "UPDATE airport_user "
			+ "set password=?, first_name=?, last_name=? role_id=? " + "WHERE email=?";

	private static final int UPDATE_QUERY_PASSWORD_INDEX = 1;
	private static final int UPDATE_QUERY_FIRST_NAME_INDEX = 2;
	private static final int UPDATE_QUERY_LAST_NAME_INDEX = 3;
	private static final int UPDATE_QUERY_ROLE_INDEX = 4;
	private static final int UPDATE_QUERY_CONDITION_EMAIL_INDEX = 5;

	private static final String SELECT_BY_EMAIL_AND_PASSWORD_QUERY = "SELECT email, user_password, first_name, last_name, user_role.name "
			+ " FROM airport_user JOIN user_role on airport_user.user_role_id=user_role.id WHERE email=? AND user_password=?";

	private static final int EMAIL_SELECT_BY_EMAIL_AND_PASSWORD_QUERY_INDEX = 1;
	private static final int PASSWORD_SELECT_BY_EMAIL_AND_PASSWORD_QUERY_INDEX = 2;

	private static final int SELECT_BY_EMAIL_AND_PASSWORD_RESULT_EMAIL_INDEX = 1;
	private static final int SELECT_BY_EMAIL_AND_PASSWORD_RESULT_PASSWORD_INDEX = 2;
	private static final int SELECT_BY_EMAIL_AND_PASSWORD_RESULT_FIRST_NAME_INDEX = 2;
	private static final int SELECT_BY_EMAIL_AND_PASSWORD_RESULT_LAST_NAME_INDEX = 3;
	private static final int SELECT_BY_EMAIL_AND_PASSWORD_RESULT_ROLE_INDEX = 4;

	private static final String COUNT_EMAIL_QUERY = "SELECT count(email) FROM airport_user GROUP BY email HAVING email=?";

	private static final int COUNT_EMAIL_QUERY_EMAIL_INDEX = 1;

	private static final int COUNT_EMAIL_QUERY_RESULT_INDEX = 1;

	private static final Logger LOGGER = LogManager.getLogger(MySqlUserDao.class);

	private MySqlUserDao() {
	}

	private static final class MySqlUserDaoInstanceHolder {
		private static final MySqlUserDao INSTANCE = new MySqlUserDao();
	}

	public static UserDao getInstance() {
		return MySqlUserDaoInstanceHolder.INSTANCE;
	}

	@Override
	public int add(User user) throws DaoException {
		ConnectionPool pool = ConnectionPool.getInstance();
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = prepareAddStatement(connection, user);) {
			return statement.executeUpdate();
		} catch (SQLException | ConnectionPoolException e) {
			LOGGER.error("error while adding a user" + user.toString(), e);
			throw new DaoException(MessageType.INTERNAL_ERROR.getType());
		}
	}

	@Override
	public int update(User user) throws DaoException {
		ConnectionPool pool = ConnectionPool.getInstance();
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = prepareUpdateStatement(connection, user);) {
			return statement.executeUpdate();
		} catch (SQLException | ConnectionPoolException e) {
			LOGGER.error("error while updateng a user" + user.toString(), e);
			throw new DaoException(MessageType.INTERNAL_ERROR.getType());
		}
	}

	@Override
	public int delete(User user) throws DaoException {
		ConnectionPool pool = ConnectionPool.getInstance();
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = prepareDeleteStatement(connection, user);) {
			return statement.executeUpdate();
		} catch (SQLException | ConnectionPoolException e) {
			LOGGER.error("error while deleting a user" + user.toString(), e);
			throw new DaoException(MessageType.INTERNAL_ERROR.getType());
		}
	}

	@Override
	public Optional<User> selectUserByEmaiAndPassword(String email, String password) throws DaoException {
		ConnectionPool pool = ConnectionPool.getInstance();
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = prepageSelectByEmailAndPasswordStatement(connection, email, password);
				ResultSet resultSet = statement.executeQuery()) {
			Optional<User> optional = Optional.empty();
			if (resultSet.next()) {
				optional = Optional.of(buildUser(resultSet));
			}
			return optional;
		} catch (SQLException | ConnectionPoolException e) {
			LOGGER.error("error while select user by email and password email=" + email + " password=" + password, e);
			throw new DaoException(MessageType.INTERNAL_ERROR.getType());
		}
	}

	@Override
	public int countUserWithEmail(String email) throws DaoException {
		ConnectionPool pool = ConnectionPool.getInstance();
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = prepareCountEmailStatementStatement(connection, email);
				ResultSet resultSet = statement.executeQuery()) {
			int result = 0;
			if (resultSet.next()) {
				result = resultSet.getInt(COUNT_EMAIL_QUERY_RESULT_INDEX);
			}
			return result;
		} catch (SQLException | ConnectionPoolException e) {
			LOGGER.error("error while counting email=" + email, e);
			throw new DaoException(MessageType.INTERNAL_ERROR.getType());
		}
	}

	private PreparedStatement prepareAddStatement(Connection connection, User user) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(ADD_QUERY);
		statement.setString(EMAIL_ADD_QUERY_INDEX, user.getEmail());
		statement.setString(PASSWORD_ADD_QUERY_INDEX, user.getPassword());
		statement.setString(FIRST_NAME_ADD_QUERY_INDEX, user.getFirstName());
		statement.setString(LAST_NAME_ADD_QUERY_INDEX, user.getLastName());
		return statement;
	}

	private PreparedStatement prepareDeleteStatement(Connection connection, User user) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);
		statement.setString(EMAIL_DELETE_QUERY_INDEX, user.getEmail());
		return statement;
	}

	private PreparedStatement prepareUpdateStatement(Connection connection, User user) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
		statement.setString(UPDATE_QUERY_PASSWORD_INDEX, user.getPassword());
		statement.setString(UPDATE_QUERY_FIRST_NAME_INDEX, user.getFirstName());
		statement.setString(UPDATE_QUERY_LAST_NAME_INDEX, user.getLastName());
		statement.setInt(UPDATE_QUERY_ROLE_INDEX, user.getRole().getId());
		statement.setString(UPDATE_QUERY_CONDITION_EMAIL_INDEX, user.getEmail());
		return statement;
	}

	private PreparedStatement prepageSelectByEmailAndPasswordStatement(Connection connection, String email,
			String password) throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_EMAIL_AND_PASSWORD_QUERY);
		preparedStatement.setString(EMAIL_SELECT_BY_EMAIL_AND_PASSWORD_QUERY_INDEX, email);
		preparedStatement.setString(PASSWORD_SELECT_BY_EMAIL_AND_PASSWORD_QUERY_INDEX, password);
		return preparedStatement;
	}

	private PreparedStatement prepareCountEmailStatementStatement(Connection connection, String email)
			throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement(COUNT_EMAIL_QUERY);
		preparedStatement.setString(COUNT_EMAIL_QUERY_EMAIL_INDEX, email);
		return preparedStatement;
	}

	private User buildUser(ResultSet resultSet) throws SQLException {
		UserBuilder builder = new UserBuilder();
		builder.setUserRole(
				Role.valueOf(resultSet.getString(SELECT_BY_EMAIL_AND_PASSWORD_RESULT_ROLE_INDEX).toUpperCase()));
		builder.setUserEmail(resultSet.getString(SELECT_BY_EMAIL_AND_PASSWORD_RESULT_EMAIL_INDEX));
		builder.setUserPassword(resultSet.getString(SELECT_BY_EMAIL_AND_PASSWORD_RESULT_PASSWORD_INDEX));
		builder.setUserFirstName(resultSet.getString(SELECT_BY_EMAIL_AND_PASSWORD_RESULT_FIRST_NAME_INDEX));
		builder.setUserLastName(resultSet.getString(SELECT_BY_EMAIL_AND_PASSWORD_RESULT_LAST_NAME_INDEX));
		return builder.getUser();
	}
}
