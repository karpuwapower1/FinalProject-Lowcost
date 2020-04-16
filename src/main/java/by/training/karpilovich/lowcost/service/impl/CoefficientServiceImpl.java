//package by.training.karpilovich.lowcost.service.impl;
//
//import java.math.BigDecimal;
//import java.util.SortedSet;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import by.training.karpilovich.lowcost.builder.coefficient.Director;
//import by.training.karpilovich.lowcost.entity.coefficient.AbstractCoefficient;
//import by.training.karpilovich.lowcost.entity.coefficient.DateCoefficient;
//import by.training.karpilovich.lowcost.entity.coefficient.LuggageCoefficient;
//import by.training.karpilovich.lowcost.entity.coefficient.PlaceCoefficient;
//import by.training.karpilovich.lowcost.exception.ServiceException;
//import by.training.karpilovich.lowcost.service.CoefficientService;
//import by.training.karpilovich.lowcost.util.MessageType;
//
//public class CoefficientServiceImpl implements CoefficientService {
//
//	private static final String LUGGAGE_COEFFICIENT_FLAG = "LUGGAGE";
//	private static final String DATE_COEFFICIENT_FLAG = "DATE";
//	private static final String PLACE_COEFFICIENT_FLAG = "PLACE";
//
//	private static final Logger LOGGER = LogManager.getLogger();
//
//	private CoefficientServiceImpl() {
//
//	}
//
//	private static final class CoefficientServiceImplInstanceHolder {
//		private static final CoefficientServiceImpl INSTANCE = new CoefficientServiceImpl();
//	}
//
//	public static CoefficientServiceImpl getInstance() {
//		return CoefficientServiceImplInstanceHolder.INSTANCE;
//	}
//	
//	@Override
//	public int getMaxToBoundValue(SortedSet<? extends AbstractCoefficient> coefficients) throws ServiceException {
//		
//		AbstractCoefficient coefficient = coefficients.last();
//		int maxBoundToValue = coefficient.getBoundTo();
//		return maxBoundToValue;
//	}
//
//	@Override
//	public DateCoefficient makeDateCoefficientFromParameters(int id, String from, String to, String value)
//			throws ServiceException {
//		return (DateCoefficient) makeCoefficient(id, from, to, value, DATE_COEFFICIENT_FLAG);
//	}
//
//	@Override
//	public PlaceCoefficient makePlaceCoefficientFromParameters(int id, String from, String to, String value)
//			throws ServiceException {
//		return (PlaceCoefficient) makeCoefficient(id, from, to, value, PLACE_COEFFICIENT_FLAG);
//	}
//
//	@Override
//	public LuggageCoefficient makeLuggageCoefficientFromParameters(int id, String from, String to, String value)
//			throws ServiceException {
//		return (LuggageCoefficient) makeCoefficient(id, from, to, value, LUGGAGE_COEFFICIENT_FLAG);
//	}
//
//	private int takeIntFromString(String value) throws ServiceException {
//		try {
//			return Integer.parseInt(value);
//		} catch (NumberFormatException e) {
//			LOGGER.error("Error while parsing String to Integer " + value, e);
//			throw new ServiceException(MessageType.INVALID_NUMBER_FORMAT.getMessage());
//		}
//	}
//
//	private BigDecimal takeBigDecimalFromString(String value) throws ServiceException {
//		try {
//			return new BigDecimal(value);
//		} catch (NumberFormatException e) {
//			LOGGER.error("Error while parsing String to Integer " + value, e);
//			throw new ServiceException(MessageType.INVALID_NUMBER_FORMAT.getMessage());
//		}
//	}
//
//	private AbstractCoefficient makeCoefficient(int id, String from, String to, String value, String flag)
//			throws ServiceException {
//		Director director = new Director();
//		int boundFrom = takeIntFromString(from);
//		int boundTo = takeIntFromString(to);
//		BigDecimal coefficientValue = takeBigDecimalFromString(value);
//		AbstractCoefficient coefficient = null;
//
//		switch (flag) {
//		case LUGGAGE_COEFFICIENT_FLAG:
//			coefficient = director.buildLuggageCoefficient(id, boundFrom, boundTo, coefficientValue);
//		case DATE_COEFFICIENT_FLAG:
//			coefficient = director.buildDateCoefficient(id, boundFrom, boundTo, coefficientValue);
//		case PLACE_COEFFICIENT_FLAG:
//			coefficient = director.buildPlaceCoefficient(id, boundFrom, boundTo, coefficientValue);
//		}
//		return coefficient;
//	}
//
//}
