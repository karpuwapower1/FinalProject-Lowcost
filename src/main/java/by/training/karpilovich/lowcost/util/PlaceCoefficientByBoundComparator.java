package by.training.karpilovich.lowcost.util;

import java.util.Comparator;

import by.training.karpilovich.lowcost.entity.PlaceCoefficient;

public class PlaceCoefficientByBoundComparator implements Comparator<PlaceCoefficient> {

	@Override
	public int compare(PlaceCoefficient first, PlaceCoefficient second) {
		
		if (first.equals(second)) {
			return 0;
		}
		if (first.getTo() <= (second.getFrom())) {
			return -1;
		}
		return 1;
	}
}