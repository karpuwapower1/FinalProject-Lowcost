package by.training.karpilovich.lowcost.builder;

import java.math.BigDecimal;
import java.util.Calendar;

import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.entity.Plane;

public class FlightBuilder {

	private Flight flight;

	public FlightBuilder() {
		flight = new Flight();
	}

	public Flight getFlight() {
		return flight;
	}

	public FlightBuilder setId(int id) {
		flight.setId(id);
		return this;
	}

	public FlightBuilder setAvailablePlaceQuantity(int places) {
		flight.setAvailablePlaceQuantity(places);
		return this;
	}

	public FlightBuilder setPermittedLuggageWeight(int weight) {
		flight.setPermittedLuggageWeigth(weight);
		return this;
	}

	public FlightBuilder setPrice(BigDecimal price) {
		flight.setPrice(price);
		return this;
	}

	public FlightBuilder setDate(Calendar date) {
		flight.setDate(date);
		return this;
	}

	public FlightBuilder setFlightNumber(String number) {
		flight.setNumber(number);
		return this;
	}

	public FlightBuilder setPlaneModel(Plane model) {
		flight.setPlaneModel(model);
		return this;
	}

	public FlightBuilder setFrom(City from) {
		flight.setFrom(from);
		return this;
	}

	public FlightBuilder setTo(City to) {
		flight.setTo(to);
		return this;
	}

	public FlightBuilder setPrimaryBoardingPrice(BigDecimal primaryBoardingPrice) {
		flight.setPrimaryBoardingPrice(primaryBoardingPrice);
		return this;
	}

	public FlightBuilder setOverweightLuggagePrice(BigDecimal price) {
		flight.setPriceForEveryKgOverweight(price);
		return this;
	}
}