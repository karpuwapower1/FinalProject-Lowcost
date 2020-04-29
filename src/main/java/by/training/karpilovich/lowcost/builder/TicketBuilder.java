package by.training.karpilovich.lowcost.builder;

import java.math.BigDecimal;
import java.util.Calendar;

import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.entity.Ticket;

public class TicketBuilder {

	private Ticket ticket;
	
	public TicketBuilder() {
		ticket = new Ticket();
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setNumber(long number) {
		ticket.setNumber(number);
	}
	
	public void setEmail(String email) {
		ticket.setEmail(email);
	}

	public void setFlight(Flight flight) {
		ticket.setFlight(flight);
	}
	
	public void setPurshaseDate(Calendar purchaseDate) {
		ticket.setPurchaseDate(purchaseDate);
	}
	
	public void setPrice(BigDecimal price) {
		ticket.setPrice(price);
	}
	
	public void setPassengerFirstName(String firstName) {
		ticket.setPassengerFirstName(firstName);
	}

	public void setPassengerLastName(String lastName) {
		ticket.setPassengerLastName(lastName);
	}
	
	public void setPassengerPassportNumber(String number) {
		ticket.setPassengerPassportNumber(number);
	}
	
	public void setOverweightLuggagePrice(BigDecimal price) {
		ticket.setOverweightLuggagePrice(price);
	}
	
	public void setLuggageQuantity(int quantity) {
		ticket.setLuggageQuantity(quantity);
	}
	
	public void setPrimaryBoardingRight(boolean right) {
		ticket.setPrimaryBoargingRight(right);
	}
}