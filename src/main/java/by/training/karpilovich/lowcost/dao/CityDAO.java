package by.training.karpilovich.lowcost.dao;

import java.util.Set;

import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.exception.DAOException;

public interface CityDAO {
	
	void addCity(City city) throws DAOException;
	
	void updateCity(City city) throws DAOException;
	
	void deleteCity(City id) throws DAOException;
	
	Set<City> getAllCities() throws DAOException;

}
