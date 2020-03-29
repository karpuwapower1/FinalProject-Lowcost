package by.training.karpilovich.lowcost.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.builder.CityBuilder;
import by.training.karpilovich.lowcost.connection.ConnectionPool;
import by.training.karpilovich.lowcost.dao.CityDAO;
import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.exception.ConnectionPoolException;
import by.training.karpilovich.lowcost.exception.DAOException;
import by.training.karpilovich.lowcost.util.MessageType;

public class CityDAOImpl implements CityDAO {

	private static final String SELECT_ALL_CITIES_QUERY = "SELECT id, name, country_name FROM city";

	private static final int RESULT_SELECT_ALL_CITIES_QUERY_ID_INDEX = 1;
	private static final int RESULT_SELECT_ALL_CITIES_QUERY_NAME_INDEX = 2;
	private static final int RESULT_SELECT_ALL_CITIES_QUERY_COUNTRY_NAME_INDEX = 3;

	private static final Logger LOGGER = LogManager.getLogger(CityDAOImpl.class);

	private CityDAOImpl() {

	}

	private static final class CityDAOImplInstanceHolder {
		private static final CityDAOImpl INSTANCE = new CityDAOImpl();
	}

	public static CityDAOImpl getInstance() {
		return CityDAOImplInstanceHolder.INSTANCE;
	}

	@Override
	public boolean addCity(City city) throws DAOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateCity(City city) throws DAOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteCity(City city) throws DAOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<City> getAllCities() throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = connection.prepareStatement(SELECT_ALL_CITIES_QUERY);
				ResultSet resultSet = statement.executeQuery()) {
			List<City> cities = new ArrayList<>();
			while (resultSet.next()) {
				cities.add(buildCity(resultSet));
			}
			return cities;
		} catch (ConnectionPoolException | SQLException e) {
			LOGGER.error("Error while getting all cities");
			throw new DAOException(MessageType.INTERNAL_ERROR.getType());
		}

	}

	private City buildCity(ResultSet resultSet) throws SQLException {
		CityBuilder builder = new CityBuilder();
		builder.setCityId(resultSet.getInt(RESULT_SELECT_ALL_CITIES_QUERY_ID_INDEX));
		builder.setCityName(resultSet.getString(RESULT_SELECT_ALL_CITIES_QUERY_NAME_INDEX));
		builder.setCityCountry(resultSet.getString(RESULT_SELECT_ALL_CITIES_QUERY_COUNTRY_NAME_INDEX));
		return builder.getCity();
	}

}
