package by.training.karpilovich.lowcost.util;

import java.util.Comparator;

import by.training.karpilovich.lowcost.entity.Flight;

public class FlightByIdComparator implements Comparator<Flight> {

	@Override
	public int compare(Flight first, Flight second) {
		if (first.getId() < second.getId()) {
			return -1;
		} else if (first.getId() > second.getId()) {
			return 1;
		}
		return 0;
	}
}