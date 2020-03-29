package by.training.karpilovich.lowcost.dao;

import java.util.Calendar;
import java.util.Set;

import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.entity.DateCoefficient;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.entity.LuggageCoefficient;
import by.training.karpilovich.lowcost.entity.PlaceCoefficient;
import by.training.karpilovich.lowcost.exception.DAOException;

public interface FlightDAO {

	boolean add(Flight flight, Set<PlaceCoefficient> placeCoefficient, Set<DateCoefficient> dateCoefficient,
			Set<LuggageCoefficient> luggageCoefficient) throws DAOException;

	boolean update(Flight flight) throws DAOException;

	boolean remove(Flight flight) throws DAOException;

	Set<Flight> getFlightsByDateAndPassengerQuantityWithoutPlaceAndDateCoefficient(City from, City to, Calendar date,
			int quantity) throws DAOException;

}
