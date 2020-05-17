package by.training.karpilovich.lowcost.specification;

import java.util.SortedSet;

import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.repository.CityRepository;

/**
 * A <tt>Specification</tt> represents the condition for select a cities from
 * {@link CityRepository}
 * 
 * @author Aliaksei Karpilovich
 */
public interface Specification {

	/**
	 * A method that returns {@link SortedSet} of cities that according to special
	 * condition. If no such cities was found returns an empty {@link Set};
	 * 
	 * @param cities the collection cities according special condition are selecting
	 *               from
	 * @return {@link SortedSet} of cities that according to special condition. If
	 *         no such cities was found returns an empty {@link Set};
	 */
	SortedSet<City> specify(SortedSet<City> cities);
}