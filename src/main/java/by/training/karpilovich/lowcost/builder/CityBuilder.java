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
	
	public void setCityId(int id) {
		city.setId(id);
	}
	
	public void setCityName(String name) {
		city.setName(name);
	}
	
	public void setCityCountry(String country) {
		city.setCountry(country);
	}
	
}
