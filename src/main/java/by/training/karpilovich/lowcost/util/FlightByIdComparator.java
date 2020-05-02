package by.training.karpilovich.lowcost.util;

import java.util.Comparator;

import by.training.karpilovich.lowcost.entity.Flight;

public class FlightByIdComparator implements Comparator<Flight> {

	@Override
	public int compare(Flight first, Flight second) {
		if (first.getId() == second.getId()) {
			return 0;
		}
		return first.getId() < second.getId() ? -1 : 1;
	}
}