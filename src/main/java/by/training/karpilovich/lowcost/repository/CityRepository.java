package by.training.karpilovich.lowcost.repository;

import java.util.List;

import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.exception.RepositoryException;
import by.training.karpilovich.lowcost.specification.Specification;

public interface CityRepository {
	
	void init() throws RepositoryException;
	
	void add(City city) throws RepositoryException;
	
	void delete(City city) throws RepositoryException;
	
	void update(City city) throws RepositoryException;
	
	List<City> getCities(Specification specification) throws RepositoryException;
	
	

}