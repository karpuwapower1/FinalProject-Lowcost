package by.training.karpilovich.lowcost.command;

public enum CommandType {
	//user command
	SIGN_IN, SIGN_OUT, SIGN_UP, DEPOSIT, DELETE_USER, UPDATE_PASSWORD, 
	//city command
	ADD_CITY, SHOW_ALL_CITIES, DELETE_CITY, REDIRECT_TO_UPDATE_CITY_PAGE, UPDATE_CITY,
	//flight command
	REDIRECT_TO_CREATE_FLIGTH_PAGE, CREATE_FLIGHT, REDIRECT_TO_ADD_PLACE_COEFFICIENT_PAGE, REDIRECT_TO_ADD_DATE_COEFFICIENT_PAGE,
	ADD_PLACE_COEFFICIENT, ADD_DATE_COEFFICIENT, ADD_FLIGHT,  CANCEL_FLIGHT_ADDING, SHOW_ALL_FLIGHTS, SEARCH_FLIGHT,
	SORT_FLIGHTS_BY_DEPARTURE_DATE, SORT_FLIGHTS_BY_TICKET_PRICE, REDIRECT_TO_UPDATE_FLIGHT_PAGE, UPDATE_FLIGHT, DELETE_FLIGHT,
	SHOW_NEXT_TWENTY_FOUR_HOURS_FLIGHTS, SHOW_FLIGHTS_BETWEEN_DATES,
	//ticket
	REDIRECT_TO_CREATE_TICKET_PAGE, CREATE_TICKET, BUY_TICKET, SHOW_ALL_TICKET, RETURN_TICKET, SHOW_SOLD_TICKETS,
	//plane
	ADD_PLANE, SHOW_ALL_PLANES,
	//other
	REDIRECT, CHANGE_LANGUAGE;
}