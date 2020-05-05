package by.training.karpilovich.lowcost.service;

import java.util.SortedSet;

import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.entity.PlaceCoefficient;
import by.training.karpilovich.lowcost.exception.ServiceException;

/**
 * Interface describes the behavior of {@link PlaceCoefficient} entity
 * 
 * @author Aliaksei Karpilovich
 */
public interface PlaceCoefficientService {

	/**
	 * Creates and returns a PlaceCoefficient from parameters. Throws
	 * ServiceExcpetion if <tt>boundFrom</tt> or <tt>boundTo</tt> is null or empty
	 * or if <tt>boundFrom</tt> equals or greater than <tt>boundTo</tt> or if
	 * <tt>boundTo</tt> greater than <tt>maxBound</tt> of if an exception occurs
	 * while parsing a coefficientValue.
	 * 
	 * @param maxBound         the max value that <tt>boundTo</tt> could be
	 * @param boundFrom        coefficient's from range value
	 * @param boundTo          coefficient's to range value
	 * @param coefficientValue the value of the coefficient
	 * @return PlaceCoefficient made from parameters
	 * @throws ServiceException if <tt>boundFrom</tt> or <tt>boundTo</tt> is null or
	 *                          empty or if <tt>boundFrom</tt> equals or greater
	 *                          than <tt>boundTo</tt> or if <tt>boundTo</tt> greater
	 *                          than <tt>maxBound</tt> of if an exception occurs
	 *                          while parsing a coefficientValue.
	 */
	public PlaceCoefficient createPlaceCoefficient(int maxPlacesQuantity, String boundFrom, String boundTo,
			String coefficientValue) throws ServiceException;

	/**
	 * Adds the <tt>coefficient</tt> into Set. Throws ServiceException if
	 * <tt>coefficient</tt> is null or if one of coefficient's bound is between one
	 * of Set entries bounds or vice versa
	 * 
	 * @param coefficients Set where coefficient is adding to
	 * @param coefficient  adding coefficient
	 * @throws ServiceException if <tt>coefficient</tt> is null or if one of
	 *                          coefficient's bound is between one of coefficient's
	 *                          entries bounds or vice versa
	 */
	void addPlaceCoefficientToSet(SortedSet<PlaceCoefficient> coefficients, PlaceCoefficient coefficient)
			throws ServiceException;

	/**
	 * Writes the coefficient's Set into data source. Throws ServiceException if
	 * <tt>flight</tt> is null. If coefficients contains entry one of whose bounds
	 * is between another, result will be unpredictable.
	 * 
	 * @param flight       the <tt>flight</tt> coefficients belong to
	 * @param coefficients adding coefficients
	 * @throws ServiceException if <tt>flight</tt> is null or data source exception
	 *                          occurs
	 */
	void saveCoefficients(Flight flight, SortedSet<PlaceCoefficient> coefficients) throws ServiceException;

	/**
	 * Returns the next coefficient's bound from value based on max coefficients'
	 * bound to value.
	 * 
	 * @param coefficients whose max to value is base for next bound from value
	 * @return the next coefficient's bound from value
	 */
	int getNextBoundFromValuePlaceCoefficient(SortedSet<PlaceCoefficient> coefficients);
}