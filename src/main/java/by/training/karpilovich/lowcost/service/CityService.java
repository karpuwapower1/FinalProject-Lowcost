package by.training.karpilovich.lowcost.service;

import java.util.List;

import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.exception.ServiceException;

public interface CityService {
	
	void addCity(String name, String countryName) throws ServiceException;
	
	void updateCity(City city) throws ServiceException;
	
	void deleteCity(City city) throws ServiceException;
	
	City getCity(String requestParameter) throws ServiceException;
	
	List<City> getAllCities() throws ServiceException;
	
	List<City> getCities(String name, String countryName) throws ServiceException;

}
