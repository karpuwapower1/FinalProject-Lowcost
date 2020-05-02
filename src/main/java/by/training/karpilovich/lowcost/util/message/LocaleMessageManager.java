package by.training.karpilovich.lowcost.util.message;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class LocaleMessageManager {

	private static final String BASE_NAME = "pagecontent";
	private static final Logger LOGGER = LogManager.getLogger(LocaleMessageManager.class);

	private LocaleMessageManager() {

	}

	public static Optional<String> getMessage(String key, Locale locale) {
		ResourceBundle bundle = ResourceBundle.getBundle(BASE_NAME, locale);
		Optional<String> value;
		try {
			value = Optional.of(bundle.getString(key));
		} catch (MissingResourceException e) {
			LOGGER.warn("Illegal key " + key);
			value = Optional.empty();
		}
		return value;
	}
}