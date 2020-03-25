package by.training.karpilovich.lowcost.command;

import java.io.IOException;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.karpilovich.lowcost.util.MessageManager;

public interface Command {
	
	default void setErrorMessage(HttpServletRequest request, Locale locale, String key) {
		Optional<String> message = MessageManager.getMessage(key, locale);
		if (message.isPresent()) {
			request.setAttribute(AttributeName.ERROR_MESSAGE.getName(), message.get());
		}
	}
	
	String exequte(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
