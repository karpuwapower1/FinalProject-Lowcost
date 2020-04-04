package by.training.karpilovich.lowcost.entity.coefficient;

import java.io.Serializable;
import java.math.BigDecimal;

public class AbstractCoefficient implements Serializable {

	private static final long serialVersionUID = 1L;

	private int flightId;
	private int boundFrom;
	private int boundTo;
	private BigDecimal value;

	public AbstractCoefficient() {
	}

	public AbstractCoefficient(int flightId, int boundFrom, int boundTo, BigDecimal value) {
		this.flightId = flightId;
		this.boundFrom = boundFrom;
		this.boundTo = boundTo;
		this.value = value;
	}

	public int getFlightId() {
		return flightId;
	}

	public void setFlightId(int flightId) {
		this.flightId = flightId;
	}

	public int getBoundFrom() {
		return boundFrom;
	}

	public void setBoundFrom(int boundFrom) {
		this.boundFrom = boundFrom;
	}

	public int getBoundTo() {
		return boundTo;
	}

	public void setBoundTo(int boundTo) {
		this.boundTo = boundTo;
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
		int result = flightId;
		result = prime * result + boundFrom;
		result = prime * result + boundTo;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		AbstractCoefficient other = (AbstractCoefficient) obj;
		if (boundFrom != other.boundFrom)
			return false;
		if (boundTo != other.boundTo)
			return false;
		if (flightId != other.flightId)
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
		return getClass().getSimpleName() + " [flightId=" + flightId + ", boundFrom=" + boundFrom + ", boundTo="
				+ boundTo + ", value=" + value + "]";
	}

}