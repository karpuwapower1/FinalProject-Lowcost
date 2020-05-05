package by.training.karpilovich.lowcost.dao;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.exception.DAOException;

/**
 * Interface describes the opportunity that data source provide to store and
 * restore {@link Flight} entity
 * 
 * @author Aliaksei Karpilovich
 *
 */
public interface FlightDAO {

	/**
	 * Adds a <tt>flight</tt> into data source. Throws DAOException if an error
	 * occurs while adding a <tt>flight</tt>
	 * 
	 * @param flight the {@link Flight} object that must be save into date source
	 * @throws DAOException if an error occurs while adding a <tt>flight</tt>
	 */
	void add(Flight flight) throws DAOException;

	/**
	 * Rewrites parameters of stored {@link Flight} than id equals to
	 * <tt>flight.getId</tt>. Throws DAOException if an error occurs while updating
	 * a <tt>flight</tt>
	 * 
	 * @param flight a new parameters that updates stored {@link Flight} with id [
	 *               <tt>flight.getId</tt>
	 * @throws DAOException if an error occurs while updating a <tt>flight</tt>
	 */
	void update(Flight flight) throws DAOException;

	/**
	 * Removes {@link Flight} object with <tt>id=flight.getId()</tt> from data
	 * source. If data source contains tickets to the <tt>flight</tt> they must
	 * become unavailable and amount of money? that user has spend to it/ must be
	 * return back to user. Throws DAOException if an error occurs while working
	 * with data source.
	 * 
	 * @param flight the {@link Flight} object which is removing from data source
	 * @throws DAOException if an error occurs while working with data source.
	 */
	void removeFlightAndReturnAllPurchasedTickets(Flight flight) throws DAOException;

	List<Flight> getFlightsByFromToDateAndPassengerQuantity(City from, City to, Calendar date, int quantity)
			throws DAOException;

	/**
	 * Counts and returns flights with number equals to <tt>number</tt> and
	 * departure date equals to <tt>date</tt> stored into data source quantity.
	 * Throws DAOexception if an error occurs while working with data source.
	 * 
	 * @param number the {@link Flight} number
	 * @param date   the {@link Flight} date
	 * @return quantity of lights with number equals to <tt>number</tt> and
	 *         departure date equals to <tt>date</tt> stored into data source
	 * @throws DAOException if an error occurs while working with data source.
	 */
	int countFlightWithNumberAndDate(String number, Calendar date) throws DAOException;

	/**
	 * Retrieves and returns all the {@link Flight} objects that data source
	 * contains. If no {@link Flight} was found an empty list is returning. Throws
	 * DAOException if an error occurs while working with data source
	 * 
	 * @return all the {@link Flight} objects that data source contains. If no
	 *         {@link Flight} was found an empty list is returning
	 * @throws DAOException if an error occurs while working with data source
	 */
	List<Flight> getAllFlights() throws DAOException;

	/**
	 * Searches and returns {@link Flight} object with
	 * <tt>flight.getId()=flightId</tt>. If no such {@link Flight} was found return
	 * an <tt>Optional.empty()</tt>. Throws DAOException if an error occurs while
	 * working with a data source
	 * 
	 * @param flightId the id of the {@link Flight} that is looking for
	 * @return{@link Optional}<{@link Flight}> object with
	 *               <tt>flight.getId()=flightId</tt>. If no such {@link Flight} was
	 *               found return an <tt>Optional.empty()</tt>.
	 * @throws DAOException if an error occurs while working with a data source
	 */
	Optional<Flight> getFlightById(int flightId) throws DAOException;

	/**
	 * Searches and returns {@link Flight} objects with
	 * <tt>from <= flight.getDate() <= to</tt>. If no such {@link Flight} was found
	 * return an empty list. Throws DAOException if an error occurs while working
	 * with a data source
	 * 
	 * @param from the minimum {@link Flight} departure date value
	 * @param to   the maximum {@link Flight} departure date value
	 * @return {@link Flight}s with <tt>from <= flight.getDate() <= to</tt> or an
	 *         empty list if no such {@link Flight} was found
	 * @throws DAOException if an error occurs while working with a data source
	 */
	List<Flight> getFlightsBetweenDates(Calendar from, Calendar to) throws DAOException;
}