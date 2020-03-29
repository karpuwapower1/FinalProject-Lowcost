package by.training.karpilovich.lowcost.specification.impl;

import java.util.ArrayList;
import java.util.List;

import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.specification.Specification;

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
	public List<City> specify(List<City> cities) {
		List<City> choosen = new ArrayList<>();
		for (City city : cities) {
			if (city.getName().equals(name) && city.getCountry().equals(countryName)) {
				choosen.add(city);
			}
		}
		return choosen;
	}

}
