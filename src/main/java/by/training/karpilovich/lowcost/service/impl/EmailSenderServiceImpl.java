package by.training.karpilovich.lowcost.service.impl;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import by.training.karpilovich.lowcost.factory.EmailMessageFactory;
import by.training.karpilovich.lowcost.service.EmailSenderService;
import by.training.karpilovich.lowcost.service.util.ServiceUtil;
import by.training.karpilovich.lowcost.util.mail.MailSender;

public class EmailSenderServiceImpl implements EmailSenderService {

	private final EmailMessageFactory messageFactory = EmailMessageFactory.getInstance();

	private final ServiceUtil util = new ServiceUtil();

	private EmailSenderServiceImpl() {
	}

	public static EmailSenderServiceImpl getInstance() {
		return EmailSenderServiceInstanceHolder.INSTANCE;
	}

	@Override
	public void sendGreetingMessage(String address, String firstName, String lastName, Locale locale) {
		if (util.checkEmailAddress(address)) {
			String message = messageFactory.getGreetingMessage(firstName, lastName, locale);
			String subject = messageFactory.getGreetingHeader(locale);
			sendMessage(address, subject, message);
		}
	}

	@Override
	public void sendFlightCancelMessage(List<String> addresses, String flightNumber, Calendar date, Locale locale) {
		if (util.checkEmailAddresses(addresses)) {
			String message = messageFactory.getCancelFlightMessage(flightNumber, date, locale);
			String subject = messageFactory.getCancelFlightSubject(locale);
			Set<String> recepients = new HashSet<>(addresses);
			sendMessage(recepients, subject, message);
		}
	}

	@Override
	public void sendRemindPasswordMessage(String address, String password, Locale locale) {
		if (util.checkEmailAddress(address)) {
			String subject = messageFactory.getRestorePasswordSubject(locale);
			String message = messageFactory.getRestorePasswordMessage(password, locale);
			sendMessage(address, subject, message);
		}
	}

	private static final class EmailSenderServiceInstanceHolder {
		private static final EmailSenderServiceImpl INSTANCE = new EmailSenderServiceImpl();
	}

	private void sendMessage(String recepient, String subject, String message) {
		Set<String> recepients = new HashSet<>();
		recepients.add(recepient);
		sendMessage(recepients, subject, message);
	}

	private void sendMessage(Set<String> recepients, String subject, String message) {
		new Thread(new MailSender(subject, message, recepients)).start();
	}
}