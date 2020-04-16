package by.training.karpilovich.lowcost.service.impl;

import java.util.List;
import java.util.Optional;

import by.training.karpilovich.lowcost.builder.PlaneBuilder;
import by.training.karpilovich.lowcost.dao.PlaneDAO;
import by.training.karpilovich.lowcost.entity.Plane;
import by.training.karpilovich.lowcost.exception.DAOException;
import by.training.karpilovich.lowcost.exception.ServiceException;
import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.factory.DAOFactory;
import by.training.karpilovich.lowcost.service.PlaneService;
import by.training.karpilovich.lowcost.util.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;
import by.training.karpilovich.lowcost.validator.plane.ModelAbsenceValidator;
import by.training.karpilovich.lowcost.validator.plane.ModelPresenceValidator;
import by.training.karpilovich.lowcost.validator.plane.ModelValidator;
import by.training.karpilovich.lowcost.validator.plane.PlaceQuantityValidator;

public class PlaneServiceImpl implements PlaneService {

	private PlaneServiceImpl() {

	}

	private static final class PlaneServiceImplInstanceHolder {
		private static final PlaneServiceImpl INSTANCE = new PlaneServiceImpl();
	}

	public static PlaneServiceImpl getInstance() {
		return PlaneServiceImplInstanceHolder.INSTANCE;
	}

	@Override
	public void add(String model, String quantity) throws ServiceException {
		Validator modelValidator = new ModelValidator(model);
		Validator modelAbsenceValidator = new ModelAbsenceValidator(model);
		int placeQuantity = parseStringToInt(quantity);
		Validator placeQuantityVaildator = new PlaceQuantityValidator(placeQuantity);
		modelValidator.setNext(modelAbsenceValidator);
		modelAbsenceValidator.setNext(placeQuantityVaildator);
		PlaneDAO planeDAO = getPlaneDAO();
		try {
			modelValidator.validate();
			planeDAO.addPlane(buildPlane(model, placeQuantity));
		} catch (ValidatorException | DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public void update(String model, String quantity) throws ServiceException {
		int placeQuantity = parseStringToInt(quantity);
		Validator placeQuantityVaildator = new PlaceQuantityValidator(placeQuantity);
		PlaneDAO planeDAO = getPlaneDAO();
		try {
			placeQuantityVaildator.validate();
			Plane updated = getPlaneByModel(model);
			Plane updating = buildPlane(model, placeQuantity);
			planeDAO.updatePlane(updating, updated);
		} catch (ValidatorException | DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(String model) throws ServiceException {
		PlaneDAO planeDAO = getPlaneDAO();
		try {
			Plane deleted = getPlaneByModel(model);
			planeDAO.deletePlane(deleted);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<Plane> getAllPlanes() throws ServiceException {
		PlaneDAO planeDAO = getPlaneDAO();
		try {
			return planeDAO.getAllPlanes();
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public Plane getPlaneByModel(String model) throws ServiceException {
		Validator modelValidator = new ModelValidator(model);
		Validator modelPresenceValidator = new ModelPresenceValidator(model);
		modelValidator.setNext(modelPresenceValidator);
		try {
			modelValidator.validate();
			PlaneDAO planeDAO = getPlaneDAO();
			Optional<Plane> optional = planeDAO.getPlaneByModelName(model);
			if (optional.isPresent()) {
				return optional.get();
			}
			throw new ServiceException(MessageType.NO_SUCH_PLANE_MODEL.getMessage());
		} catch (ValidatorException | DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	private Plane buildPlane(String model, int placeQuantity) {
		PlaneBuilder builder = new PlaneBuilder();
		builder.setPlaneModel(model);
		builder.setPlanePlaceQuantity(placeQuantity);
		return builder.getPlane();
	}

	private int parseStringToInt(String value) throws ServiceException {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new ServiceException(MessageType.INVALID_NUMBER_FORMAT.getMessage());
		}
	}

	private PlaneDAO getPlaneDAO() {
		DAOFactory factory = DAOFactory.getInstance();
		return factory.getPlaneDAO();
	}
}