package by.training.karpilovich.lowcost.specification.impl;

import java.util.List;

import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.specification.Specification;

public class QuerySpecificationAllCities implements Specification {
	
	private QuerySpecificationAllCities() {
	}

	public static Specification getInstance() {
		return new QuerySpecificationAllCities();
	}
	
	
	@Override
	public List<City> specify(List<City> cities) {
		return cities;
	}

}
