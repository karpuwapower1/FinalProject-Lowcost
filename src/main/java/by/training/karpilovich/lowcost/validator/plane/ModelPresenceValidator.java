package by.training.karpilovich.lowcost.validator.plane;

import by.training.karpilovich.lowcost.dao.PlaneDAO;
import by.training.karpilovich.lowcost.exception.DAOException;
import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.factory.DAOFactory;
import by.training.karpilovich.lowcost.util.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;

public class ModelPresenceValidator extends Validator {

	private String model;

	public ModelPresenceValidator(String model) {
		this.model = model;
	}

	@Override
	public void validate() throws ValidatorException {
		PlaneDAO planeDAO = DAOFactory.getInstance().getPlaneDAO();
		try {
			if (!planeDAO.getPlaneByModelName(model).isPresent()) {
				throw new ValidatorException(MessageType.NO_SUCH_PLANE_MODEL.getMessage());
			}
			continueValidate();
		} catch (DAOException e) {
			throw new ValidatorException(e.getMessage(), e);
		}
	}

}
