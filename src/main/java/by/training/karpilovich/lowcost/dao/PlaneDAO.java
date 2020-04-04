package by.training.karpilovich.lowcost.dao;

import java.util.List;
import java.util.Optional;

import by.training.karpilovich.lowcost.entity.Plane;
import by.training.karpilovich.lowcost.exception.DAOException;

public interface PlaneDAO {
	
	void addPlane(Plane plane) throws DAOException;
	
	void updatePlane(Plane plane, Plane update) throws DAOException;
	
	void deletePlane(Plane plane) throws DAOException;
	
	List<Plane> getAllPlanes() throws DAOException;
	
	Optional<Plane> getPlaneByModelName(String model) throws DAOException;

}
