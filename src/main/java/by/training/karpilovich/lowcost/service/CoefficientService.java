package by.training.karpilovich.lowcost.service;

import by.training.karpilovich.lowcost.entity.coefficient.DateCoefficient;
import by.training.karpilovich.lowcost.entity.coefficient.LuggageCoefficient;
import by.training.karpilovich.lowcost.entity.coefficient.PlaceCoefficient;
import by.training.karpilovich.lowcost.exception.ServiceException;

public interface CoefficientService {

	DateCoefficient makeDateCoefficientFromParameters(int id, String from, String to, String value)
			throws ServiceException;
	
	PlaceCoefficient makePlaceCoefficientFromParameters(int id, String from, String to, String value)
			throws ServiceException;
	
	LuggageCoefficient makeLuggageCoefficientFromParameters(int id, String from, String to, String value)
			throws ServiceException;

}
