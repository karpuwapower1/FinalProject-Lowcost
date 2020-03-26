package by.training.karpilovich.lowcost.service.impl;

import java.util.List;

import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.exception.ServiceException;
import by.training.karpilovich.lowcost.service.FlightService;

public class FlightServiceImpl implements FlightService {
	
	private FlightServiceImpl() {}

	private static final class FlightServiceInstanceHolder {
		private static final FlightServiceImpl INSTANCE = new FlightServiceImpl();
	}
	
	public static FlightService getInstance() {
		return FlightServiceInstanceHolder.INSTANCE;
	}

	@Override
	public void addFlight(String number, String countryFrom, String countryTo, String date, String defaultPrice,
			String model) throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeFlight(String number, String date) throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Flight> getFlight(String countryFrom, String countryTo, String date, String passengerQuantity)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
