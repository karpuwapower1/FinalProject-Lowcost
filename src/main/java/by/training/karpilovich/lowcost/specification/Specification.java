package by.training.karpilovich.lowcost.specification;

import java.util.SortedSet;

import by.training.karpilovich.lowcost.entity.City;

public interface Specification {

	SortedSet<City> specify(SortedSet<City> cities);

}