package by.training.karpilovich.lowcost.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

public class Ticket implements Serializable {

	private static final long serialVersionUID = 1L;

	private String email;
	private long number;
	private Flight flight;
	private Calendar purchaseDate;
	private BigDecimal price;
	private String passengerFirstName;
	private String passengerLastName;
	private String passengerPassportNumber;
	private int luggageQuantity;
	private BigDecimal overweightLuggagePrice;
	private boolean primaryBoargingRight;

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public Calendar getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Calendar purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getPassengerFirstName() {
		return passengerFirstName;
	}

	public void setPassengerFirstName(String passengerFirstName) {
		this.passengerFirstName = passengerFirstName;
	}

	public String getPassengerLastName() {
		return passengerLastName;
	}

	public void setPassengerLastName(String passengerLastName) {
		this.passengerLastName = passengerLastName;
	}

	public String getPassengerPassportNumber() {
		return passengerPassportNumber;
	}

	public void setPassengerPassportNumber(String passengerPassportNumber) {
		this.passengerPassportNumber = passengerPassportNumber;
	}

	public int getLuggageQuantity() {
		return luggageQuantity;
	}

	public void setLuggageQuantity(int luggageQuantity) {
		this.luggageQuantity = luggageQuantity;
	}

	public boolean isPrimaryBoargingRight() {
		return primaryBoargingRight;
	}

	public void setPrimaryBoargingRight(boolean primaryBoargingRight) {
		this.primaryBoargingRight = primaryBoargingRight;
	}

	public BigDecimal getOverweightLuggagePrice() {
		return overweightLuggagePrice;
	}

	public void setOverweightLuggagePrice(BigDecimal overweightLuggagePrice) {
		this.overweightLuggagePrice = overweightLuggagePrice;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((flight == null) ? 0 : flight.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + luggageQuantity;
		result = prime * result + (int) number;
		result = prime * result + ((overweightLuggagePrice == null) ? 0 : overweightLuggagePrice.hashCode());
		result = prime * result + ((passengerFirstName == null) ? 0 : passengerFirstName.hashCode());
		result = prime * result + ((passengerLastName == null) ? 0 : passengerLastName.hashCode());
		result = prime * result + ((passengerPassportNumber == null) ? 0 : passengerPassportNumber.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + (primaryBoargingRight ? 1231 : 1237);
		result = prime * result + ((purchaseDate == null) ? 0 : purchaseDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ticket other = (Ticket) obj;
		if (flight == null) {
			if (other.flight != null)
				return false;
		} else if (!flight.equals(other.flight))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (luggageQuantity != other.luggageQuantity)
			return false;
		if (number != other.number)
			return false;
		if (overweightLuggagePrice == null) {
			if (other.overweightLuggagePrice != null)
				return false;
		} else if (!overweightLuggagePrice.equals(other.overweightLuggagePrice))
			return false;
		if (passengerFirstName == null) {
			if (other.passengerFirstName != null)
				return false;
		} else if (!passengerFirstName.equals(other.passengerFirstName))
			return false;
		if (passengerLastName == null) {
			if (other.passengerLastName != null)
				return false;
		} else if (!passengerLastName.equals(other.passengerLastName))
			return false;
		if (passengerPassportNumber == null) {
			if (other.passengerPassportNumber != null)
				return false;
		} else if (!passengerPassportNumber.equals(other.passengerPassportNumber))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (primaryBoargingRight != other.primaryBoargingRight)
			return false;
		if (purchaseDate == null) {
			if (other.purchaseDate != null)
				return false;
		} else if (!purchaseDate.equals(other.purchaseDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [email=" + email + ", number=" + number + ", flight=" + flight
				+ ", purchaseDate=" + purchaseDate + ", price=" + price + ", passengerFirstName=" + passengerFirstName
				+ ", passengerLastName=" + passengerLastName + ", passengerPassportNumber=" + passengerPassportNumber
				+ ", luggageQuantity=" + luggageQuantity + ", overweightLuggagePrice=" + overweightLuggagePrice
				+ ", primaryBoargingRight=" + primaryBoargingRight + "]";
	}
}