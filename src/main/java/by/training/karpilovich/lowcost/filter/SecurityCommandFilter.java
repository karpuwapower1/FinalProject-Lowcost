package by.training.karpilovich.lowcost.filter;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.CommandType;
import by.training.karpilovich.lowcost.command.JSPParameter;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.entity.Role;
import by.training.karpilovich.lowcost.factory.CommandFactory;

public class SecurityCommandFilter implements Filter {

	private CommandFactory factory;

	public void init() {
		factory = CommandFactory.getInstance();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		if (!isCommandAccordsToRole(httpRequest)) {
			request.getRequestDispatcher(Page.DEFAULT.getAddress()).forward(httpRequest, response);
			return;
		}
		chain.doFilter(httpRequest, response);
	}

	private boolean isCommandAccordsToRole(HttpServletRequest request) {
		Role role = getRoleFromSession(request);
		Optional<String> command = getComamndFromRequest(request);
		boolean isAccording = false;
		if (command.isPresent()) {
			CommandType type = getCommandType(command.get());
			isAccording = isCammndAccordsToRole(type, role);
		}
		return isAccording;
	}

	private Role getRoleFromSession(HttpServletRequest request) {
		return (Role) request.getSession().getAttribute(Attribute.USER_ROLE.toString());
	}

	private Optional<String> getComamndFromRequest(HttpServletRequest request) {
		String command = request.getParameter(JSPParameter.COMMAND);
		Optional<String> optional = Optional.empty();
		if (command != null && !command.isEmpty()) {
			optional = Optional.of(command);
		}
		return optional;
	}

	private CommandType getCommandType(String command) {
		try {
			return CommandType.valueOf(command.toUpperCase());
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	private boolean isCammndAccordsToRole(CommandType type, Role role) {
		return factory.getCommandToTole(role).contains(type);
	}
}