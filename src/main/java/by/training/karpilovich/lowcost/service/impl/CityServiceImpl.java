package by.training.karpilovich.lowcost.service.impl;

import java.util.List;

import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.exception.RepositoryException;
import by.training.karpilovich.lowcost.exception.ServiceException;
import by.training.karpilovich.lowcost.factory.RepositoryFactory;
import by.training.karpilovich.lowcost.factory.SpecificationFactory;
import by.training.karpilovich.lowcost.repository.CityRepository;
import by.training.karpilovich.lowcost.service.CityService;
import by.training.karpilovich.lowcost.specification.Specification;
import by.training.karpilovich.lowcost.util.MessageType;

public class CityServiceImpl implements CityService {

	private static final String REGEX = ", ";
	private static final int CITY_NAME_INDEX = 0;
	private static final int COUNTRY_NAME_INDEX = 1;

	private CityServiceImpl() {

	}

	private static final class CityCerviceImplInstanceHolder {
		private static final CityServiceImpl INSTANCE = new CityServiceImpl();
	}

	public static CityServiceImpl getInstance() {
		return CityCerviceImplInstanceHolder.INSTANCE;
	}

	@Override
	public void addCity(String name, String countryName) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCity(City city) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteCity(City city) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<City> getAllCities() throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public City getCity(String requestParameter) throws ServiceException {
		String cityName = getCityNameFromRequestParameter(requestParameter);
		String countryName = getCountryNameFromRequestParameter(requestParameter);
		List<City> cities = getCities(cityName, countryName);
		if (cities.size() != 1) {
			throw new ServiceException(MessageType.INVALID_CITY.getType());
		}
		return cities.get(0);
	}

	@Override
	public List<City> getCities(String name, String countryName) throws ServiceException {
		SpecificationFactory factory = SpecificationFactory.getInstance();
		Specification specification = factory.getQuerySpecificationByNameAndCountryName(name, countryName);
		try {
			CityRepository repository = RepositoryFactory.getInstance().getCityRepository();
			return repository.getCities(specification);
		} catch (RepositoryException e) {
			throw new ServiceException(e.getMessage());
		}

	}

	private String getCityNameFromRequestParameter(String parameter) {
		return parameter.split(REGEX)[CITY_NAME_INDEX];

	}

	private String getCountryNameFromRequestParameter(String parameter) {
		return parameter.split(REGEX)[COUNTRY_NAME_INDEX];
	}

}
