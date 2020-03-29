package by.training.karpilovich.lowcost.factory;

import by.training.karpilovich.lowcost.specification.Specification;
import by.training.karpilovich.lowcost.specification.impl.QuerySpecificationAllCities;
import by.training.karpilovich.lowcost.specification.impl.QuerySpecificationByNameAndCountryName;

public class SpecificationFactory {
	
	private SpecificationFactory() {
		
	}
	
	private static final class SpecificationFactoryInstanceHolder {
		private static final SpecificationFactory INSTANCE = new SpecificationFactory();
	}
	
	public static SpecificationFactory getInstance() {
		return SpecificationFactoryInstanceHolder.INSTANCE;
	}
	
	public Specification getQuerySpecificationAllCities() {
		return QuerySpecificationAllCities.getInstance();
	}
	
	public Specification getQuerySpecificationByNameAndCountryName(String name, String countryName) {
		return QuerySpecificationByNameAndCountryName.getInstance(name, countryName);
	}

}
