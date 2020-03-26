package by.training.karpilovich.lowcost.dao;

import java.util.Calendar;
import java.util.List;

import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.exception.DaoException;

public interface FlightDAO {

	boolean add(Flight flight) throws DaoException;

	boolean update(Flight flight) throws DaoException;

	boolean remove(Flight flight) throws DaoException;

	List<Flight> getFlightsByDateAndPassengerQuantity(String countryFrom, String contryTo, Calendar date, int quantity)
			throws DaoException;

}
