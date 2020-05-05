package by.training.karpilovich.lowcost.dao;

import java.util.Set;

import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.entity.PlaceCoefficient;
import by.training.karpilovich.lowcost.exception.DAOException;

/**
 * Interface describes the opportunity that data source provide to store and
 * restore {@link PlaceCoefficient} entity
 * 
 * @author Aliaksei Karpilovich
 *
 */
public interface PlaceCoefficientDAO {

	/**
	 * Saves the <tt>coefficient</tt> into data source . Throws DAOException if an
	 * error occurs while writing a <tt>coefficient</tt>
	 * 
	 * @param flightId    the id of {@link Flight} coefficient is adding to
	 * @param coefficient the adding coefficient
	 * @throws DAOException if an error occurs while saving a <tt>coefficient</tt>
	 */
	void addCoefficient(int flightId, PlaceCoefficient coefficient) throws DAOException;

	/**
	 * Saves the Set of <tt>coefficients</tt> into data source . Throws DAOException
	 * if an error occurs while writing a <tt>coefficient</tt>
	 * 
	 * @param flightId     the id of {@link Flight} coefficient is adding to
	 * @param coefficients the adding Set of coefficients
	 * @throws DAOException if an error occurs while saving a <tt>coefficient</tt>
	 */
	void addCoefficients(int flightId, Set<PlaceCoefficient> coefficients) throws DAOException;

}