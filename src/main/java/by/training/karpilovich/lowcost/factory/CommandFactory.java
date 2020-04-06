package by.training.karpilovich.lowcost.factory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.CommandType;
import by.training.karpilovich.lowcost.command.impl.AddCityCommand;
import by.training.karpilovich.lowcost.command.impl.ChangeLanguageCommand;
import by.training.karpilovich.lowcost.command.impl.RedirectCommand;
import by.training.karpilovich.lowcost.command.impl.RedirectToDefaultPageCommand;
import by.training.karpilovich.lowcost.command.impl.SearchFlightCommand;
import by.training.karpilovich.lowcost.command.impl.SignInCommand;
import by.training.karpilovich.lowcost.command.impl.SignOutCommand;

import by.training.karpilovich.lowcost.command.impl.SignUpCommand;

public class CommandFactory {

	private static final Logger LOGGER = LogManager.getLogger(CommandFactory.class);

	private CommandFactory() {
	}

	private static final class CommandFactoryInstanceHolder {
		private static final CommandFactory INSTANCE = new CommandFactory();
	}

	public static CommandFactory getInstance() {
		return CommandFactoryInstanceHolder.INSTANCE;
	}

	public Command getCommad(String commandName) {
		LOGGER.debug(commandName);
		Command command = new RedirectToDefaultPageCommand();
		if (commandName == null || commandName.isEmpty()) {
			LOGGER.debug("illegal command ");
			return command;
		}
		try {
			CommandType commandType = CommandType.valueOf(commandName.toUpperCase());
			switch (commandType) {
			case SIGN_IN:
				command = new SignInCommand();
				break;
			case SIGN_UP:
				command = new SignUpCommand();
				break;
			case SIGN_OUT:
				command = new SignOutCommand();
				break;
			case ADD_CITY:
				command = new AddCityCommand();
				break;
			case SEARCH_FLIGHT:
				command = new SearchFlightCommand();
				break;
			case REDIRECT:
				command = new RedirectCommand();
				break;
			case CHANGE_LANGUAGE:
				command = new ChangeLanguageCommand();
				break;
			default:
				break;
			}
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e);
		}
		return command;
	}

}
