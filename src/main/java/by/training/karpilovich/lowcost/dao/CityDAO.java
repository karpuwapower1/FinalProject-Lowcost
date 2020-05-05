package by.training.karpilovich.lowcost.dao;

import java.util.Set;

import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.exception.DAOException;

/**
 * Interface describes the opportunity that data source provide to store and
 * restore {@link City} entity
 * 
 * @author Aliaksei Karpilovich
 *
 */
public interface CityDAO {

	/**
	 * Saves the <tt>city</tt> into data source and sets id to added city according
	 * data source rules. Throws DAOException if an error occurs while writing a
	 * <tt>city</tt>
	 * 
	 * @param city the {@link City} that must be added to data source
	 * @throws DAOException if an error occurs while writing a <tt>city</tt>
	 */
	void addCity(City city) throws DAOException;

	/**
	 * Updates information about {@link City} is a data source that id equals to
	 * <tt>city.getId()</tt>. Throws DAOException if an error occurs while writing a
	 * <tt>city</tt>
	 * 
	 * @param city {@link City} that information is updating
	 * @throws DAOException if an error occurs while writing a <tt>city</tt>
	 */
	void updateCity(City city) throws DAOException;

	/**
	 * Deletes {@link City} from data source that id is equals to
	 * <tt>city.getId()</tt>. Throws DAOException if an error occurs while deleting
	 * a <tt>city</tt>
	 * 
	 * @param city {@link City} that must be deleted
	 * @throws DAOException if an error occurs while deleting a <tt>city</tt>
	 */
	void deleteCity(City city) throws DAOException;

	/**
	 * Retrieves and returns all cities that data source contains. If no city was
	 * found returns an empty set. Throws DAOException if an error occurs while
	 * searching cities
	 * 
	 * @return Set of all cities that data source contains or an empty Set if data
	 *         source does not contains any of it.
	 * @throws DAOException if an error occurs while searching cities
	 */
	Set<City> getAllCities() throws DAOException;
}