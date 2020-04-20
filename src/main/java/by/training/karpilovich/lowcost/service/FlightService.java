package by.training.karpilovich.lowcost.service;

import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import by.training.karpilovich.lowcost.entity.DateCoefficient;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.entity.PlaceCoefficient;
import by.training.karpilovich.lowcost.exception.ServiceException;

public interface FlightService {

	void addFlight(Flight flight, SortedSet<PlaceCoefficient> placeCoefficients, SortedSet<DateCoefficient> dateCoefficients) throws ServiceException;
	
	void removeFlight(String number, String date) throws ServiceException;

	Set<Flight> getFlight(String cityFrom, String cityTo, String date, String passengerQuantity)
			throws ServiceException;
	
	int getFlightCountWithNumberAndDate(String number, Calendar date) throws ServiceException;
	
	List<Flight> getAllFlights() throws ServiceException;
	
	Flight getFlightById(String id) throws ServiceException;

}