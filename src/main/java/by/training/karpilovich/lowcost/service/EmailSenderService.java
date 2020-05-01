package by.training.karpilovich.lowcost.service;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public interface EmailSenderService {

	void sendGreetingMessage(String address, String firstName, String lastName, Locale locale);

	void sendFlightCancelMessage(List<String> addresses, String flightNumber, Calendar date, Locale locale);

	void sendRemindPasswordMessage(String address, String password, Locale locale);
}