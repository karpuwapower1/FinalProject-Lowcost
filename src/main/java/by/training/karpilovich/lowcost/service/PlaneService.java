package by.training.karpilovich.lowcost.service;

import java.util.List;

import by.training.karpilovich.lowcost.entity.Plane;
import by.training.karpilovich.lowcost.exception.ServiceException;

/**
 * Describes a behavior of {@link Plane} entity
 * 
 * @author Aliaksei Karpilovich
 *
 */
public interface PlaneService {

	/**
	 * Adds a Plane with <tt>model</tt> and <tt>placeQuantity</tt>. Throws
	 * ServiceException if <tt>model</tt> is null or empty or id
	 * <tt>placeQuantity</tt> less than or equals to 0 or if Plane with the
	 * <tt>model</tt> and <tt>placeQuantity</tt> already present in a data source or
	 * if an error occurs while in data source while adding a plane.
	 * 
	 * @param model         adding plane model
	 * @param placeQuantity adding plane placeQuantity
	 * @throws ServiceException if <tt>model</tt> is null or empty or id
	 *                          <tt>placeQuantity</tt> less than or equals to 0 or
	 *                          if Plane with the <tt>model</tt> and
	 *                          <tt>placeQuantity</tt> already present in a data
	 *                          source or if an error occurs while in data source
	 *                          while adding a plane.
	 */
	void add(String model, String placeQuantity) throws ServiceException;

	/**
	 * Deletes {@link Plane} with <tt>model</tt> from data source. Throws
	 * ServiceException if model is null or empty or if data source do not contain
	 * {@link Plane} with <tt>model</tt> or if an error occurs while deleting a
	 * plane from data source
	 * 
	 * @param model the deleting {@link Plane} <tt>model</tt>
	 * @throws ServiceException if model is null or empty or if data source do not
	 *                          contain {@link Plane} with <tt>model</tt> or if an
	 *                          error occurs while deleting a plane from data source
	 */
	void delete(String model) throws ServiceException;

	/**
	 * Returns list of all planes data source contains. If no plane was found
	 * returns an empty list. Throws ServiceException if an error occurs in data
	 * source while getting a planes
	 * 
	 * @return list of all planes data source contains or an empty list if no plane
	 *         was found
	 * @throws ServiceException if an error occurs in data source while getting a
	 *                          planes
	 */
	List<Plane> getAllPlanes() throws ServiceException;

	/**
	 * Searches and returns a {@link Plane} with <tt>model</tt>. Throws an
	 * ServiceExcepotion if if model is null or empty or if data source do not
	 * contain {@link Plane} with <tt>model</tt> or if an error occurs while
	 * deleting a plane from data source
	 * 
	 * @param model searching {@link Plane} <tt>model</tt>
	 * @return {@link Plane} with <tt>model</tt>
	 * @throws ServiceException if if model is null or empty or if data source do
	 *                          not contain {@link Plane} with <tt>model</tt> or if
	 *                          an error occurs while deleting a plane from data
	 *                          source
	 */
	Plane getPlaneByModel(String model) throws ServiceException;
}