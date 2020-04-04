package by.training.karpilovich.lowcost.specification;

import java.util.List;

import by.training.karpilovich.lowcost.entity.City;

public interface Specification {

	List<City> specify(List<City> cities);
	
}
