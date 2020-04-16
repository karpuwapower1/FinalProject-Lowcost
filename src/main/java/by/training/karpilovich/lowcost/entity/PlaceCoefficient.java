package by.training.karpilovich.lowcost.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class PlaceCoefficient implements Serializable {

	private static final long serialVersionUID = 1L;

	private int from;
	private int to;
	private int flightId;
	private BigDecimal value;

	public PlaceCoefficient() {

	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public int getFlightId() {
		return flightId;
	}

	public void setFlightId(int flightId) {
		this.flightId = flightId;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + flightId;
		result = prime * result + from;
		result = prime * result + to;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		PlaceCoefficient other = (PlaceCoefficient) obj;
		if (flightId != other.flightId)
			return false;
		if (from != other.from)
			return false;
		if (to != other.to)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [from=" + from + ", to=" + to + ", flightId=" + flightId + ", value="
				+ value + "]";
	}
}