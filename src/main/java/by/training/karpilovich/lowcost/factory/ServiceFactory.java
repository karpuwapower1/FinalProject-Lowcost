package by.training.karpilovich.lowcost.factory;

import by.training.karpilovich.lowcost.service.CityService;
import by.training.karpilovich.lowcost.service.CoefficientService;
import by.training.karpilovich.lowcost.service.FlightService;
import by.training.karpilovich.lowcost.service.UserService;
import by.training.karpilovich.lowcost.service.impl.CityServiceImpl;
import by.training.karpilovich.lowcost.service.impl.CoefficientServiceImpl;
import by.training.karpilovich.lowcost.service.impl.FlightServiceImpl;
import by.training.karpilovich.lowcost.service.impl.PlaneServiceImpl;
import by.training.karpilovich.lowcost.service.impl.UserServiceImpl;

public class ServiceFactory {

	private ServiceFactory() {
	}

	private static final class ServiceFactoryInstanceHolder {
		private static final ServiceFactory INSTANCE = new ServiceFactory();
	}

	public static ServiceFactory getInstance() {
		return ServiceFactoryInstanceHolder.INSTANCE;
	}

	public UserService getUserService() {
		return UserServiceImpl.getInstance();
	}
	
	public FlightService getFlightService() {
		return FlightServiceImpl.getInstance();
	}
	
	public CityService getCityService() {
		return CityServiceImpl.getInstance();
	}
	
	public PlaneServiceImpl getPlaneService() {
		return PlaneServiceImpl.getInstance();
	}
	
	public CoefficientService getCoefficientService() {
		return CoefficientServiceImpl.getInstance();
	}

}
