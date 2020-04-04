package by.training.karpilovich.lowcost.dao;

import java.util.List;

import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.exception.DAOException;

public interface CityDAO {
	
	void addCity(City city) throws DAOException;
	
	void updateCity(City city) throws DAOException;
	
	void deleteCity(City city) throws DAOException;
	
	List<City> getAllCities() throws DAOException;

}
