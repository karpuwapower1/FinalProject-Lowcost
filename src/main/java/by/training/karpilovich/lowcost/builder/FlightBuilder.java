package by.training.karpilovich.lowcost.builder;

import java.math.BigDecimal;
import java.util.Calendar;

import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.entity.PlaneModel;

public class FlightBuilder {
	
	private Flight flight;
	
	public FlightBuilder() {
		flight = new Flight();
	}
	
	public Flight getFlight() {
		return flight;
	}
	
	public void setId(int id) {
		flight.setId(id);
	}
	
	public void setAvailablePlaceQuantity(int places) {
		flight.setAvailablePlaceQuantity(places);
	}
	
	public void setPermittedLuggageWeight(int weight) {
		flight.setPermittedLuggageWeigth(weight);
	}
	
	public void setPrice(BigDecimal price) {
		flight.setPrice(price);
	}
	
	public void setDate(Calendar date) {
		flight.setDate(date);
	}
	
	public void setFlightNumber(String number) {
		flight.setNumber(number);
	}
	
	public void setPlaneModel(PlaneModel model) {
		flight.setPlaneModel(model);
	}
	
	public void setFrom(City from) {
		flight.setFrom(from);
	}
	public void setTo(City to) {
		flight.setTo(to);
	}


}
