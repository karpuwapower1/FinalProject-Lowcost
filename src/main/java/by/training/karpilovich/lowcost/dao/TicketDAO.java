package by.training.karpilovich.lowcost.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.entity.Ticket;
import by.training.karpilovich.lowcost.exception.DAOException;

public interface TicketDAO {
	
	List<Ticket> add(Map<Ticket, BigDecimal> ticketsAndPricesMap) throws DAOException;
	
	boolean deleteTicket(Ticket ticket) throws DAOException;
	
	List<Ticket> getTicketByEmail(String email) throws DAOException;
	
	List<Ticket> getTicketsByFlightId(int flightId) throws DAOException;
	
	Optional<Ticket> getTicketByNumber(long ticketNumber) throws DAOException;
	
	List<String> getTicketToFlightHolders(Flight flight) throws DAOException;
}