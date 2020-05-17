package by.training.karpilovich.lowcost.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.connection.ConnectionPool;
import by.training.karpilovich.lowcost.dao.DateCoefficientDAO;
import by.training.karpilovich.lowcost.entity.DateCoefficient;
import by.training.karpilovich.lowcost.exception.ConnectionPoolException;
import by.training.karpilovich.lowcost.exception.DAOException;
import by.training.karpilovich.lowcost.util.message.MessageType;

public class DateCoefficientDAOImpl implements DateCoefficientDAO {
	
	private final ConnectionPool pool = ConnectionPool.getInstance();

	private static final String ADD_DATE_COEFFICIENT_QUERY = "INSERT INTO "
			+ " date_coeff (`flight_id`, `from`, `to`, `coeff`) " 
			+ " VALUES(?, ?, ?, ?)";

	private static final int ADD_COEFFICIENT_QUERY_FLIGHT_ID_INDEX = 1;
	private static final int ADD_COEFFICIENT_QUERY_BOUND_FROM_INDEX = 2;
	private static final int ADD_COEFFICIENT_QUERY_BOUND_TO_INDEX = 3;
	private static final int ADD_COEFFICIENT_QUERY_VALUE_INDEX = 4;

	private static final Logger LOGGER = LogManager.getLogger(DateCoefficientDAOImpl.class);

	public static DateCoefficientDAOImpl getInstance() {
		return DateCoefficientDAOImplInstanceHolder.INSTATNCE;
	}
	
	@Override
	public void addCoefficient(int flightId, DateCoefficient coefficient) throws DAOException {
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = connection.prepareStatement(ADD_DATE_COEFFICIENT_QUERY);) {
			prepareAddDateCoefficientStatement(statement, coefficient, flightId);
			statement.executeUpdate();
		} catch (SQLException | ConnectionPoolException e) {
			LOGGER.error("Error while adding coefficients ", e);
			throw new DAOException(MessageType.INTERNAL_ERROR.getMessage(), e);
		}
	}

	@Override
	public void addCoefficients(int flightId, Set<DateCoefficient> coefficients) throws DAOException {
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = connection.prepareStatement(ADD_DATE_COEFFICIENT_QUERY);) {
			prepareAddDateCoefficientStatement(statement, coefficients, flightId);
			statement.executeBatch();
		} catch (SQLException | ConnectionPoolException e) {
			LOGGER.error("Error while adding coefficients ", e);
			throw new DAOException(MessageType.INTERNAL_ERROR.getMessage(), e);
		}
	}
	
	private static final class DateCoefficientDAOImplInstanceHolder {
		private static final DateCoefficientDAOImpl INSTATNCE = new DateCoefficientDAOImpl();
	}

	private void prepareAddDateCoefficientStatement(PreparedStatement statement, Set<DateCoefficient> coefficients,
			int flightId) throws SQLException {
		for (DateCoefficient coefficient : coefficients) {
			prepareAddDateCoefficientStatement(statement, coefficient, flightId);
			statement.addBatch();
		}
	}

	private void prepareAddDateCoefficientStatement(PreparedStatement statement, DateCoefficient coefficient,
			int flightId) throws SQLException {
		statement.setInt(ADD_COEFFICIENT_QUERY_FLIGHT_ID_INDEX, flightId);
		statement.setTimestamp(ADD_COEFFICIENT_QUERY_BOUND_FROM_INDEX, new Timestamp(coefficient.getFrom().getTimeInMillis()));
		statement.setTimestamp(ADD_COEFFICIENT_QUERY_BOUND_TO_INDEX,new Timestamp(coefficient.getTo().getTimeInMillis()));
		statement.setBigDecimal(ADD_COEFFICIENT_QUERY_VALUE_INDEX, coefficient.getValue());
	}
}