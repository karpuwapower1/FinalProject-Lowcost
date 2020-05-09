package by.training.karpilovich.lowcost.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.builder.CityBuilder;
import by.training.karpilovich.lowcost.connection.ConnectionPool;
import by.training.karpilovich.lowcost.dao.CityDAO;
import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.exception.ConnectionPoolException;
import by.training.karpilovich.lowcost.exception.DAOException;
import by.training.karpilovich.lowcost.util.message.MessageType;

public class CityDAOImpl implements CityDAO {

	private static final String ADD_QUERY = "INSERT INTO city (name, country_name) VALUES (?,?)";

	private static final int ADD_QUERY_NAME_INDEX = 1;
	private static final int ADD_QUERY_COUNTRY_NAME_INDEX = 2;

	private static final String UPDATE_QUERY = "UPDATE city SET name=?, country_name=? WHERE id=?";

	private static final int UPDATE_QUERY_NAME_INDEX = 1;
	private static final int UPDATE_QUERY_COUNTRY_NAME_INDEX = 2;
	private static final int UPDATE_QUERY_COUNTRY_ID_INDEX = 3;

	private static final String DELETE_QUERY = "DELETE FROM city WHERE id=?";

	private static final int DELETE_QUERY_ID_INDEX = 1;

	private static final String SELECT_ALL_CITIES_QUERY = " SELECT id, name, country_name " + " FROM city "
			+ " ORDER BY country_name, name";

	private static final int RESULT_SELECT_ALL_CITIES_QUERY_ID_INDEX = 1;
	private static final int RESULT_SELECT_ALL_CITIES_QUERY_NAME_INDEX = 2;
	private static final int RESULT_SELECT_ALL_CITIES_QUERY_COUNTRY_NAME_INDEX = 3;

	private static final int LAST_INSERTED_INDEX = 1;

	private static final Logger LOGGER = LogManager.getLogger(CityDAOImpl.class);

	private ConnectionPool pool = ConnectionPool.getInstance();

	private CityDAOImpl() {
	}

	private static final class CityDAOImplInstanceHolder {
		private static final CityDAOImpl INSTANCE = new CityDAOImpl();
	}

	public static CityDAOImpl getInstance() {
		return CityDAOImplInstanceHolder.INSTANCE;
	}

	@Override
	public void addCity(City city) throws DAOException {
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = connection.prepareStatement(ADD_QUERY,
						Statement.RETURN_GENERATED_KEYS);) {
			prepareAddStatement(statement, city);
			statement.executeUpdate();
			setIdToNewAddedCity(city, statement);
		} catch (SQLException | ConnectionPoolException e) {
			LOGGER.error("Error while adding a city " + city, e);
			throw new DAOException(MessageType.INTERNAL_ERROR.getMessage(), e);
		}
	}

	@Override
	public void updateCity(City city) throws DAOException {
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);) {
			prepareUpdateStatement(statement, city);
			statement.executeUpdate();
		} catch (SQLException | ConnectionPoolException e) {
			LOGGER.error("Error while updating a city " + city, e);
			throw new DAOException(MessageType.INTERNAL_ERROR.getMessage(), e);
		}
	}

	@Override
	public void deleteCity(City city) throws DAOException {
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);) {
			prepareDeleteStatement(statement, city);
			statement.executeUpdate();
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new DAOException(MessageType.CITY_CAN_NOT_BE_DELETED.getMessage(), e);
		} catch (SQLException | ConnectionPoolException e) {
			LOGGER.error("Error while updating a city" + city, e);
			throw new DAOException(MessageType.INTERNAL_ERROR.getMessage(), e);
		}
	}

	@Override
	public Set<City> getAllCities() throws DAOException {
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = connection.prepareStatement(SELECT_ALL_CITIES_QUERY);
				ResultSet resultSet = statement.executeQuery()) {
			return getCitiesFromResultSet(resultSet);
		} catch (ConnectionPoolException | SQLException e) {
			LOGGER.error("Error while getting all cities");
			throw new DAOException(MessageType.INTERNAL_ERROR.getMessage(), e);
		}
	}

	private void setIdToNewAddedCity(City city, Statement statement) throws SQLException {
		city.setId(getIdFromPreparedStatement(statement));
	}

	private int getIdFromPreparedStatement(Statement statement) throws SQLException {
		try (ResultSet resultSet = statement.getGeneratedKeys()) {
			resultSet.next();
			return resultSet.getInt(LAST_INSERTED_INDEX);
		}
	}

	private void prepareAddStatement(PreparedStatement statement, City city) throws SQLException {
		statement.setString(ADD_QUERY_NAME_INDEX, city.getName());
		statement.setString(ADD_QUERY_COUNTRY_NAME_INDEX, city.getCountry());
	}

	private void prepareUpdateStatement(PreparedStatement statement, City city) throws SQLException {
		statement.setString(UPDATE_QUERY_NAME_INDEX, city.getName());
		statement.setString(UPDATE_QUERY_COUNTRY_NAME_INDEX, city.getCountry());
		statement.setInt(UPDATE_QUERY_COUNTRY_ID_INDEX, city.getId());
	}

	private void prepareDeleteStatement(PreparedStatement statement, City city) throws SQLException {
		statement.setInt(DELETE_QUERY_ID_INDEX, city.getId());
	}

	private City buildCity(ResultSet resultSet) throws SQLException {
		CityBuilder builder = new CityBuilder();
		builder.setCityId(resultSet.getInt(RESULT_SELECT_ALL_CITIES_QUERY_ID_INDEX));
		builder.setCityName(resultSet.getString(RESULT_SELECT_ALL_CITIES_QUERY_NAME_INDEX));
		builder.setCityCountry(resultSet.getString(RESULT_SELECT_ALL_CITIES_QUERY_COUNTRY_NAME_INDEX));
		return builder.getCity();
	}

	private Set<City> getCitiesFromResultSet(ResultSet resultSet) throws SQLException {
		Set<City> cities = new HashSet<>();
		while (resultSet.next()) {
			cities.add(buildCity(resultSet));
		}
		return cities;
	}
}