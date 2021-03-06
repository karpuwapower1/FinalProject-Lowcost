package by.training.karpilovich.lowcost.service.impl;

import java.util.Set;
import java.util.SortedSet;

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
import by.training.karpilovich.lowcost.service.util.ServiceUtil;
import by.training.karpilovich.lowcost.specification.Specification;
import by.training.karpilovich.lowcost.util.message.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;
import by.training.karpilovich.lowcost.validator.city.CityAbsenceValidator;
import by.training.karpilovich.lowcost.validator.city.CityNameValidator;
import by.training.karpilovich.lowcost.validator.city.CountryNameValidator;

public class CityServiceImpl implements CityService {

	private static final Logger LOGGER = LogManager.getLogger(CityServiceImpl.class);

	private CityDAO cityDAO = DAOFactory.getInstance().getCityDAO();
	private CityRepository cityRepository = RepositoryFactory.getInstance().getCityRepository();
	private SpecificationFactory specificationFactory = SpecificationFactory.getInstance();
	private ServiceUtil serviceUtil = new ServiceUtil();

	private CityServiceImpl() {
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
			cityRepository.add(city);
		} catch (ValidatorException | DAOException | RepositoryException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public void updateCity(String cityId, String name, String country) throws ServiceException {
		Validator validator = getCityValidator(name, country);
		try {
			validator.validate();
			City city = buildCity(name, country);
			city.setId(serviceUtil.takeIntFromString(cityId));
			cityDAO.updateCity(city);
			cityRepository.update(city);
		} catch (ValidatorException | DAOException | RepositoryException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public void deleteCity(String cityId) throws ServiceException {
		int id = serviceUtil.takeIntFromString(cityId);
		City city = getCityById(id);
		try {
			cityDAO.deleteCity(city);
			cityRepository.delete(city);
		} catch (DAOException | RepositoryException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public SortedSet<City> getAllCities() throws ServiceException {
		Specification specification = specificationFactory.getQuerySpecificationAllCities();
		try {
			return cityRepository.getCities(specification);
		} catch (RepositoryException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public City getCityById(String cityId) throws ServiceException {
		int id = serviceUtil.takeIntFromString(cityId);
		return getCityById(id);
	}

	@Override
	public City getCityById(int id) throws ServiceException {
		try {
			return findCityById(id);
		} catch (RepositoryException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	private static final class CityCerviceImplInstanceHolder {
		private static final CityServiceImpl INSTANCE = new CityServiceImpl();
	}

	private City buildCity(String name, String countryName) {
		return new CityBuilder().setCityName(name).setCityCountry(countryName).getCity();
	}

	private Validator getCityValidator(String name, String countryName) {
		return new CityNameValidator(name).setNext(new CountryNameValidator(countryName))
				.setNext(new CityAbsenceValidator(name, countryName));
	}

	private City findCityById(int id) throws ServiceException, RepositoryException {
		Specification specification = specificationFactory.getQuerySpecificationById(id);
		Set<City> cities = cityRepository.getCities(specification);
		if (!cities.isEmpty()) {
			return cities.iterator().next();
		}
		LOGGER.error("Error while getting city by id " + id);
		throw new ServiceException(MessageType.INTERNAL_ERROR.getMessage());
	}
}