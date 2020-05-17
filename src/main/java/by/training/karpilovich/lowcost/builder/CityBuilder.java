package by.training.karpilovich.lowcost.builder;

import by.training.karpilovich.lowcost.entity.City;

public class CityBuilder {

	private City city;

	public CityBuilder() {
		city = new City();
	}

	public City getCity() {
		return city;
	}

	public CityBuilder setCityId(int id) {
		city.setId(id);
		return this;
	}

	public CityBuilder setCityName(String name) {
		city.setName(name);
		return this;
	}

	public CityBuilder setCityCountry(String country) {
		city.setCountry(country);
		return this;
	}
}