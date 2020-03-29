package by.training.karpilovich.lowcost.util;

import java.util.Comparator;

import by.training.karpilovich.lowcost.entity.AbstractCoefficient;

public class CoefficientByBoundComparator implements Comparator<AbstractCoefficient> {

	@Override
	public int compare(AbstractCoefficient o1, AbstractCoefficient o2) {
		if (o1.getBoundTo() < o2.getBoundFrom()) {
			return -1;
		} else if (o1.getBoundTo() > o2.getBoundFrom()) {
			return 1;
		}
		return 0;
	}

}
