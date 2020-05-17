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

	public TicketBuilder setNumber(long number) {
		ticket.setNumber(number);
		return this;
	}

	public TicketBuilder setEmail(String email) {
		ticket.setEmail(email);
		return this;
	}

	public TicketBuilder setFlight(Flight flight) {
		ticket.setFlight(flight);
		return this;
	}

	public TicketBuilder setPurshaseDate(Calendar purchaseDate) {
		ticket.setPurchaseDate(purchaseDate);
		return this;
	}

	public TicketBuilder setPrice(BigDecimal price) {
		ticket.setPrice(price);
		return this;
	}

	public TicketBuilder setPassengerFirstName(String firstName) {
		ticket.setPassengerFirstName(firstName);
		return this;
	}

	public TicketBuilder setPassengerLastName(String lastName) {
		ticket.setPassengerLastName(lastName);
		return this;
	}

	public TicketBuilder setPassengerPassportNumber(String number) {
		ticket.setPassengerPassportNumber(number);
		return this;
	}

	public TicketBuilder setOverweightLuggagePrice(BigDecimal price) {
		ticket.setOverweightLuggagePrice(price);
		return this;
	}

	public TicketBuilder setLuggageQuantity(int quantity) {
		ticket.setLuggageQuantity(quantity);
		return this;
	}

	public TicketBuilder setPrimaryBoardingRight(boolean right) {
		ticket.setPrimaryBoargingRight(right);
		return this;
	}
}