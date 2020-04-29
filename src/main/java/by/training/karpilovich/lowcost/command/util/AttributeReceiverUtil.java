package by.training.karpilovich.lowcost.command.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.entity.DateCoefficient;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.entity.PlaceCoefficient;
import by.training.karpilovich.lowcost.entity.Ticket;
import by.training.karpilovich.lowcost.util.DateCoefficientByBoundComparator;
import by.training.karpilovich.lowcost.util.PlaceCoefficientByBoundComparator;

public class AttributeReceiverUtil {

	public SortedSet<DateCoefficient> takeDateCoefficientFromSession(HttpSession session) {
		SortedSet<DateCoefficient> coefficients = new TreeSet<>(new DateCoefficientByBoundComparator());
		SortedSet<?> objects = (SortedSet<?>) session.getAttribute(Attribute.DATE_COEFFICIENT.toString());
		if (objects != null && !objects.isEmpty()) {
			for (Object object : objects) {
				coefficients.add((DateCoefficient) object);
			}
		}
		return coefficients;
	}

	public SortedSet<PlaceCoefficient> takePlaceCoefficientFromSession(HttpSession session) {
		SortedSet<PlaceCoefficient> coefficients = new TreeSet<>(new PlaceCoefficientByBoundComparator());
		SortedSet<?> objects = (SortedSet<?>) session.getAttribute(Attribute.PLACE_COEFFICIENT.toString());
		if (objects != null && !objects.isEmpty()) {
			for (Object object : objects) {
				coefficients.add((PlaceCoefficient) object);
			}
		}
		return coefficients;
	}
	
	public List<Ticket> takeTicketsFromSession(HttpSession session) {
		List<?> objects = (List<?>) session.getAttribute(Attribute.TICKETS.toString());
		if (objects == null) {
			return new ArrayList<>();
		}
		List<Ticket> tickets = new ArrayList<>(objects.size());
		for (Object object : objects) {
			tickets.add((Ticket) object);
		}
		return tickets;
	}
	
	public List<Flight> takeFlightsFromSession(HttpSession session) {
		Collection<?> objects = (Collection<?>) session.getAttribute(Attribute.FLIGHTS.toString());
		List<Flight> flights = null;
		if (objects != null) {
			flights = new ArrayList<>(objects.size());
			for (Object object : objects) {
				flights.add((Flight) object);
			}
		}
		return flights;
	}
}