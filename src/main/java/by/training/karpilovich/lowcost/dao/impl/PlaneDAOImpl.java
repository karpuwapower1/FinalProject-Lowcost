package by.training.karpilovich.lowcost.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.builder.PlaneBuilder;
import by.training.karpilovich.lowcost.connection.ConnectionPool;
import by.training.karpilovich.lowcost.dao.PlaneDAO;
import by.training.karpilovich.lowcost.entity.Plane;
import by.training.karpilovich.lowcost.exception.ConnectionPoolException;
import by.training.karpilovich.lowcost.exception.DAOException;
import by.training.karpilovich.lowcost.util.message.MessageType;

public class PlaneDAOImpl implements PlaneDAO {

	private static final String ADD_PLANE_QUERY = "INSERT INTO " + " plane(model, places_quantity)  " + " VALUES(?,?)";

	private static final int ADD_QUERY_MODEL_INDEX = 1;
	private static final int ADD_QUERY_PLACE_QUANTITY_INDEX = 2;

	private static final String DELETE_PLANE_QUERY = "DELETE FROM plane " + "	WHERE model=?";

	private static final int DELETE_QUERY_MODEL_INDEX = 1;

	private static final String SELECT_ALL_PLANES_QUERY = "SELECT model, places_quantity " + " FROM plane ";

	private static final String SELECT_PLANE_BY_MODEL_QUERY = "SELECT model, places_quantity " + " FROM plane "
			+ " WHERE model=?";

	private static final int SELECT_PLANE_BY_MODEL_QUERY_MODEL_INDEX = 1;

	private static final String RESULT_SELECT_PLANE_BY_MODEL_MODEL = "model";
	private static final String RESULT_SELECT_PLANE_BY_MODEL_PLACE_QUANTITY = "places_quantity";
	
	private static final Logger LOGGER = LogManager.getLogger(PlaneDAOImpl.class);
	
	private ConnectionPool pool = ConnectionPool.getInstance();

	private PlaneDAOImpl() {
	}

	private static final class PlaneDAOImplInstanceHolder {
		private static final PlaneDAOImpl INSTANCE = new PlaneDAOImpl();
	}

	public static PlaneDAOImpl getInstance() {
		return PlaneDAOImplInstanceHolder.INSTANCE;
	}

	@Override
	public void addPlane(Plane plane) throws DAOException {
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = connection.prepareStatement(ADD_PLANE_QUERY);) {
			prepareAddStatement(statement, plane);
			statement.executeUpdate();
		} catch (ConnectionPoolException | SQLException e) {
			LOGGER.error("Error while adding a plane=" + plane, e);
			throw new DAOException(MessageType.INTERNAL_ERROR.getMessage(), e);
		}
	}

	@Override
	public void deletePlane(Plane plane) throws DAOException {
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_PLANE_QUERY);) {
			prepareDeleteStatement(statement, plane);
			statement.executeUpdate();
		} catch (ConnectionPoolException | SQLException e) {
			LOGGER.error("Error while deleting a plane=" + plane, e);
			throw new DAOException(MessageType.INTERNAL_ERROR.getMessage(), e);
		}
	}

	@Override
	public List<Plane> getAllPlanes() throws DAOException {
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = connection.prepareStatement(SELECT_ALL_PLANES_QUERY);
				ResultSet resultSet = statement.executeQuery();) {
			List<Plane> planes = new ArrayList<>();
			while (resultSet.next()) {
				planes.add(buildPlane(resultSet));
			}
			return planes;
		} catch (ConnectionPoolException | SQLException e) {
			LOGGER.error("Error while getting all planes", e);
			throw new DAOException(MessageType.INTERNAL_ERROR.getMessage(), e);
		}
	}

	@Override
	public Optional<Plane> getPlaneByModelName(String model) throws DAOException {
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = connection.prepareStatement(SELECT_PLANE_BY_MODEL_QUERY);) {
			prepareSelectPlaneByModelStatement(statement, model);
			try (ResultSet resultSet = statement.executeQuery();) {
				Optional<Plane> plane = Optional.empty();
				if (resultSet.next()) {
					plane = Optional.of(buildPlane(resultSet));
				}
				return plane;
			}
		} catch (ConnectionPoolException | SQLException e) {
			LOGGER.error("Error while getting plane by molel=" + model, e);
			throw new DAOException(MessageType.INTERNAL_ERROR.getMessage(), e);
		}
	}

	private void prepareAddStatement(PreparedStatement statement, Plane plane) throws SQLException {
		statement.setString(ADD_QUERY_MODEL_INDEX, plane.getModel());
		statement.setInt(ADD_QUERY_PLACE_QUANTITY_INDEX, plane.getPlaceQuantity());
	}

	private void prepareDeleteStatement(PreparedStatement statement, Plane plane) throws SQLException {
		statement.setString(DELETE_QUERY_MODEL_INDEX, plane.getModel());
	}

	private void prepareSelectPlaneByModelStatement(PreparedStatement statement, String model) throws SQLException {
		statement.setString(SELECT_PLANE_BY_MODEL_QUERY_MODEL_INDEX, model);
	}

	private Plane buildPlane(ResultSet resultSet) throws SQLException {
		PlaneBuilder builder = new PlaneBuilder();
		builder.setPlaneModel(resultSet.getString(RESULT_SELECT_PLANE_BY_MODEL_MODEL));
		builder.setPlanePlaceQuantity(resultSet.getInt(RESULT_SELECT_PLANE_BY_MODEL_PLACE_QUANTITY));
		return builder.getPlane();
	}
}