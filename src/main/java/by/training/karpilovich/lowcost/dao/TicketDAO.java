package by.training.karpilovich.lowcost.dao;

import java.util.List;

import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.entity.Ticket;
import by.training.karpilovich.lowcost.exception.DAOException;

public interface TicketDAO {
	
	void add(Ticket ticket) throws DAOException;
	
	void deleteTicket(Ticket ticket) throws DAOException;
	
	List<Ticket> geticketByEmail(String email) throws DAOException;
	
	List<Ticket> getTicketToFlight(Flight flight) throws DAOException;
}