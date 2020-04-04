package by.training.karpilovich.lowcost.specification.impl;

import java.util.ArrayList;
import java.util.List;

import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.specification.Specification;

public class QuerySpecificationById implements Specification {
	
	private int id;
	
	private QuerySpecificationById(int id) {
		this.id = id;
	}
	
	public static Specification getInstance(int id) {
		return new QuerySpecificationById(id);
	}

	@Override
	public List<City> specify(List<City> cities) {
		List<City> choosen = new ArrayList<>();
		for (City city : cities) {
			if (city.getId() == id) {
				choosen.add(city);
			}
		}
		return choosen;
	}

}
