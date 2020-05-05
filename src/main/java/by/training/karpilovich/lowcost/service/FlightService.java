package by.training.karpilovich.lowcost.service;

import java.util.Calendar;
import java.util.List;

import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.entity.Plane;
import by.training.karpilovich.lowcost.exception.ServiceException;

/**
 * Describes a behavior of {@link Flight} entity
 * 
 * @author Aliaksei Karpilovich
 */
public interface FlightService {

	/**
	 * Creates flight from input parameters and returns it. Throws ServiceExcetpion
	 * if <tt>from</tt>, to or <tt>plane</tt> is null or if <tt>number</tt> is null
	 * or empty or if <tt>price</tt> or <tt>primaryBoardingPrice</tt> or
	 * <tt>priceForKgOverweight</tt> or <tt>permittedLuggage</tt> is less than 0 or
	 * an exception occurs while parsing them or if <tt>date</tt> is less than today
	 * or an exception occurs while parsing it or if data source already contains
	 * flight with <tt>number</tt> and <tt>date</tt>.
	 * 
	 * @param number               the flight's number
	 * @param from                 the departure City
	 * @param to                   the destination City
	 * @param date                 the departure date
	 * @param defaultPrice         the price without coefficients' affection
	 * @param primaryBoardingPrice the price for primary boarding right
	 * @param plane                the plane model
	 * @param permittedLuggage     luggage quantity that is included into the ticket
	 *                             price
	 * @param priceForKgOverweight price for every weight kg over than included
	 *                             weight
	 * @return created from parameters flight
	 * @throws ServiceException if <tt>from</tt>, to or <tt>plane</tt> is null or if
	 *                          <tt>number</tt> is null or empty or if
	 *                          <tt>price</tt> or <tt>primaryBoardingPrice</tt> or
	 *                          <tt>priceForKgOverweight</tt> or
	 *                          <tt>permittedLuggage</tt> is less than 0 or an
	 *                          exception occurs while parsing them or if
	 *                          <tt>date</tt> is less than today or an exception
	 *                          occurs while parsing it or if data source already
	 *                          contains flight with <tt>number</tt> and
	 *                          <tt>date</tt>.
	 */
	Flight createFlight(String number, City from, City to, String date, String defaultPrice,
			String primaryBoardingPrice, Plane plane, String permittedLuggage, String priceForKgOverweight)
			throws ServiceException;

	/**
	 * Adds the <tt>flight</tt> into data source Throws ServiceException if
	 * <tt>flight</tt> is null or flight's number is null or empty or if the
	 * flight's date is less than today or if flight with these number and date has
	 * already been added into data source or if price or flight's
	 * priceForKgOverweight or primaryBoardingPrice or permittedLuggage is less than
	 * 0 or if flight's from or to or plane is null.
	 * 
	 * @param flight that is adding into data source.
	 * @throws ServiceException if <tt>flight</tt> is null or flight's number is
	 *                          null or empty or if the flight's date is less than
	 *                          today or if flight with these number and date has
	 *                          already been added into data source or if price or
	 *                          flight's priceForKgOverweight or
	 *                          primaryBoardingPrice or permittedLuggage is less
	 *                          than 0 or if flight's from or to or plane is null or
	 *                          if an error occurs while adding flight into data
	 *                          source
	 */
	void addFlight(Flight flight) throws ServiceException;

	/**
	 * Removes flight with <tt>flightId</tt> from data source. Throws
	 * ServiceException if an exception occurs while parsing <tt>flightId</tt> or if
	 * data source does not contains a flight <tt>flightId</tt>.
	 * 
	 * @param flightId the id of removing flight
	 * @throws ServiceException if an exception occurs while parsing
	 *                          <tt>flightId</tt> or if data source does not
	 *                          contains a flight <tt>flightId</tt> or an error
	 *                          occurs while removing a flight from data source
	 */
	void removeFlightAndReturnAllPurchasedTickets(String flightId) throws ServiceException;

	/**
	 * Update flight with <tt>flightId</tt> using passing parameters. Throws
	 * ServiceExcetpion if <tt>from</tt> or <tt>to</tt> or <tt>plane</tt> is null or
	 * if <tt>number</tt> is null or empty or if <tt>price</tt> or
	 * <tt>primaryBoardingPrice</tt> or <tt>priceForKgOverweight</tt> or
	 * <tt>permittedLuggage</tt> is less than 0 or an exception occurs while parsing
	 * them or if <tt>date</tt> is less than today or an exception occurs while
	 * parsing it or if data source already contains flight with <tt>number</tt> and
	 * <tt>date</tt>.
	 * 
	 * @param flightId             the updating flight's id
	 * @param number               the flight's number
	 * @param from                 the departure City
	 * @param to                   the destination City
	 * @param date                 the departure date
	 * @param defaultPrice         the price without coefficients' affection
	 * @param primaryBoardingPrice the price for primary boarding right
	 * @param plane                the plane model
	 * @param permittedLuggage     luggage quantity that is included into the ticket
	 *                             price
	 * @param priceForKgOverweight price for every weight kg over than included
	 *                             weight
	 * @return created from parameters flight
	 * 
	 * @throws ServiceException if from, to or plane is null or if number is null or
	 *                          empty or if price or primaryBoardingPrice or
	 *                          priceForKgOverweight or permittedLuggage is less
	 *                          than 0 or an exception occurs while parsing them or
	 *                          if date is less than today or an exception occurs
	 *                          while parsing it or if data source already contains
	 *                          flight with number and date as passing number and
	 *                          date or if an error occurs while updating flight
	 *                          into data source.
	 */
	void updateFlight(String flightId, String number, City from, City to, String date, String defaultPrice,
			String primaryBoardingPrice, Plane plane, String permittedLuggage, String priceForKgOverweight)
			throws ServiceException;

	/**
	 * Search and return flights with specified parameters. If no flights was found
	 * return an empty list. Throws ServiceException if <tt>from</tt> or <tt>to</tt>
	 * is null or <tt>date</tt> is less than today or an exception occurs while
	 * parsing it or if <tt>passegerQuantity</tt> is less than or equals 0.
	 * 
	 * @param from              flight's departure city
	 * @param to                flight's destination city
	 * @param date              flight's departure date
	 * @param passengerQuantity least empty places that searching flight must
	 *                          contains
	 * @return list of flights with specified parameters or empty list if no flights
	 *         was found.
	 * @throws ServiceException if <tt>from</tt> or <tt>to</tt> is null or
	 *                          <tt>date</tt> is less than today or an exception
	 *                          occurs while parsing it or if
	 *                          <tt>passegerQuantity</tt> is less than or equals 0
	 *                          or an error occurs while searching flights into data
	 *                          source
	 */
	List<Flight> searchFlights(City from, City to, String date, String passengerQuantity) throws ServiceException;

	/**
	 * Counts and returns quantity of flights with <tt>number</tt> and
	 * <tt>date</tt>. Throws ServiceException if <tt>number</tt> is null or empty or
	 * if <tt>date</tt> is less than today or an error occurs while parsing it or an
	 * error occurs into data source.
	 * 
	 * @param number is searching flight number
	 * @param date   searching flight departure date
	 * @return flight's with these <tt>number</tt> and <tt>date</tt> quantity
	 * @throws ServiceException if <tt>number</tt> is null or empty or if
	 *                          <tt>date</tt> is less than today or an error occurs
	 *                          while parsing it or an error occurs into data
	 *                          source.
	 */
	int getFlightCountWithNumberAndDate(String number, Calendar date) throws ServiceException;

	/**
	 * Gets and returns all flights from data source. If no flight was found returns
	 * an empty list.
	 * 
	 * @return list contains all flights from data source or an empty list if data
	 *         source contains to flight.
	 * @throws ServiceException if an exception occurs while getting flights from a
	 *                          data source
	 */
	List<Flight> getAllFlights() throws ServiceException;

	/**
	 * Finds and return flight with <tt>id</tt>. Throws an exception if no flight
	 * was found or if an exception occurs while parsing an <tt>id</tt> or if an
	 * error occurs in a data source
	 * 
	 * @param id searching flight's id
	 * @return flight with <tt>id</tt>
	 * @throws ServiceException if no flight was found or if an exception occurs
	 *                          while parsing an <tt>id</tt> or if an error occurs
	 *                          in a data source
	 */
	Flight getFlightById(String id) throws ServiceException;

	/**
	 * Sorts list of flights by price (excluding luggage price).
	 * 
	 * @param flights list that must be sort
	 * @throws ServiceException if <tt>flights</tt> equals null or empty
	 */
	void sortFlightByTicketPrice(List<Flight> flights) throws ServiceException;

	/**
	 * Sorts list of flights by departure date.
	 * 
	 * @param flights list that must be sort
	 * @throws ServiceException if <tt>flights</tt> equals null or empty
	 */
	void sortFlightByDepartureDate(List<Flight> flights) throws ServiceException;

	/**
	 * Searches flights that <tt>flight.getDepartureDate()</tt> is between
	 * <tt>dateFrom</tt> and <tt>dateTo</tt> include. Returns list of found flights
	 * or empty list if no flight was found. Throws ServiceException if
	 * <tt>fromDate</tt> is greater than <tt>toDate</tt> or if an error occurs in
	 * data source.
	 * 
	 * @param dateFrom the minimum <tt>flight.getDepartureDate()</tt> value
	 * @param dateTo   the maximum <tt>flight.getDepartureDate()</tt> value
	 * @return list of found flights or empty list if no flight was found.
	 * @throws ServiceException if <tt>fromDate</tt> is greater than <tt>toDate</tt>
	 *                          or if an error occurs in data source.
	 */
	List<Flight> searchFlightsBetweenDates(String dateFrom, String dateTo) throws ServiceException;

	/**
	 * Searches flights that <tt>flight.getDepartureDate()</tt> is in next 24 hours.
	 * Returns list of found flights or empty list if no flight was found. Throws
	 * ServiceException if if an error occurs in data source.
	 * 
	 * @return list of found flights or empty list if no flight was found.
	 * @throws ServiceException if an error occurs in data source.
	 */
	List<Flight> searchNextTwentyForHoursFlights() throws ServiceException;
}