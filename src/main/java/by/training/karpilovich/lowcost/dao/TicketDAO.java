package by.training.karpilovich.lowcost.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.entity.Ticket;
import by.training.karpilovich.lowcost.entity.User;
import by.training.karpilovich.lowcost.exception.DAOException;
import by.training.karpilovich.lowcost.exception.ServiceException;

/**
 * Interface describes the opportunity that data source provide to store and
 * restore {@link Ticket} entity
 * 
 * @author Aliaksei Karpilovich
 *
 */
public interface TicketDAO {
	/**
	 * Makes a purchase for every {@link Ticket} in a <tt>tickets</tt> Map, saves
	 * information about every ticket's purchase, after that sets
	 * <tt>purchaseDate</tt> and ticket's <tt>number</tt> that were get according
	 * data source rules to every buying ticket after that adds tickets into data
	 * source and returns buying tickets. Subtracts total <tt>tickets</tt> price
	 * from {@link User}'s balance that email is equals to
	 * <tt>ticket.getEmail()</tt> . Throws DAOException if an error occurs while
	 * adding tickets into data source.
	 * 
	 * If an exception occurs data source must stay without changes
	 * 
	 * @param user    {@link User} who makes a purchase
	 * @param tickets list of {@link Ticket} than user desires to have
	 * @return list of users tickets if operation has a success
	 * @throws ServiceException if <tt>user</tt> is null or if <tt>tickets</tt> is
	 *                          empty or if any of <tt>tickets</tt> entries is not
	 *                          valid or if user's balance is less than total
	 *                          <tt>tickets</tt> price or if an error occurs while
	 *                          adding tickets into data source.
	 * @param ticketsAndPricesMap map of all tickets that user desired to buy and
	 *                            tickets' prices (including price for luggage)
	 * @return list of tickets that user has bought
	 * @throws DAOException if an error occurs while adding tickets into data
	 *                      source.
	 */
	List<Ticket> buyTickets(Map<Ticket, BigDecimal> ticketsAndPricesMap) throws DAOException;

	/**
	 * Returns <tt>ticket</tt> back an returns tickets's cost back to user. Throws
	 * DAOException if an error occurs while working with data source. If an error
	 * occurs data source must stay unchanged
	 * 
	 * 
	 * @param ticket the {@link Ticket} that user want to return
	 * @return true if operation has a success, false - otherwise
	 * @throws DAOException if an error occurs while working with data source.
	 */
	boolean returnTicket(Ticket ticket) throws DAOException;

	/**
	 * Returns all {@link Ticket} that ticket.getEmail() method returned value
	 * equals to <tt>email</tt>. If no one ticket was found returns an empty list.
	 * Throws DAOException if an error occurs while getting tickets from data
	 * source.
	 * 
	 * @param email the value that {@link Ticket}.getEmail() method must return
	 * @return all {@link Ticket} that ticket.getEmail() method returned value
	 *         equals to <tt>email</tt> or an empty list if no such a ticket was
	 *         found.
	 * @throws DAOException if an error occurs while getting tickets from data
	 *                      source.
	 */
	List<Ticket> getTicketByEmail(String email) throws DAOException;

	/**
	 * Returns all {@link Ticket} that ticket.getFlightId() method returned value
	 * equals to <tt>flightId</tt>. If no one ticket was found returns an empty
	 * list. Throws DAOException if an error occurs while getting tickets from data
	 * source.
	 * 
	 * @param flightId the value that {@link Ticket}.getFlightId() method must
	 *                 return
	 * @return all {@link Ticket} that ticket.getFlightId() method returned value
	 *         equals to <tt>flightId</tt> or an empty list if no such a ticket was
	 *         found.
	 * @throws DAOException if an error occurs while getting tickets from data
	 *                      source.
	 */
	List<Ticket> getTicketsByFlightId(int flightId) throws DAOException;

	/**
	 * Returns {@link Optional}.of({@link Ticket}) that ticket.getNumber() method
	 * returned value equals to <tt>ticketNumber</tt>. If no one ticket was found
	 * returns an <tt>Optional.empty()</tt>. Throws DAOException if an error occurs
	 * while getting tickets from data source.
	 * 
	 * @param ticketNumber the value that {@link Ticket}.getNumber() method must
	 *                     return
	 * @return {@link Optional}.of({@link Ticket}) that ticket.getNumber() method
	 *         returned value equals to <tt>ticketNumber</tt> or an
	 *         <tt>Optional.empty()</tt> if no such a ticket was found.
	 * @throws DAOException if an error occurs while getting tickets from data
	 *                      source.
	 */
	Optional<Ticket> getTicketByNumber(long ticketNumber) throws DAOException;

	/**
	 * Returns all {@link User}s that have bought tickets to <tt>flight</tt>. If
	 * there is no such user into data source returns an empty list. Throws
	 * DAOException if an error occurs while getting tickets from data source.
	 * 
	 * @param ticketNumber the value that {@link Ticket}.getNumber() method must
	 *                     return
	 * @return {@link Optional}.of({@link Ticket}) that ticket.getNumber() method
	 *         returned value equals to <tt>ticketNumber</tt> or an
	 *         <tt>Optional.empty()</tt> if no such a ticket was found.
	 * @throws DAOException if an error occurs while getting tickets from data
	 *                      source.
	 */
	/**
	 * 
	 * @param flight the {@link Flight} ticket's possessors is looking for to
	 * @return all {@link User}s that have bought tickets to <tt>flight</tt> or an
	 *         empty list if there is no such user into data source
	 * @throws DAOException if an error occurs while getting tickets from data
	 *                      source.
	 */
	List<String> getPossessorsOfTicketToFlightEmails(Flight flight) throws DAOException;
}