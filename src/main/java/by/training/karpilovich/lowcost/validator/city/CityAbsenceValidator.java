package by.training.karpilovich.lowcost.validator.city;

import java.util.Set;

import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.exception.RepositoryException;
import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.factory.RepositoryFactory;
import by.training.karpilovich.lowcost.factory.SpecificationFactory;
import by.training.karpilovich.lowcost.repository.CityRepository;
import by.training.karpilovich.lowcost.specification.Specification;
import by.training.karpilovich.lowcost.util.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;

public class CityPresenceValidator extends Validator {

	private String name;
	private String countryName;

	public CityPresenceValidator(String name, String countryName) {
		this.name = name;
		this.countryName = countryName;
	}

	@Override
	public void validate() throws ValidatorException {
		Set<City> cities = takeCitiesFromRepository();
		if (cities.size() != 0) {
			throw new ValidatorException(MessageType.CITY_ALREADY_PRESENTS.getMessage());
		}
		continueValidate();
	}

	private Set<City> takeCitiesFromRepository() throws ValidatorException {
		RepositoryFactory factory = RepositoryFactory.getInstance();
		CityRepository repository = factory.getCityRepository();
		SpecificationFactory specificationFactory = SpecificationFactory.getInstance();
		Specification specification = specificationFactory.getQuerySpecificationByNameAndCountryName(name, countryName);
		try {
			return repository.getCities(specification);
		} catch (RepositoryException e) {
			throw new ValidatorException(e.getMessage(), e);
		}
	}

}
