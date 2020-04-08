package by.training.karpilovich.lowcost.service;

import java.util.List;

import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.exception.ServiceException;

public interface CityService {
	
	void addCity(String name, String countryName) throws ServiceException;
	
	void updateCity(String id, String name, String country) throws ServiceException;
	
	void deleteCity(String id) throws ServiceException;
	
	List<City> getAllCities() throws ServiceException;
	
	List<City> getCities(String name, String countryName) throws ServiceException;
	
	City getCityById(int id) throws ServiceException;
	
	City getCityById(String id) throws ServiceException;

}
