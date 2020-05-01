package by.training.karpilovich.lowcost.util.mail;

import java.util.Set;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MailSender implements Runnable {

	private static final Logger LOGGER = LogManager.getLogger(MailSender.class);

	private final String messageText;
	private final Set<String> toEmailAddress;
	private final String subject;
	private Message message;
	private final MailSenderPropertiesLoader loader = MailSenderPropertiesLoader.getInstance();

	public MailSender(String subject, String messageText, Set<String> toEmailAddress) {
		this.subject = subject;
		this.messageText = messageText;
		this.toEmailAddress = toEmailAddress;
	}

	@Override
	public void run() {
		try {
			initMessage();
			Transport.send(message);
		} catch (MessagingException e) {
			LOGGER.error("Error while sending a messageText. to=" + toEmailAddress + " messageText=" + messageText, e);
		}
	}

	public void initMessage() throws MessagingException {
		message = new MimeMessage(loader.getSession());
		message.setFrom(new InternetAddress(loader.getSenderEmailAddress()));
		message.setSubject(subject);
		message.setText(messageText);
		setRecipients();
	}

	public void setRecipients() throws MessagingException {
		for (String address : toEmailAddress) {
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(address));
		}
	}
}