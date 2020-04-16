package by.training.karpilovich.lowcost.dao;

import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.entity.DateCoefficient;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.entity.PlaceCoefficient;
import by.training.karpilovich.lowcost.exception.DAOException;

public interface FlightDAO {

	void add(Flight flight, SortedSet<DateCoefficient> dateCoefficients, SortedSet<PlaceCoefficient> placeCoefficients) throws DAOException;

	void update(Flight flight) throws DAOException;

	void remove(Flight flight) throws DAOException;

	Set<Flight> getFlightsByFromToDateAndPassengerQuantity(City from, City to, Calendar date,
			int quantity) throws DAOException;
	
	int countFlightWithNumberAndDate(String number, Calendar date) throws DAOException;
	
	List<Flight> getAllFlights() throws DAOException;

}
