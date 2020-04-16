package by.training.karpilovich.lowcost.util;

public enum MessageType {
	
	ILLEGAL_EMAIL("message.error.initialization.invalid_email"),
	ILLEGAL_NAME("message.error.initialization.invalid_name"),
	ILLEGAL_PASSWORD("message.error.initialization.invalid_password"),
	PASSWORDS_NOT_MATCH("message.error.initialization.diffrent_password"),
	USER_IS_NULL("message.error.null_user"),
	INTERNAL_ERROR("message.error.internal"),
	ADMIN_DELETING("message.error.admin_deleting"),
	ILLEGAL_EMAIL_OR_PASSWORD_MESSAGE("message.error.initialization.invalid_email_password"),
	EMAIL_ALREADY_PRESENT("message.error.email_already_present"),
	
	INVALID_CITY("message.error.invalid_city"),
	INVALID_DATE("message.error.invalid_date"),
	INVALID_PRICE("message.error.invalid_price"),
	INVALID_PASSEGER_QUANTITY("message.error.invalid_passenger_quantity"),
	INVALID_DEFAULT_LUGGAGE("message.error.invalid_default_luggage"),
	
	INVALID_PLANE_MODEL("message.error.invalid_plane_model"),
	INVALID_PLANE_PASSENGER_QUANTITY("message.error.invalid_plane_passenger_quntity"),
	NO_SUCH_PLANE_MODEL("message.error.no_plane_model"),
	PLANE_MODEL_ALREADY_PRESENTS("message.error.plane_model_already_present"),
	
	
	INVALID_CITY_NAME("message.error.invalid_city_name"),
	INVALID_COUNTRY_NAME("message.error.invalid_contry_name"),
	CITY_ALREADY_PRESENTS("message.error.city_already_presents"),
	CITY_CAN_NOT_BE_DELETED("message.error.city_can_not_be_deleted"),
	
	INVALID_NUMBER_FORMAT("message.error.invalid_number_format"),
	
	INVALID_FLIGHT_NUMBER("message.error.invalid_flight_number"),
	FLIGHT_NUMBER_AND_DATE_PRESENT_ALREADY("message.error.flight_with_number_and_date_present"),
	NULL_FLIGHT("message.error.null_flight"),
	
	INVALID_COEFFICINET_VALUE("message.error.invalid_coefficient_value"),
	INVALID_COEFFICINET_BOUND_FROM("message.error.invalid_coefficient_bound_from"),
	INVALID_COEFFICINET_BOUND_TO("message.error.invalid_coefficient_bound_to"),
	
	INSUFFICIENT_FUNDS("message.error.insufficient_funds"),
	INVALID_AMOUNT("message.error.invalid_amount");
	
	private String type;
	
	private MessageType(String type) {
		this.type = type;
	}
	
	public String getMessage() {
		return type;
	}

}
