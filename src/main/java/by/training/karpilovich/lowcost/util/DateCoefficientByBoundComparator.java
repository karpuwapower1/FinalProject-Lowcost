package by.training.karpilovich.lowcost.util;

import java.util.Comparator;

import by.training.karpilovich.lowcost.entity.DateCoefficient;

public class DateCoefficientByBoundComparator implements Comparator<DateCoefficient> {

	@Override
	public int compare(DateCoefficient first, DateCoefficient second) {
		if (first.equals(second) || first.getFrom().equals(second.getFrom()) || first.getTo().equals(second.getTo())
				|| (first.getFrom().compareTo(second.getFrom()) <= 0 && first.getTo().compareTo(second.getTo()) >= 0) || 
				(first.getFrom().compareTo(second.getFrom()) >= 0 && first.getTo().compareTo(second.getTo()) <= 0)) {
			return 0;
		}
		return first.getTo().compareTo(second.getFrom()) <= 0 ? -1 : 1;
	}
}