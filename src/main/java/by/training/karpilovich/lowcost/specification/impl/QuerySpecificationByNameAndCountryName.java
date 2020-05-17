package by.training.karpilovich.lowcost.specification.impl;

import java.util.SortedSet;
import java.util.TreeSet;

import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.specification.Specification;
import by.training.karpilovich.lowcost.util.CityByCountryAndNameComparator;

public class QuerySpecificationByNameAndCountryName implements Specification {

	private String name;
	private String countryName;

	private QuerySpecificationByNameAndCountryName(String name, String countryName) {
		this.name = name;
		this.countryName = countryName;
	}

	public static Specification getInstance(String name, String countryName) {
		return new QuerySpecificationByNameAndCountryName(name, countryName);
	}

	@Override
	public SortedSet<City> specify(SortedSet<City> cities) {
		SortedSet<City> choosen = new TreeSet<>(new CityByCountryAndNameComparator());
		for (City city : cities) {
			if (city.getName().equals(name) && city.getCountry().equals(countryName)) {
				choosen.add(city);
			}
		}
		return choosen;
	}
}