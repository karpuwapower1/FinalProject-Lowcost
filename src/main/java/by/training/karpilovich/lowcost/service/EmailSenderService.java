package by.training.karpilovich.lowcost.service;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Describes a service to sending mails
 * 
 * @author Aliaksei Karpilovich
 *
 */
public interface EmailSenderService {

	/**
	 * Sends message that welcome user.
	 * 
	 * @param address   address the message is send to
	 * @param firstName recipient's first name
	 * @param lastName  recipient's last name
	 * @param locale    user's locale that describes message's language
	 */
	void sendGreetingMessage(String address, String firstName, String lastName, Locale locale);

	/**
	 * Sends a message about flight cancellation
	 * 
	 * @param addresses    user's addresses who poses ticket on cancelled flight
	 * @param flightNumber the cancelled flight's number
	 * @param date         the cancelled flight's
	 * @param locale       user's locale that describes message's language
	 */
	void sendFlightCancelMessage(List<String> addresses, String flightNumber, Calendar date, Locale locale);

	/**
	 * Sends a user's password
	 * 
	 * @param address  forgot password user email address
	 * @param password the user's password
	 * @param locale   user's locale that describes message's language
	 */
	void sendRemindPasswordMessage(String address, String password, Locale locale);
}