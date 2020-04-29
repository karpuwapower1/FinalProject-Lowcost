package by.training.karpilovich.lowcost.dao;

import java.util.Set;

import by.training.karpilovich.lowcost.entity.DateCoefficient;
import by.training.karpilovich.lowcost.exception.DAOException;

public interface DateCoefficientDAO {
	
	void addCoefficient(int flightId, DateCoefficient coefficient) throws DAOException;
	
	void addCoefficients(int flightId, Set<DateCoefficient> coefficients) throws DAOException;

}