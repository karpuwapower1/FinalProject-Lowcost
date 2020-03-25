package by.training.karpilovich.lowcost.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

public class Flight implements Serializable {

	private static final long serialVersionUID = 1L;

	private String number;
	private PlaneModel planeModel;
	private City from;
	private City to;
	private Calendar date;
	private BigDecimal price;
	private int permittedLuggageWeigth;
	private int availablePlaceQuantity;

	public Flight() {
	}

	public Flight(String number, PlaneModel planeModel, City from, City to, Calendar date, BigDecimal price,
			int permittedLuggageWeigth, int availablePlaceQuantity) {
		this.number = number;
		this.planeModel = planeModel;
		this.from = from;
		this.to = to;
		this.date = date;
		this.price = price;
		this.permittedLuggageWeigth = permittedLuggageWeigth;
		this.availablePlaceQuantity = availablePlaceQuantity;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public PlaneModel getPlaneModel() {
		return planeModel;
	}

	public void setPlaneModel(PlaneModel planeModel) {
		this.planeModel = planeModel;
	}

	public City getFrom() {
		return from;
	}

	public void setFrom(City from) {
		this.from = from;
	}

	public City getTo() {
		return to;
	}

	public void setTo(City to) {
		this.to = to;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getPermittedLuggageWeigth() {
		return permittedLuggageWeigth;
	}

	public void setPermittedLuggageWeigth(int permittedLuggageWeigth) {
		this.permittedLuggageWeigth = permittedLuggageWeigth;
	}

	public int getAvailablePlaceQuantity() {
		return availablePlaceQuantity;
	}

	public void setAvailablePlaceQuantity(int availablePlaceQuantity) {
		this.availablePlaceQuantity = availablePlaceQuantity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + availablePlaceQuantity;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + permittedLuggageWeigth;
		result = prime * result + ((planeModel == null) ? 0 : planeModel.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Flight other = (Flight) obj;
		if (availablePlaceQuantity != other.availablePlaceQuantity)
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (permittedLuggageWeigth != other.permittedLuggageWeigth)
			return false;
		if (planeModel == null) {
			if (other.planeModel != null)
				return false;
		} else if (!planeModel.equals(other.planeModel))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [number=" + number + ", planeModel=" + planeModel + ", from="
				+ from.toString() + ", to=" + to.toString() + ", date=" + date + ", price=" + price
				+ ", permittedLuggageWeigth=" + permittedLuggageWeigth + ", availablePlaceQuantity="
				+ availablePlaceQuantity + "]";
	}

}
