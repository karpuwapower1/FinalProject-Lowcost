package by.training.karpilovich.lowcost.service;

import java.util.Calendar;
import java.util.SortedSet;

import by.training.karpilovich.lowcost.entity.DateCoefficient;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.entity.PlaceCoefficient;
import by.training.karpilovich.lowcost.exception.ServiceException;

public interface FlightCreatorService {

	Flight createFlight(String number, String fromId, String toId, String date, String defaultPrice,
			String primaryBoardingPrice, String model, String permittedLuggage, String priceForKgOverweight)
			throws ServiceException;

	int getNextBoundFromValuePlaceCoefficient(SortedSet<PlaceCoefficient> coefficients) throws ServiceException;

	Calendar getNextBoundFromValueDateCoefficient(SortedSet<DateCoefficient> coefficinets) throws ServiceException;

	void addDateCoefficient(Flight flight, SortedSet<DateCoefficient> coefficients, String to,
			String value) throws ServiceException;

	void addPlaceCoefficient(Flight flight, SortedSet<PlaceCoefficient> coefficients, String to,
			String value) throws ServiceException;
}