package by.training.karpilovich.lowcost.repository;

import java.util.SortedSet;

import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.exception.RepositoryException;
import by.training.karpilovich.lowcost.specification.Specification;

/**
 * Represents local cash of all cities in data source.
 * 
 * @author Aliaksei Karpilovich
 *
 */
public interface CityRepository {

	/**
	 * Initialize local cash. Download all cities from data source into local cash.
	 * Throws RepositoryException if an error occurs while working with data source
	 * 
	 * @throws RepositoryException if an error occurs while getting all cities from
	 *                             data source
	 */
	void init() throws RepositoryException;

	/**
	 * Add <tt>city</tt> to local cash. Throws RepositoryException if cash hasn't
	 * been initialized yet.
	 * 
	 * @param city the adding to a local cash {@link City}
	 * @throws RepositoryException if cash hasn't been initialized yet.
	 */
	void add(City city) throws RepositoryException;

	/**
	 * Delete <tt>city</tt> from local cash. Throws RepositoryException if cash
	 * hasn't been initialized yet.
	 * 
	 * @param city the removed from cash {@link City}
	 * @throws RepositoryException if cash hasn't been initialized yet.
	 */
	void delete(City city) throws RepositoryException;

	/**
	 * Update <tt>city</tt> that local cash contains. Throws RepositoryException if
	 * cash hasn't been initialized or cash do not contain city that must be updated
	 * hasn't been initialized yet.
	 * 
	 * @param city the updated {@link City}
	 * @throws RepositoryException if cash hasn't been initialized or cash do not
	 *                             contain city that must be updated hasn't been
	 *                             initialized yet.
	 */
	void update(City city) throws RepositoryException;

	/**
	 * Return {@link SortedSet} of cities according to the {@link Specification}
	 * condition. If no such cities were found return an empty set. Throws
	 * RepositoryException if cash hasn't been initialized yet.
	 * 
	 * @param specification the {@link Specification} that declares a search
	 *                      condition.
	 * @return {@link SortedSet} of cities according to the {@link Specification}
	 *         condition
	 * @throws RepositoryException if cash hasn't been initialized yet.
	 */
	SortedSet<City> getCities(Specification specification) throws RepositoryException;
}