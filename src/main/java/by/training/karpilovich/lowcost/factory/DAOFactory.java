package by.training.karpilovich.lowcost.factory;

import by.training.karpilovich.lowcost.dao.CityDAO;
import by.training.karpilovich.lowcost.dao.FlightDAO;
import by.training.karpilovich.lowcost.dao.UserDAO;
import by.training.karpilovich.lowcost.dao.impl.CityDAOImpl;
import by.training.karpilovich.lowcost.dao.impl.FlightDAOImpl;
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

}
