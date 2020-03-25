package by.training.karpilovich.lowcost.factory;

import by.training.karpilovich.lowcost.dao.UserDao;
import by.training.karpilovich.lowcost.dao.impl.MySqlUserDao;

public class DaoFactory {
	
	private DaoFactory() {}
	
	private static final class DaoFactoryInstanceHolder {
		private static final DaoFactory INSTANCE = new DaoFactory();
	}
	
	public static DaoFactory getInstance() {
		return DaoFactoryInstanceHolder.INSTANCE;
	}
	
	public UserDao getUserDao() {
		return MySqlUserDao.getInstance();
	}

}
