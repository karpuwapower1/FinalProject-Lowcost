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
import by.training.karpilovich.lowcost.service.util.ServiceUtil;
import by.training.karpilovich.lowcost.util.message.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;
import by.training.karpilovich.lowcost.validator.plane.ModelAbsenceValidator;
import by.training.karpilovich.lowcost.validator.plane.ModelValidator;
import by.training.karpilovich.lowcost.validator.plane.PlaceQuantityValidator;

public class PlaneServiceImpl implements PlaneService {

	private PlaneDAO planeDAO = DAOFactory.getInstance().getPlaneDAO();
	private ServiceUtil serviceUtil = new ServiceUtil();

	private PlaneServiceImpl() {
	}

	private static final class PlaneServiceImplInstanceHolder {
		private static final PlaneServiceImpl INSTANCE = new PlaneServiceImpl();
	}

	public static PlaneServiceImpl getInstance() {
		return PlaneServiceImplInstanceHolder.INSTANCE;
	}

	@Override
	public void add(String model, String placeQuantity) throws ServiceException {
		int quantity = serviceUtil.takeIntFromString(placeQuantity);
		Validator validator = createPlaneValidator(model, quantity);
		try {
			validator.validate();
			planeDAO.addPlane(buildPlane(model, quantity));
		} catch (ValidatorException | DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(String model) throws ServiceException {
		try {
			Plane deleted = getPlaneByModel(model);
			planeDAO.deletePlane(deleted);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<Plane> getAllPlanes() throws ServiceException {
		try {
			return planeDAO.getAllPlanes();
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public Plane getPlaneByModel(String model) throws ServiceException {
		Validator modelValidator = new ModelValidator(model);
		try {
			modelValidator.validate();
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

	private Validator createPlaneValidator(String model, int placeQuantity) {
		Validator validator = new ModelValidator(model);
		Validator modelAbsenceValidator = new ModelAbsenceValidator(model);
		Validator placeQuantityVaildator = new PlaceQuantityValidator(placeQuantity);
		validator.setNext(modelAbsenceValidator);
		modelAbsenceValidator.setNext(placeQuantityVaildator);
		return validator;
	}
}