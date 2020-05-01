package by.training.karpilovich.lowcost.factory;

import java.util.Calendar;
import java.util.Locale;

import by.training.karpilovich.lowcost.util.DateParser;
import by.training.karpilovich.lowcost.util.mail.MailMessageManager;
import by.training.karpilovich.lowcost.util.mail.MailMessageType;

public class EmailMessageFactory {

	private final MailMessageManager messageManager = new MailMessageManager();

	private EmailMessageFactory() {
	}

	private static final class EmailMessageFactoryInstanceHolder {
		private static final EmailMessageFactory INSTANCE = new EmailMessageFactory();
	}

	public static EmailMessageFactory getInstance() {
		return EmailMessageFactoryInstanceHolder.INSTANCE;
	}

	public String getGreetingMessage(String firstName, String lastName, Locale locale) {
		String key = MailMessageType.GREETING_MESSAGE.getKey();
		return String.format(messageManager.getMessage(key, locale), firstName, lastName);
	}

	public String getGreetingHeader(Locale locale) {
		String key = MailMessageType.GREETING_SUBJECT.getKey();
		return messageManager.getMessage(key, locale);
	}

	public String getCancelFlightSubject(Locale locale) {
		String key = MailMessageType.CANCEL_FLIGHT_SUBJECT.getKey();
		return messageManager.getMessage(key, locale);
	}

	public String getCancelFlightMessage(String flightNumber, Calendar date, Locale locale) {
		String key = MailMessageType.CANCEL_FLIGHT_MESSAGE.getKey();
		return String.format(messageManager.getMessage(key, locale), flightNumber, DateParser.format(date));
	}

	public String getRestorePasswordSubject(Locale locale) {
		String key = MailMessageType.RESTORE_PASSWORD_SUBJECT.getKey();
		return messageManager.getMessage(key, locale);
	}

	public String getRestorePasswordMessage(String password, Locale locale) {
		String key = MailMessageType.RESTORE_PASSWORD_MESSAGE.getKey();
		return String.format(messageManager.getMessage(key, locale), password);
	}
}