package by.training.karpilovich.lowcost.entity;

import java.io.Serializable;
import java.util.Calendar;

public class LuggageCoefficient implements Serializable {

	private static final long serialVersionUID = 1L;

	private String flightNumber;
	private Calendar date;
	private int weightFrom;
	private int weightTo;

	public LuggageCoefficient() {
	}

	public LuggageCoefficient(String flightNumber, Calendar date, int weightFrom, int weightTo) {
		this.flightNumber = flightNumber;
		this.date = date;
		this.weightFrom = weightFrom;
		this.weightTo = weightTo;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public int getWeightFrom() {
		return weightFrom;
	}

	public void setWeightFrom(int weightFrom) {
		this.weightFrom = weightFrom;
	}

	public int getWeightTo() {
		return weightTo;
	}

	public void setWeightTo(int weightTo) {
		this.weightTo = weightTo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((flightNumber == null) ? 0 : flightNumber.hashCode());
		result = prime * result + weightFrom;
		result = prime * result + weightTo;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		LuggageCoefficient other = (LuggageCoefficient) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (flightNumber == null) {
			if (other.flightNumber != null)
				return false;
		} else if (!flightNumber.equals(other.flightNumber))
			return false;
		if (weightFrom != other.weightFrom)
			return false;
		if (weightTo != other.weightTo)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LuggageCoefficient [flightNumber=" + flightNumber + ", date=" + date + ", weightFrom=" + weightFrom
				+ ", weightTo=" + weightTo + "]";
	}

}
