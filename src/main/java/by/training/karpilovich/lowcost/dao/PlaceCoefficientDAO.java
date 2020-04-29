package by.training.karpilovich.lowcost.dao;

import java.util.Set;

import by.training.karpilovich.lowcost.entity.PlaceCoefficient;
import by.training.karpilovich.lowcost.exception.DAOException;

public interface PlaceCoefficientDAO {
	
	void addCoefficient(int flightId, PlaceCoefficient coefficient) throws DAOException;
	
	void addCoefficients(int flightId, Set<PlaceCoefficient> coefficients) throws DAOException;

}