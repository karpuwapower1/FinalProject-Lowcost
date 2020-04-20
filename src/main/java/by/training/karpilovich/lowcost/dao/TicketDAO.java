package by.training.karpilovich.lowcost.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.entity.Ticket;
import by.training.karpilovich.lowcost.exception.DAOException;

public interface TicketDAO {
	
	List<Ticket> add(Map<Ticket, BigDecimal> ticketsAndPricesMap) throws DAOException;
	
	void deleteTicket(Ticket ticket) throws DAOException;
	
	List<Ticket> getTicketByEmail(String email) throws DAOException;
	
	List<Ticket> getTicketToFlight(Flight flight) throws DAOException;
	
}