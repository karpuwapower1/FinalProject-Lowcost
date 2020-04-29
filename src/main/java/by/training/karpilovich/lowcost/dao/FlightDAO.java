package by.training.karpilovich.lowcost.dao;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.exception.DAOException;

public interface FlightDAO {

	void add(Flight flight) throws DAOException;

	void update(Flight flight) throws DAOException;

	void remove(Flight flight) throws DAOException;

	List<Flight> getFlightsByFromToDateAndPassengerQuantity(City from, City to, Calendar date,
			int quantity) throws DAOException;
	
	int countFlightWithNumberAndDate(String number, Calendar date) throws DAOException;
	
	List<Flight> getAllFlights() throws DAOException;
	
	Optional<Flight> getFlightById(int flightId) throws DAOException;
	
	List<Flight> getFlightsBetweenDates(Calendar from, Calendar to) throws DAOException;
}