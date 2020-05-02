package by.training.karpilovich.lowcost.validator.plane;

import by.training.karpilovich.lowcost.dao.PlaneDAO;
import by.training.karpilovich.lowcost.exception.DAOException;
import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.factory.DAOFactory;
import by.training.karpilovich.lowcost.util.message.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;

public class ModelAbsenceValidator extends Validator {
	
	private String model;

	public ModelAbsenceValidator(String model) {
		this.model = model;
	}

	@Override
	public void validate() throws ValidatorException {
		PlaneDAO planeDAO = DAOFactory.getInstance().getPlaneDAO();
		try {
			if (planeDAO.getPlaneByModelName(model).isPresent()) {
				throw new ValidatorException(MessageType.PLANE_MODEL_ALREADY_PRESENTS.getMessage());
			}
			continueValidate();
		} catch (DAOException e) {
			throw new ValidatorException(e.getMessage(), e);
		}
	}

}
