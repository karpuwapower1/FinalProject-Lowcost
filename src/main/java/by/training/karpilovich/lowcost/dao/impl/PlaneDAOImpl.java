package by.training.karpilovich.lowcost.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import by.training.karpilovich.lowcost.builder.PlaneBuilder;
import by.training.karpilovich.lowcost.connection.ConnectionPool;
import by.training.karpilovich.lowcost.dao.PlaneDAO;
import by.training.karpilovich.lowcost.entity.Plane;
import by.training.karpilovich.lowcost.exception.ConnectionPoolException;
import by.training.karpilovich.lowcost.exception.DAOException;
import by.training.karpilovich.lowcost.util.MessageType;

public class PlaneDAOImpl implements PlaneDAO {

	private static final String ADD_PLANE_QUERY = "INSERT INTO " + " plane(model, places_quantity)  " + " VALUES(?,?)";

	private static final int ADD_QUERY_MODEL_INDEX = 1;
	private static final int ADD_QUERY_PLACE_QUANTITY_INDEX = 2;

	private static final String UPDATE_PLANE_QUERY = "UPDATE plane " + " SET model=?, places_quantity=? "
			+ " WHERE model=?";

	private static final int UPDATE_QUERY_NEW_MODEL_INDEX = 1;
	private static final int UPDATE_QUERY_NEW_PLACE_QUANTITY_INDEX = 2;
	private static final int UPDATE_QUERY_OLD_MODEL_INDEX = 3;

	private static final String DELETE_PLANE_QUERY = "DELETE FROM plane " + "	WHERE model=?";

	private static final int DELETE_QUERY_MODEL_INDEX = 1;

	private static final String SELECT_ALL_PLANES_QUERY = "SELECT model, places_quantity " + " FROM plane ";

	private static final int RESULT_SELECT_ALL_PLANES_QUERY_MODEL_INDEX = 1;
	private static final int RESULT_SELECT_ALL_PLANES_QUERY_PLACE_QUANTITY_INDEX = 2;

	private static final String SELECT_PLANE_BY_MODEL_QUERY = "SELECT model, places_quantity " + " FROM plane "
			+ " WHERE model=?";

	private static final int SELECT_PLANE_BY_MODEL_QUERY_MODEL_INDEX = 1;

	private static final int RESULT_SELECT_PLANE_BY_MODEL_QUERY_MODEL_INDEX = 1;
	private static final int RESULT_SELECT_PLANE_BY_MODEL_PLACE_QUERY_QUANTITY_INDEX = 2;

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
		ConnectionPool pool = ConnectionPool.getInstance();
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = connection.prepareStatement(ADD_PLANE_QUERY);) {
			prepareAddStatement(statement, plane);
			statement.executeUpdate();
		} catch (ConnectionPoolException | SQLException e) {
			throw new DAOException(MessageType.INTERNAL_ERROR.getMessage());
		}
	}

	@Override
	public void updatePlane(Plane plane, Plane update) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_PLANE_QUERY);) {
			prepareUpdateStatement(statement, plane, update);
			statement.executeUpdate();
		} catch (ConnectionPoolException | SQLException e) {
			throw new DAOException(MessageType.INTERNAL_ERROR.getMessage());
		}
	}

	@Override
	public void deletePlane(Plane plane) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_PLANE_QUERY);) {
			prepareDeleteStatement(statement, plane);
			statement.executeUpdate();
		} catch (ConnectionPoolException | SQLException e) {
			throw new DAOException(MessageType.INTERNAL_ERROR.getMessage());
		}
	}

	@Override
	public List<Plane> getAllPlanes() throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = connection.prepareStatement(SELECT_ALL_PLANES_QUERY);
				ResultSet resultSet = statement.executeQuery();) {
			List<Plane> planes = new ArrayList<>();
			while (resultSet.next()) {
				planes.add(buildPlaneFromSelectAllPlanesResultSet(resultSet));
			}
			return planes;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DAOException(MessageType.INTERNAL_ERROR.getMessage());
		}
	}

	@Override
	public Optional<Plane> getPlaneByModelName(String model) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = connection.prepareStatement(SELECT_PLANE_BY_MODEL_QUERY);) {
			prepareSelectPlaneByModelStatement(statement, model);
			try (ResultSet resultSet = statement.executeQuery();) {
				Optional<Plane> plane = Optional.empty();
				if (resultSet.next()) {
					plane = Optional.of(buildPlaneFromSelectPlanByModelResultSet(resultSet));
				}
				return plane;
			}
		} catch (ConnectionPoolException | SQLException e) {
			throw new DAOException(MessageType.INTERNAL_ERROR.getMessage());
		}
	}

	private void prepareAddStatement(PreparedStatement statement, Plane plane) throws SQLException {
		statement.setString(ADD_QUERY_MODEL_INDEX, plane.getModel());
		statement.setInt(ADD_QUERY_PLACE_QUANTITY_INDEX, plane.getPlaceQuantity());
	}

	private void prepareUpdateStatement(PreparedStatement statement, Plane plane, Plane update) throws SQLException {
		statement.setString(UPDATE_QUERY_NEW_MODEL_INDEX, update.getModel());
		statement.setInt(UPDATE_QUERY_NEW_PLACE_QUANTITY_INDEX, update.getPlaceQuantity());
		statement.setString(UPDATE_QUERY_OLD_MODEL_INDEX, plane.getModel());
	}

	private void prepareDeleteStatement(PreparedStatement statement, Plane plane) throws SQLException {
		statement.setString(DELETE_QUERY_MODEL_INDEX, plane.getModel());
	}

	private void prepareSelectPlaneByModelStatement(PreparedStatement statement, String model) throws SQLException {
		statement.setString(SELECT_PLANE_BY_MODEL_QUERY_MODEL_INDEX, model);
	}

	private Plane buildPlaneFromSelectAllPlanesResultSet(ResultSet resultSet) throws SQLException {
		String model = resultSet.getString(RESULT_SELECT_ALL_PLANES_QUERY_MODEL_INDEX);
		int placeQuantity = resultSet.getInt(RESULT_SELECT_ALL_PLANES_QUERY_PLACE_QUANTITY_INDEX);
		return buildPlane(model, placeQuantity);
	}

	private Plane buildPlaneFromSelectPlanByModelResultSet(ResultSet resultSet) throws SQLException {
		String model = resultSet.getString(RESULT_SELECT_PLANE_BY_MODEL_QUERY_MODEL_INDEX);
		int placeQuantity = resultSet.getInt(RESULT_SELECT_PLANE_BY_MODEL_PLACE_QUERY_QUANTITY_INDEX);
		return buildPlane(model, placeQuantity);
	}

	private Plane buildPlane(String model, int placeQuantity) {
		PlaneBuilder builder = new PlaneBuilder();
		builder.setPlaneModel(model);
		builder.setPlanePlaceQuantity(placeQuantity);
		return builder.getPlane();
	}
}