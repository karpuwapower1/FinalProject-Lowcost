package by.training.karpilovich.lowcost.factory;

import by.training.karpilovich.lowcost.dao.UserDAO;
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

}
