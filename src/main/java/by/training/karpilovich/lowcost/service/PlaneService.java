package by.training.karpilovich.lowcost.service;

import java.util.List;

import by.training.karpilovich.lowcost.entity.Plane;
import by.training.karpilovich.lowcost.exception.ServiceException;

public interface PlaneService {
	
	void add(String model, String placeQuantity) throws ServiceException;
	
	void update(String model, String placeQuantity) throws ServiceException;
	
	void delete(String model) throws ServiceException;
	
	List<Plane> getAllPlanes() throws ServiceException;
	
	Plane getPlaneByModel(String model) throws ServiceException;

}
