package by.training.karpilovich.lowcost.command.util;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.entity.DateCoefficient;
import by.training.karpilovich.lowcost.entity.PlaceCoefficient;
import by.training.karpilovich.lowcost.util.DateCoefficientByBoundComparator;
import by.training.karpilovich.lowcost.util.PlaceCoefficientByBoundComparator;

public abstract class CoefficientCreatorHelper {

	protected SortedSet<DateCoefficient> getDateCoefficientFromSession(HttpSession session) {
		SortedSet<DateCoefficient> coefficients = new TreeSet<>(new DateCoefficientByBoundComparator());
		SortedSet<?> objects = (SortedSet<?>) session.getAttribute(Attribute.DATE_COEFFICIENT.toString());
		if (objects != null && !objects.isEmpty()) {
			for (Object object : objects) {
				coefficients.add((DateCoefficient) object);
			}
		}
		return coefficients;
	}

	protected SortedSet<PlaceCoefficient> getPlaceCoefficientFromSession(HttpSession session) {
		SortedSet<PlaceCoefficient> coefficients = new TreeSet<>(new PlaceCoefficientByBoundComparator());
		SortedSet<?> objects = (SortedSet<?>) session.getAttribute(Attribute.PLACE_COEFFICIENT.toString());
		if (objects != null && !objects.isEmpty()) {
			for (Object object : objects) {
				coefficients.add((PlaceCoefficient) object);
			}
		}
		return coefficients;
	}
}