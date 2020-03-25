package by.training.karpilovich.lowcost.factory;

import by.training.karpilovich.lowcost.service.InitializatorService;
import by.training.karpilovich.lowcost.service.impl.InitializatorServiceImpl;

public class ServiceFactory {
	
	private ServiceFactory() {}
	
	private static final class ServiceFactoryInstanceHolder {
		private static final ServiceFactory INSTANCE = new ServiceFactory();
	}
	
	public static ServiceFactory getInstance() {
		return ServiceFactoryInstanceHolder.INSTANCE;
	}
	
	public InitializatorService getInitializatorService() {
		return InitializatorServiceImpl.getInstance();
	}

}
