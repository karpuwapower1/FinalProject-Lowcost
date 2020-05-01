package by.training.karpilovich.lowcost.util.mail;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MailSenderPropertiesLoader {

	private static final String PROPERTIES_FILE_PATH = "mail.properties";
	private static final String HOST_PROPERTY_KEY = "mail.smtp.host";
	private static final String PORT_PROPERTY_KEY = "mail.smtp.port";
	private static final String USER_NAME_PROPERTY_KEY = "mail.user.name";
	private static final String USER_PASSWORD_PROPERTY_KEY = "mail.user.password";

	private static final Logger LOGGER = LogManager.getLogger(MailSenderPropertiesLoader.class);

	private Session session;
	private String host;
	private int port;
	private String senderEmailAddress;
	private String senderPassword;

	private MailSenderPropertiesLoader() {
		init();
	}

	private static final class MailSenderloaderInstanceHolder {
		private static final MailSenderPropertiesLoader INSTANCE = new MailSenderPropertiesLoader();
	}

	public static MailSenderPropertiesLoader getInstance() {
		return MailSenderloaderInstanceHolder.INSTANCE;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getSenderEmailAddress() {
		return senderEmailAddress;
	}

	public String getSenderPassword() {
		return senderPassword;
	}

	public Session getSession() {
		return session;
	}

	private void init() {
		try {
			Properties properties = loadProperties();
			initFields(properties);
			initSession(properties);
		} catch (IOException e) {
			LOGGER.error("Error while loading email's properties. ", e);
		}
	}

	private Properties loadProperties() throws IOException {
		Properties properties = new Properties();
		try (InputStream propertiesStream = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE_PATH)) {
			properties.load(propertiesStream);
			return properties;
		}
	}

	private void initFields(Properties properties) {
		host = properties.getProperty(HOST_PROPERTY_KEY);
		port = Integer.parseInt(properties.getProperty(PORT_PROPERTY_KEY));
		senderEmailAddress = properties.getProperty(USER_NAME_PROPERTY_KEY);
		senderPassword = properties.getProperty(USER_PASSWORD_PROPERTY_KEY);
	}

	private void initSession(Properties properties) {
		session = Session.getDefaultInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(senderEmailAddress, senderPassword);
			}

		});
	}
}