package by.training.karpilovich.lowcost.factory;

import by.training.karpilovich.lowcost.service.UserService;
import by.training.karpilovich.lowcost.service.impl.UserServiceImpl;

public class ServiceFactory {
	
	private ServiceFactory() {}
	
	private static final class ServiceFactoryInstanceHolder {
		private static final ServiceFactory INSTANCE = new ServiceFactory();
	}
	
	public static ServiceFactory getInstance() {
		return ServiceFactoryInstanceHolder.INSTANCE;
	}
	
	public UserService getUserService() {
		return UserServiceImpl.getInstance();
	}

}
