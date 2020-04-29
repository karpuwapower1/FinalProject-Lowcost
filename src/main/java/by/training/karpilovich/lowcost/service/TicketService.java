package by.training.karpilovich.lowcost.service;

import java.math.BigDecimal;
import java.util.List;

import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.entity.Ticket;
import by.training.karpilovich.lowcost.entity.User;
import by.training.karpilovich.lowcost.exception.ServiceException;

public interface TicketService {

	void bookTicketToFlight(Flight flight, int passengerQuantity) throws ServiceException;

	Ticket createTicket(User user, Flight flight, String firstName, String lastName, String passportNumber,
			String luggageQuantity, String primaryBoardingRight) throws ServiceException;

	List<Ticket> byeTickets(User user, List<Ticket> ticket) throws ServiceException;
	
	void unbookTicketToFlight(Flight flight, int passengerQuantity) throws ServiceException;
	
	List<Ticket> getAllUserTickets(User user) throws ServiceException;
	
	List<Ticket> getAllTicketsToFlight(Flight flight) throws ServiceException;
	
	void returnTicket(User user, String ticketNumber) throws ServiceException;
	
	Ticket getTicketByNumber(String number) throws ServiceException;
	
	BigDecimal countTicketPrice(List<Ticket> tickets) throws ServiceException;
}