package by.training.karpilovich.lowcost.util;

import java.util.Comparator;

import by.training.karpilovich.lowcost.entity.Flight;

public class FlightByDepartureDateComparator implements Comparator<Flight> {

	@Override
	public int compare(Flight first, Flight second) {
		return first.getDate().compareTo(second.getDate());
	}
}