package by.training.karpilovich.lowcost.service;

import java.util.SortedSet;

import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.entity.PlaceCoefficient;
import by.training.karpilovich.lowcost.exception.ServiceException;

public interface PlaceCoefficientService {

	void addPlaceCoefficientToSet(SortedSet<PlaceCoefficient> coefficients, int maxPlacesQuantity, String from,
			String to, String value) throws ServiceException;

	void saveCoefficients(Flight flight, SortedSet<PlaceCoefficient> coefficients) throws ServiceException;

	int getNextBoundFromValuePlaceCoefficient(SortedSet<PlaceCoefficient> coefficients) throws ServiceException;
}