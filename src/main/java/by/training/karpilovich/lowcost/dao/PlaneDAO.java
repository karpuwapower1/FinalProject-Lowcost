package by.training.karpilovich.lowcost.dao;

import java.util.List;
import java.util.Optional;

import by.training.karpilovich.lowcost.entity.Plane;
import by.training.karpilovich.lowcost.exception.DAOException;

/**
 * Interface describes the opportunity that data source provide to store and
 * restore {@link Plane} entity
 * 
 * @author Aliaksei Karpilovich
 *
 */
public interface PlaneDAO {
	/**
	 * Saves the <tt>plane</tt> into data source. Throws DAOException if an error
	 * occurs while writing a <tt>plane</tt>
	 * 
	 * @param plane the {@link Plane} that must be added to data source
	 * @throws DAOException if an error occurs while writing a <tt>plane</tt>
	 */
	void addPlane(Plane plane) throws DAOException;

	/**
	 * Deletes {@link Plane} from data source that <tt>model</tt> is equals to
	 * <tt>plane.getModel()</tt>. Throws DAOException if an error occurs while
	 * deleting a <tt>plane</tt>
	 * 
	 * @param plane {@link Plane} that must be deleted
	 * @throws DAOException if an error occurs while deleting a <tt>plane</tt>
	 */
	void deletePlane(Plane plane) throws DAOException;

	/**
	 * Retrieves and returns all planes that data source contains. If no plane was
	 * found returns an empty list. Throws DAOException if an error occurs while
	 * searching planes
	 * 
	 * @return list of all planes that data source contains or an empty list if data
	 *         source does not contains any of it.
	 * @throws DAOException if an error occurs while searching planes
	 */
	List<Plane> getAllPlanes() throws DAOException;

	/**
	 * Searches and returns {@link Plane} object with
	 * <tt>plane.getModel().equals(model)</tt>. If no such {@link Plane} was found
	 * return an <tt>Optional.empty()</tt>. Throws DAOException if an error occurs
	 * while working with a data source
	 * 
	 * @param model the model of the {@link Plane} that is looking for
	 * @return{@link Optional}<{@link Plane} object that
	 *               <tt>plane.getModel().equals(model)</tt>. If no such
	 *               {@link Plane} was found return an <tt>Optional.empty()</tt>.
	 * 
	 * @throws DAOException if an error occurs while working with a data source
	 */
	Optional<Plane> getPlaneByModelName(String model) throws DAOException;
}