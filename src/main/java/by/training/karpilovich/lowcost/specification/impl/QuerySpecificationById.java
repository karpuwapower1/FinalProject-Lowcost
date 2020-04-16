package by.training.karpilovich.lowcost.specification.impl;

import java.util.SortedSet;
import java.util.TreeSet;

import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.specification.Specification;
import by.training.karpilovich.lowcost.util.CityByCountryAndNameComparator;

public class QuerySpecificationById implements Specification {
	
	private int id;
	
	private QuerySpecificationById(int id) {
		this.id = id;
	}
	
	public static Specification getInstance(int id) {
		return new QuerySpecificationById(id);
	}

	@Override
	public SortedSet<City> specify(SortedSet<City> cities) {
		SortedSet<City> choosen = new TreeSet<>(new CityByCountryAndNameComparator());
		for (City city : cities) {
			if (city.getId() == id) {
				choosen.add(city);
			}
		}
		return choosen;
	}
}