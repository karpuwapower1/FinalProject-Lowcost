package by.training.karpilovich.lowcost.service;

import java.util.Calendar;
import java.util.List;

import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.entity.Plane;
import by.training.karpilovich.lowcost.exception.ServiceException;

public interface FlightService {

	Flight createFlight(String number, City from, City to, String date, String defaultPrice,
			String primaryBoardingPrice, Plane plane, String permittedLuggage, String priceForKgOverweight)
			throws ServiceException;

	void addFlight(Flight flight) throws ServiceException;

	void removeFlightAndReturnAllPurchasedTickets(String flightId) throws ServiceException;

	void updateFlight(String flightId, String number, City from, City to, String date, String defaultPrice,
			String primaryBoardingPrice, Plane plane, String permittedLuggage, String priceForKgOverweight)
			throws ServiceException;

	List<Flight> getFlight(City from, City to, String date, String passengerQuantity)
			throws ServiceException;

	int getFlightCountWithNumberAndDate(String number, Calendar date) throws ServiceException;

	List<Flight> getAllFlights() throws ServiceException;

	Flight getFlightById(String id) throws ServiceException;

	void sortFlightByTicketPrice(List<Flight> flights) throws ServiceException;

	void sortFlightByDepartureDate(List<Flight> flights) throws ServiceException;
	
	List<Flight> getFlightsBetweenDates(String dateFrom, String dateTo) throws ServiceException;
	
	List<Flight> getNextTwentyForHoursFlights() throws ServiceException;

}