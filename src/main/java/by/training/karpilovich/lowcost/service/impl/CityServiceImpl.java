package by.training.karpilovich.lowcost.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.builder.CityBuilder;
import by.training.karpilovich.lowcost.dao.CityDAO;
import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.exception.DAOException;
import by.training.karpilovich.lowcost.exception.RepositoryException;
import by.training.karpilovich.lowcost.exception.ServiceException;
import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.factory.DAOFactory;
import by.training.karpilovich.lowcost.factory.RepositoryFactory;
import by.training.karpilovich.lowcost.factory.SpecificationFactory;
import by.training.karpilovich.lowcost.repository.CityRepository;
import by.training.karpilovich.lowcost.service.CityService;
import by.training.karpilovich.lowcost.specification.Specification;
import by.training.karpilovich.lowcost.util.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;
import by.training.karpilovich.lowcost.validator.city.CityNameValidator;
import by.training.karpilovich.lowcost.validator.city.CityPresenceValidator;
import by.training.karpilovich.lowcost.validator.city.CountryNameValidator;

public class CityServiceImpl implements CityService {

	private static final Logger LOGGER = LogManager.getLogger(CityServiceImpl.class);

	private final CityDAO cityDAO = DAOFactory.getInstance().getCityDAO();
	private final CityRepository cityRepository = RepositoryFactory.getInstance().getCityRepository();
	private final SpecificationFactory specificationFactory = SpecificationFactory.getInstance();

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
		Validator validator = getCityValidator(name, countryName);
		try {
			validator.validate();
			City city = buildCity(name, countryName);
			cityDAO.addCity(city);
			LOGGER.debug(city.toString());
			cityRepository.add(city);
		} catch (ValidatorException | DAOException | RepositoryException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public void updateCity(String id, String name, String country) throws ServiceException {
		Validator validator = getCityValidator(name, country);
		try {
			validator.validate();
			City city = buildCity(name, country);
			city.setId(getIntFromString(id));
			cityDAO.updateCity(city);
			cityRepository.update(city);
		} catch (ValidatorException | DAOException | RepositoryException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public void deleteCity(String cityId) throws ServiceException {
		int id = getIntFromString(cityId);
		City city = getCityById(id);
		try {
			cityDAO.deleteCity(city);
			cityRepository.delete(city);
		} catch (DAOException | RepositoryException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<City> getAllCities() throws ServiceException {
		Specification specification = specificationFactory.getQuerySpecificationAllCities();
		try {
			return cityRepository.getCities(specification);
		} catch (RepositoryException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
	
	@Override
	public City getCityById(String cityId) throws ServiceException {
		int id = getIntFromString(cityId);
		return getCityById(id);
	}

	@Override
	public City getCityById(int id) throws ServiceException {
		try {
			Specification specification = specificationFactory.getQuerySpecificationById(id);
			List<City> cities = cityRepository.getCities(specification);
			if (cities.size() == 1) {
				return cities.get(0);
			}
			LOGGER.error("Error while getting city by id " + id);
			throw new ServiceException(MessageType.INTERNAL_ERROR.getMessage());
		} catch (RepositoryException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public List<City> getCities(String name, String countryName) throws ServiceException {
		Specification specification = specificationFactory.getQuerySpecificationByNameAndCountryName(name, countryName);
		try {
			return cityRepository.getCities(specification);
		} catch (RepositoryException e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
	private City buildCity(String name, String countryName) {
		CityBuilder builder = new CityBuilder();
		builder.setCityName(name);
		builder.setCityCountry(countryName);
		return builder.getCity();
	}

	private Validator getCityValidator(String name, String countryName) {
		Validator nameValidator = new CityNameValidator(name);
		Validator countryNameValidator = new CountryNameValidator(countryName);
		Validator cityPresenceValidator = new CityPresenceValidator(name, countryName);
		nameValidator.setNext(countryNameValidator);
		countryNameValidator.setNext(cityPresenceValidator);
		return nameValidator;
	}
	
	private int getIntFromString(String value) throws ServiceException {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new ServiceException(MessageType.INVALID_NUMBER_FORMAT.getMessage());
		}
	}

}
