package by.training.karpilovich.lowcost.util.mail;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MailMessageManager {
	
	private static final Logger LOGGER = LogManager.getLogger(MailMessageManager.class);
	
	private static final String BASE_NAME = "messages";

	public String getMessage(String key, Locale locale) {
		ResourceBundle bundle = ResourceBundle.getBundle(BASE_NAME, locale);
		try {
			return bundle.getString(key);
		} catch (MissingResourceException e) {
			LOGGER.warn("Illegal key " + key);
			return "";
		}
	}
}