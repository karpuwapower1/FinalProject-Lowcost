package by.training.karpilovich.lowcost.command;

public enum Page {

	DEFAULT("/index.jsp"), SIGN_IN("/jsp/signin.jsp"), SIGN_UP("/jsp/signup.jsp"), ADD_CITY("/jsp/add_city.jsp"),
	ALL_CITIES("/jsp/all_cities.jsp"), UPDATE_CITY("/jsp/update_city.jsp"), CREATE_FLIGHT("/jsp/create_flight.jsp"), 
	FLIGHT_PREVIEW("/jsp/flight_preview.jsp"), ADD_PLACE_COEFFICIENT("/jsp/add_place_coefficient.jsp"),
	ADD_DATE_COEFFICIENT("/jsp/add_date_coefficient.jsp"),
	RESULT("/jsp/show_flights.jsp"), INTERNAL_ERROR("TODO");

	private String address;

	private Page(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}
}