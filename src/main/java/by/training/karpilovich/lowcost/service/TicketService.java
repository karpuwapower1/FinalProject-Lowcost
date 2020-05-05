package by.training.karpilovich.lowcost.service;

import java.math.BigDecimal;
import java.util.List;

import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.entity.Ticket;
import by.training.karpilovich.lowcost.entity.User;
import by.training.karpilovich.lowcost.exception.ServiceException;

/**
 * Describes the behavior of {@link Ticket} entity.
 * 
 * @author Aliaksei Karpilovich
 *
 */
public interface TicketService {

	/**
	 * Books <tt>passengerQuantity</tt> places to <tt>flight</tt>. Throws
	 * ServiceException if <tt>flight</tt> is null or if <tt>passengerQuantity</tt>
	 * is greater than available places to <tt>flight</tt> left.
	 * 
	 * @param flight            a {@link Flight} tickets books to
	 * @param passengerQuantity desirable free {@link Flight} available places
	 *                          quantity
	 * @throws ServiceException if <tt>flight</tt> is null or if
	 *                          <tt>passengerQuantity</tt> is greater than available
	 *                          places to <tt>flight</tt> left.
	 */
	void bookTicketToFlight(Flight flight, int passengerQuantity) throws ServiceException;

	/**
	 * Creates and returns {@link Ticket} from parameters. Throws ServiceException
	 * if <tt>user</tt> or <tt>flight</tt> is null or if <tt>firstName</tt> or
	 * <tt>lastName</tt> is null or empty or if <tt>passportNumber</tt> or if it do
	 * not accords to ([A-Z]{2}[0-9]{7}) REGEX or if <tt>luggageQuantity</tt> is not
	 * null and not empty and an exception occurs while parsing it.
	 * 
	 * @param user                 buyer of the ticket
	 * @param flight               flight for which ticket is buying
	 * @param firstName            passenger's first name
	 * @param lastName             passenger's last name
	 * @param passportNumber       passengers passport number
	 * @param luggageQuantity      passenger's luggage weight in kg
	 * @param primaryBoardingRight primary boarding right into flight
	 * @return {@link Ticket} created from parameters
	 * @throws ServiceException if <tt>user</tt> or <tt>flight</tt> is null or if
	 *                          <tt>firstName</tt> or <tt>lastName</tt> is null or
	 *                          empty or if <tt>passportNumber</tt> or if it do not
	 *                          accords to ([A-Z]{2}[0-9]{7}) REGEX or if
	 *                          <tt>luggageQuantity</tt> is not null and not empty
	 *                          and an exception occurs while parsing it.
	 */
	Ticket createTicket(User user, Flight flight, String firstName, String lastName, String passportNumber,
			String luggageQuantity, String primaryBoardingRight) throws ServiceException;

	/**
	 * Makes a purchase of <tt>tickets</tt> and if purchase has success adds tickets
	 * into data source and returns buying tickets. Subtracts total <tt>tickets</tt>
	 * price from user's balance . Throws ServiceException if <tt>user</tt> is null
	 * or if <tt>tickets</tt> is empty or if any of <tt>tickets</tt> entries is not
	 * valid or if user's balance is less than total <tt>tickets</tt> price or if an
	 * error occurs while adding tickets into data source.
	 * 
	 * If an exception occurs user's balance must stay without changes
	 * 
	 * @param user    {@link User} who makes a purchase
	 * @param tickets list of {@link Ticket} than user desires to have
	 * @return list of users tickets if operation has a success
	 * @throws ServiceException if <tt>user</tt> is null or if <tt>tickets</tt> is
	 *                          empty or if any of <tt>tickets</tt> entries is not
	 *                          valid or if user's balance is less than total
	 *                          <tt>tickets</tt> price or if an error occurs while
	 *                          adding tickets into data source.
	 */
	List<Ticket> byeTickets(User user, List<Ticket> tickets) throws ServiceException;

	/**
	 * Makes free <tt>passengerQuantity</tt> places to <tt>flight</tt>. Throws
	 * ServiceException if <tt>flight</tt> is null.
	 * 
	 * @param flight            a {@link Flight} tickets unbooks to
	 * @param passengerQuantity places quantity that makes free
	 * @throws ServiceException if <tt>flight</tt> is null.
	 */
	void unbookTicketToFlight(Flight flight, int passengerQuantity) throws ServiceException;

	/**
	 * Returns all tickets than <tt>user</tt> possesses. If no one ticket was found
	 * returns an empty list. Throws ServiceException if <tt>user</tt> is null or if
	 * an error occurs while getting tickets from data source.
	 * 
	 * @param user the returned <tt>tickets</tt> owner
	 * @return list of tickets than belongs to <tt>user</tt>
	 * @throws ServiceException if <tt>user</tt> is null or if an exception occurs
	 *                          while getting a tickets from data source.
	 */
	List<Ticket> getAllUserTickets(User user) throws ServiceException;

	/**
	 * Retrieves and returns all bought ticket to the <tt>flight</tt>. If no such
	 * tickets found return an empty list. Throws ServiceExcption if <tt>flight</tt>
	 * is null or an exception occurs while searching tickets into data source.
	 * 
	 * @param flight the {@link Flight} tickets are searching to
	 * @return list of tickets to <tt>flight</tt> that were bought or an empty list
	 *         if no such tickets was found.
	 * @throws ServiceException if <tt>flight</tt> is null or an exception occurs
	 *                          while searching tickets into data source.
	 */
	List<Ticket> getAllTicketsToFlight(Flight flight) throws ServiceException;

	/**
	 * Returns <tt>ticket</tt> with <tt>ticketNumber</tt> back an returns tickets's cost
	 * back to user. Throws ServiceException if <tt>user</tt> is null or
	 * <tt>user</tt> do not possess {@link Ticket} with <tt>ticketNumber</tt> if
	 * <tt>ticketNumber</tt> is null or empty or if an error occurs while parsing it
	 * or if {@link Flight} departure date passed or if an exception occurs while
	 * working with data source.
	 * 
	 * @param user         {@link User} who possess the {@link Ticket} with
	 *                     <tt>ticketNumber</tt>
	 * @param ticketNumber the number of returned ticket
	 * @throws ServiceException if <tt>user</tt> is null or <tt>user</tt> do not
	 *                          possess {@link Ticket} with <tt>ticketNumber</tt> if
	 *                          <tt>ticketNumber</tt> is null or empty or if an
	 *                          error occurs while parsing it or if {@link Flight}
	 *                          departure date passed or if an exception occurs
	 *                          while working with data source.
	 */
	void returnTicket(User user, String ticketNumber) throws ServiceException;

	/**
	 * Retrieves and returns {@link Ticket} with <tt>ticketNumber</tt>. Adds ticket
	 * price (including luggage price) to {@link User}'s balance. Throws
	 * ServiceException if <tt>ticketNumber</tt> is null or empty or if an error
	 * occurs while parsing it or if no {@link Ticket} with <tt>ticketNumber</tt>
	 * was found
	 * 
	 * If an exception occurs {@link User}'s balance must stay without changes.
	 * 
	 * @param ticketNumber the number of searching {@link Ticket}
	 * @return {@link Ticket} which number is <tt>ticketNumber</tt>
	 * @throws ServiceException if <tt>ticketNumber</tt> is null or empty or if an
	 *                          error occurs while parsing it or if no
	 *                          {@link Ticket} with <tt>ticketNumber</tt> was found
	 */
	Ticket getTicketByNumber(String ticketNumber) throws ServiceException;

	/**
	 * Counts and returns total <tt>tickets</tt> price including price for luggage.
	 * Throws ServiceException if <tt>tickets</tt> is null or empty.
	 * 
	 * @param tickets list of tickets whose price must be count
	 * @return total <tt>tickets</tt> price including price for luggage
	 * @throws ServiceException if <tt>tickets</tt> is null or empty.
	 */
	BigDecimal countTicketPrice(List<Ticket> tickets) throws ServiceException;

	/**
	 * Retrieves and returns all bought tickets to <tt>flight</tt> or an empty list
	 * if no such ticket was found. Throws ServiceException if <tt>flight</tt> is
	 * null or if an error occurs while searching tickets into data source.
	 * 
	 * @param flight the {@link Flight} tickers are searching to
	 * @return list of bought {@link Ticket} to <tt>flight</tt> or empty list if no
	 *         {@link Ticket} was found
	 * @throws ServiceException if <tt>flight</tt> is null or if an error occurs
	 *                          while searching tickets into data source.
	 */
	List<String> getTicketToFlightHolders(Flight flight) throws ServiceException;
}