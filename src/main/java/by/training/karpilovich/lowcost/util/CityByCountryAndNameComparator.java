package by.training.karpilovich.lowcost.util;

import java.util.Comparator;

import by.training.karpilovich.lowcost.entity.City;

public class CityByCountryAndNameComparator implements Comparator<City> {

	@Override
	public int compare(City o1, City o2) {
		int value;
		if ((value = o1.getCountry().compareTo(o2.getCountry())) == 0) {
			value = o1.getName().compareTo(o2.getName());
		}
		return value;
	}
}