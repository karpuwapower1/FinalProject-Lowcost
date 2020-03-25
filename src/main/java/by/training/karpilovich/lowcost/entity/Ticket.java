package by.training.karpilovich.lowcost.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

public class Ticket implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String number;
	private String flightNumber;
	private Calendar departureDate;
	private Calendar purchaseDate;
	private BigDecimal price;
	private String passengerFirstName;
	private String passengerLastName;
	private String passengerPassportNumber;
	private int luggageQuantity;
	private boolean paied;
	private boolean available;
	private boolean primaryBoargingRight;

	public Ticket() {
	}

	public Ticket(String number, String flightNumber, Calendar departureDate, Calendar purchaseDate, BigDecimal price,
			String passengerFirstName, String passengerLastName, String passengerPassportNumber, int luggageQuantity,
			boolean paied, boolean available, boolean primaryBoargingRight) {
		super();
		this.number = number;
		this.flightNumber = flightNumber;
		this.departureDate = departureDate;
		this.purchaseDate = purchaseDate;
		this.price = price;
		this.passengerFirstName = passengerFirstName;
		this.passengerLastName = passengerLastName;
		this.passengerPassportNumber = passengerPassportNumber;
		this.luggageQuantity = luggageQuantity;
		this.paied = paied;
		this.available = available;
		this.primaryBoargingRight = primaryBoargingRight;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public Calendar getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Calendar departureDate) {
		this.departureDate = departureDate;
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

	public boolean isPaied() {
		return paied;
	}

	public void setPaied(boolean paied) {
		this.paied = paied;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public boolean isPrimaryBoargingRight() {
		return primaryBoargingRight;
	}

	public void setPrimaryBoargingRight(boolean primaryBoargingRight) {
		this.primaryBoargingRight = primaryBoargingRight;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (available ? 1231 : 1237);
		result = prime * result + ((departureDate == null) ? 0 : departureDate.hashCode());
		result = prime * result + ((flightNumber == null) ? 0 : flightNumber.hashCode());
		result = prime * result + luggageQuantity;
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + (paied ? 1231 : 1237);
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
		if (obj == null || getClass() != obj.getClass())
			return false;
		Ticket other = (Ticket) obj;
		if (available != other.available)
			return false;
		if (departureDate == null) {
			if (other.departureDate != null)
				return false;
		} else if (!departureDate.equals(other.departureDate))
			return false;
		if (flightNumber == null) {
			if (other.flightNumber != null)
				return false;
		} else if (!flightNumber.equals(other.flightNumber))
			return false;
		if (luggageQuantity != other.luggageQuantity)
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (paied != other.paied)
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
		return getClass().getSimpleName() + " [number=" + number + ", flightNumber=" + flightNumber + ", departureDate="
				+ departureDate + ", purchaseDate=" + purchaseDate + ", price=" + price + ", passengerFirstName="
				+ passengerFirstName + ", passengerLastName=" + passengerLastName + ", passengerPassportNumber="
				+ passengerPassportNumber + ", luggageQuantity=" + luggageQuantity + ", paied=" + paied + ", available="
				+ available + ", primaryBoargingRight=" + primaryBoargingRight + "]";
	}

}
