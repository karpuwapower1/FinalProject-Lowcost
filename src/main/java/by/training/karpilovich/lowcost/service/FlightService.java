package by.training.karpilovich.lowcost.service;

import java.util.Set;

import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.exception.ServiceException;

public interface FlightService {

	/*
	 * number, date, from, to, default_price, default_luggage_kg, available_places,
	 * model, place_quantity
	 */

	void addFlight(String number, String fromId, String toId, String date, String defaultPrice, String model, String permittedLuggage)
			throws ServiceException;
	
	void addCoefficient(Flight flight, String from, String to, String value);

	void removeFlight(String number, String date) throws ServiceException;

	Set<Flight> getFlight(String cityFrom, String cityTo, String date, String passengerQuantity)
			throws ServiceException;

}
