package by.training.karpilovich.lowcost.util;

import java.util.Comparator;

import by.training.karpilovich.lowcost.entity.PlaceCoefficient;

public class PlaceCoefficientByBoundComparator implements Comparator<PlaceCoefficient> {

	@Override
	public int compare(PlaceCoefficient first, PlaceCoefficient second) {

		if (first.equals(second) || first.getFrom() == second.getFlightId() || first.getTo() == second.getTo()
				|| (first.getFrom() <= second.getFrom() && first.getTo() >= second.getTo())
				|| (first.getFrom() >= second.getFrom() && first.getTo() <= second.getTo())) {
			return 0;
		}
		return first.getTo() <= second.getFrom() ? -1 : 1;
	}
}