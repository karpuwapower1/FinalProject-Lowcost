package by.training.karpilovich.lowcost.dao;

import java.util.List;

import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.exception.DAOException;

public interface CityDAO {
	
	boolean addCity(City city) throws DAOException;
	
	boolean updateCity(City city) throws DAOException;
	
	boolean deleteCity(City city) throws DAOException;
	
	List<City> getAllCities() throws DAOException;

}
