package by.training.karpilovich.lowcost.service;

import java.util.List;

import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.exception.ServiceException;

public interface FlightService {

	/*
	 * number, date, from, to, default_price, default_luggage_kg, available_places,
	 * model, place_quantity
	 */

	void addFlight(String number, String countryFrom, String countryTo, String date, String defaultPrice, String model)
			throws ServiceException;

	void removeFlight(String number, String date) throws ServiceException;

	List<Flight> getFlight(String countryFrom, String countryTo, String date, String passengerQuantity)
			throws ServiceException;

}
