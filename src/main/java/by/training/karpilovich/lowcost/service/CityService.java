package by.training.karpilovich.lowcost.service;

import java.util.Set;

import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.exception.ServiceException;

/**
 * Interface describes the behavior of {@link City} entity
 * 
 * @author Aliaksei Karpilovich
 *
 */

public interface CityService {

	/**
	 * Saves City with specified name and country. Throws ServiceException if name
	 * or countryName equals null or empty, or if City with this name and country
	 * name has been added already.
	 * 
	 * @param name        the name of the added City
	 * @param countryName the name of the country added City belongs to
	 * @throws ServiceException if name or countryName is null or empty, or if City
	 *                          with these name and countryName has been added
	 *                          already or if reading from data source throws an
	 *                          exception
	 */
	void addCity(String name, String countryName) throws ServiceException;

	/**
	 * Sets name and contryName to City with specified id. throws ServiceException
	 * if name or countryName is null or empty; or if City with these name and
	 * countryName presents.
	 * 
	 * @param cityId  updating City's id
	 * @param name    new City's name
	 * @param country new country's name
	 * @throws ServiceException if name or countryName is null or empty; or if City
	 *                          with these name and countryName presents; or if
	 *                          reading from data source throws an exception
	 */
	void updateCity(String cityId, String name, String countryName) throws ServiceException;

	/**
	 * Deletes City with specified id. Throws ServiceException if parsing the id
	 * into number throws an exception; or if name or countryName is null; or empty
	 * or if City with this id does not exist
	 * 
	 * @param cityId deleting City's id
	 * @throws ServiceException is City with this id does not exist; or if name or
	 *                          countryName is null or empty; or if reading from
	 *                          data source throws an exception
	 */

	void deleteCity(String cityId) throws ServiceException;

	/**
	 * Returns Set of all cities.
	 * 
	 * @return Set of cities. If no City was found return an empty Set
	 * @throws ServiceException if reading from data source throws an exception
	 */
	Set<City> getAllCities() throws ServiceException;

	/**
	 * Returns City with specified id. Throws ServiceException if City with the id
	 * does not present in a source.
	 * 
	 * @param id the desired City's id
	 * @return a City with id
	 * @throws ServiceException if City with the id does not exist or if reading
	 *                          from data source throws an exception
	 */
	City getCityById(int id) throws ServiceException;

	/**
	 * Returns City with specified id. Throws ServiceException if parsing id to
	 * number throws an exception or if City with the id does not present in a
	 * source.
	 * 
	 * @param id the desired City's id
	 * @return a City with id
	 * @throws ServiceException if City with the id does not exist or if reading
	 *                          from data source throws an exception
	 */
	City getCityById(String id) throws ServiceException;
}