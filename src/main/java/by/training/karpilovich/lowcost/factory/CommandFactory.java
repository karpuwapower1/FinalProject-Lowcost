package by.training.karpilovich.lowcost.factory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.CommandType;
import by.training.karpilovich.lowcost.command.impl.AddCityCommand;
import by.training.karpilovich.lowcost.command.impl.AddDateCoefficientCommand;
import by.training.karpilovich.lowcost.command.impl.AddFlightCommand;
import by.training.karpilovich.lowcost.command.impl.AddPlaceCoefficientCommand;
import by.training.karpilovich.lowcost.command.impl.ChangeLanguageCommand;
import by.training.karpilovich.lowcost.command.impl.CreateFlightCommand;
import by.training.karpilovich.lowcost.command.impl.DeleteCityCommand;
import by.training.karpilovich.lowcost.command.impl.SearchFlightCommand;
import by.training.karpilovich.lowcost.command.impl.ShowAllCitiesCommand;
import by.training.karpilovich.lowcost.command.impl.ShowAllFlightsCommand;
import by.training.karpilovich.lowcost.command.impl.SignInCommand;
import by.training.karpilovich.lowcost.command.impl.SignOutCommand;
import by.training.karpilovich.lowcost.command.impl.SignUpCommand;
import by.training.karpilovich.lowcost.command.impl.UpdateCityCommand;
import by.training.karpilovich.lowcost.command.impl.redirect.RedirectToPageCommand;
import by.training.karpilovich.lowcost.command.impl.redirect.RedirectToAddDateCoefficientPageCommand;
import by.training.karpilovich.lowcost.command.impl.redirect.RedirectToAddPlaceCoefficientPageCommand;
import by.training.karpilovich.lowcost.command.impl.redirect.RedirectToCreateFlightPageCommand;
import by.training.karpilovich.lowcost.command.impl.redirect.RedirectToDefaultPageCommand;
import by.training.karpilovich.lowcost.command.impl.redirect.RedirectToUpdateCityPageCommand;

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
			case SHOW_ALL_CITIES:
				command = new ShowAllCitiesCommand();
				break;
			case UPDATE_CITY:
				command = new UpdateCityCommand();
				break;
			case DELETE_CITY:
				command = new DeleteCityCommand();
				break;
			case REDIRECT_TO_UPDATE_CITY_PAGE:
				command = new RedirectToUpdateCityPageCommand();
				break;
			case REDIRECT_TO_CREATE_FLIGTH_PAGE:
				command = new RedirectToCreateFlightPageCommand();
				break;
			case CREATE_FLIGHT:
				command = new CreateFlightCommand();
				break;
			case REDIRECT_TO_ADD_DATE_COEFFICIENT_PAGE:
				command = new RedirectToAddDateCoefficientPageCommand();
				break;
			case ADD_DATE_COEFFICIENT:
				command = new AddDateCoefficientCommand();
				break;
			case REDIRECT_TO_ADD_PLACE_COEFFICIENT_PAGE:
				command = new RedirectToAddPlaceCoefficientPageCommand();
				break;
			case ADD_PLACE_COEFFICIENT:
				command = new AddPlaceCoefficientCommand();
				break;
			case ADD_FLIGHT:
				command = new AddFlightCommand();
				break;
			case SEARCH_FLIGHT:
				command = new SearchFlightCommand();
				break;
			case SHOW_ALL_FLIGHTS:
				command = new ShowAllFlightsCommand();
				break;
			case REDIRECT:
				command = new RedirectToPageCommand();
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
