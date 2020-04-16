package by.training.karpilovich.lowcost.util;

import java.util.Comparator;

import by.training.karpilovich.lowcost.entity.DateCoefficient;

public class DateCoefficientByBoundComparator implements Comparator<DateCoefficient> {

	@Override
	public int compare(DateCoefficient first, DateCoefficient second) {
		
		if (first.equals(second)) {
			return 0;
		}
		if (first.getTo().compareTo(second.getFrom()) <= 0) {
			return -1;
		}
		return 1;
	}
}