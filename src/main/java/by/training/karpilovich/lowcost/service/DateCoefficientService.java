package by.training.karpilovich.lowcost.service;

import java.util.Calendar;
import java.util.SortedSet;

import by.training.karpilovich.lowcost.entity.DateCoefficient;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.exception.ServiceException;

public interface DateCoefficientService {

	void addDateCoefficientToSet(SortedSet<DateCoefficient> coefficients, Calendar maxValue, String from, String to,
			String value) throws ServiceException;

	void saveCoefficients(Flight flight, SortedSet<DateCoefficient> coefficients) throws ServiceException;

	Calendar getNextBoundFromValueDateCoefficient(SortedSet<DateCoefficient> coefficients) throws ServiceException;
}