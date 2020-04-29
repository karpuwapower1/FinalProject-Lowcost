package by.training.karpilovich.lowcost.factory;

import by.training.karpilovich.lowcost.dao.CityDAO;
import by.training.karpilovich.lowcost.dao.DateCoefficientDAO;
import by.training.karpilovich.lowcost.dao.FlightDAO;
import by.training.karpilovich.lowcost.dao.PlaceCoefficientDAO;
import by.training.karpilovich.lowcost.dao.PlaneDAO;
import by.training.karpilovich.lowcost.dao.TicketDAO;
import by.training.karpilovich.lowcost.dao.UserDAO;
import by.training.karpilovich.lowcost.dao.impl.CityDAOImpl;
import by.training.karpilovich.lowcost.dao.impl.DateCoefficientDAOImpl;
import by.training.karpilovich.lowcost.dao.impl.FlightDAOImpl;
import by.training.karpilovich.lowcost.dao.impl.PlaceCoefficientDAOImpl;
import by.training.karpilovich.lowcost.dao.impl.PlaneDAOImpl;
import by.training.karpilovich.lowcost.dao.impl.TicketDAOImpl;
import by.training.karpilovich.lowcost.dao.impl.UserDAOImpl;

public class DAOFactory {
	
	private DAOFactory() {}
	
	private static final class DAOFactoryInstanceHolder {
		private static final DAOFactory INSTANCE = new DAOFactory();
	}
	
	public static DAOFactory getInstance() {
		return DAOFactoryInstanceHolder.INSTANCE;
	}
	
	public UserDAO getUserDAO() {
		return UserDAOImpl.getInstance();
	}
	
	public CityDAO getCityDAO() {
		return CityDAOImpl.getInstance();
	}

	public FlightDAO getFlightDAO() {
		return FlightDAOImpl.getInstance();
	}
	
	public PlaneDAO getPlaneDAO() {
		return PlaneDAOImpl.getInstance();
	}
	
	public TicketDAO getTicketDAO() {
		return TicketDAOImpl.getInstance();
	}
	
	public PlaceCoefficientDAO getPlaceCoefficientDAO() {
		return PlaceCoefficientDAOImpl.getInstance();
	}
	
	public DateCoefficientDAO getDateCoefficientDAO() {
		return DateCoefficientDAOImpl.getInstance();
	}
}