package by.training.karpilovich.lowcost.factory;

import java.util.EnumMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.CommandType;
import by.training.karpilovich.lowcost.command.impl.AddCityCommand;
import by.training.karpilovich.lowcost.command.impl.AddDateCoefficientCommand;
import by.training.karpilovich.lowcost.command.impl.AddFlightCommand;
import by.training.karpilovich.lowcost.command.impl.AddPlaceCoefficientCommand;
import by.training.karpilovich.lowcost.command.impl.AddPlaneCommand;
import by.training.karpilovich.lowcost.command.impl.BuyTicketCommand;
import by.training.karpilovich.lowcost.command.impl.CancelFlightAddingCommand;
import by.training.karpilovich.lowcost.command.impl.ChangeLanguageCommand;
import by.training.karpilovich.lowcost.command.impl.CreateFlightCommand;
import by.training.karpilovich.lowcost.command.impl.CreateTicketCommand;
import by.training.karpilovich.lowcost.command.impl.DeleteCityCommand;
import by.training.karpilovich.lowcost.command.impl.DeleteFlightCommand;
import by.training.karpilovich.lowcost.command.impl.DeleteUserCommand;
import by.training.karpilovich.lowcost.command.impl.DepositCommand;
import by.training.karpilovich.lowcost.command.impl.ReturnTicketCommand;
import by.training.karpilovich.lowcost.command.impl.SearchFlightCommand;
import by.training.karpilovich.lowcost.command.impl.ShowAllCitiesCommand;
import by.training.karpilovich.lowcost.command.impl.ShowAllFlightsCommand;
import by.training.karpilovich.lowcost.command.impl.ShowAllPlanesCommand;
import by.training.karpilovich.lowcost.command.impl.ShowAllTicketCommand;
import by.training.karpilovich.lowcost.command.impl.ShowAllTicketToFlightCommand;
import by.training.karpilovich.lowcost.command.impl.ShowFlightsBetweenDatesCommand;
import by.training.karpilovich.lowcost.command.impl.ShowNextTwentyFourHoursFlightsCommand;
import by.training.karpilovich.lowcost.command.impl.SignInCommand;
import by.training.karpilovich.lowcost.command.impl.SignOutCommand;

import by.training.karpilovich.lowcost.command.impl.SignUpCommand;
import by.training.karpilovich.lowcost.command.impl.SortFlightsByDateCommand;
import by.training.karpilovich.lowcost.command.impl.SortFlightsByPriceCommand;
import by.training.karpilovich.lowcost.command.impl.UpdateCityCommand;
import by.training.karpilovich.lowcost.command.impl.UpdateFlightCommand;
import by.training.karpilovich.lowcost.command.impl.redirect.RedirectToAddDateCoefficientPageCommand;
import by.training.karpilovich.lowcost.command.impl.redirect.RedirectToAddPlaceCoefficientPageCommand;
import by.training.karpilovich.lowcost.command.impl.redirect.RedirectToCreateFlightPageCommand;
import by.training.karpilovich.lowcost.command.impl.redirect.RedirectToCreateTicketPageCommand;
import by.training.karpilovich.lowcost.command.impl.redirect.RedirectToDefaultPageCommand;
import by.training.karpilovich.lowcost.command.impl.redirect.RedirectToPageCommand;
import by.training.karpilovich.lowcost.command.impl.redirect.RedirectToUpdateCityPageCommand;
import by.training.karpilovich.lowcost.command.impl.redirect.RedirectToUpdateFlightPage;

public class CommandFactory {

	private static final Logger LOGGER = LogManager.getLogger(CommandFactory.class);

	private final Map<CommandType, Command> commands = new EnumMap<>(CommandType.class);

	private CommandFactory() {
		commands.put(CommandType.SIGN_IN, new SignInCommand());
		commands.put(CommandType.SIGN_OUT, new SignOutCommand());
		commands.put(CommandType.SIGN_UP, new SignUpCommand());
		commands.put(CommandType.DEPOSIT, new DepositCommand());
		commands.put(CommandType.DELETE_USER, new DeleteUserCommand());
		
		commands.put(CommandType.ADD_CITY, new AddCityCommand());
		commands.put(CommandType.UPDATE_CITY, new UpdateCityCommand());
		commands.put(CommandType.DELETE_CITY, new DeleteCityCommand());
		commands.put(CommandType.SHOW_ALL_CITIES, new ShowAllCitiesCommand());
		commands.put(CommandType.REDIRECT_TO_UPDATE_CITY_PAGE, new RedirectToUpdateCityPageCommand());

		commands.put(CommandType.REDIRECT_TO_CREATE_FLIGTH_PAGE, new RedirectToCreateFlightPageCommand());
		commands.put(CommandType.CREATE_FLIGHT, new CreateFlightCommand());
		commands.put(CommandType.REDIRECT_TO_ADD_DATE_COEFFICIENT_PAGE, new RedirectToAddDateCoefficientPageCommand());
		commands.put(CommandType.REDIRECT_TO_ADD_PLACE_COEFFICIENT_PAGE,
				new RedirectToAddPlaceCoefficientPageCommand());
		commands.put(CommandType.ADD_DATE_COEFFICIENT, new AddDateCoefficientCommand());
		commands.put(CommandType.ADD_PLACE_COEFFICIENT, new AddPlaceCoefficientCommand());
		commands.put(CommandType.ADD_FLIGHT, new AddFlightCommand());
		commands.put(CommandType.SEARCH_FLIGHT, new SearchFlightCommand());
		commands.put(CommandType.SHOW_ALL_FLIGHTS, new ShowAllFlightsCommand());
		commands.put(CommandType.SORT_FLIGHTS_BY_DEPARTURE_DATE, new SortFlightsByDateCommand());
		commands.put(CommandType.SORT_FLIGHTS_BY_TICKET_PRICE, new SortFlightsByPriceCommand());
		commands.put(CommandType.CANCEL_FLIGHT_ADDING, new CancelFlightAddingCommand());
		commands.put(CommandType.REDIRECT_TO_UPDATE_FLIGHT_PAGE, new RedirectToUpdateFlightPage());
		commands.put(CommandType.UPDATE_FLIGHT, new UpdateFlightCommand());
		commands.put(CommandType.DELETE_FLIGHT, new DeleteFlightCommand());
		commands.put(CommandType.SHOW_NEXT_TWENTY_FOUR_HOURS_FLIGHTS, new ShowNextTwentyFourHoursFlightsCommand());
		commands.put(CommandType.SHOW_FLIGHTS_BETWEEN_DATES, new ShowFlightsBetweenDatesCommand());

		commands.put(CommandType.REDIRECT_TO_CREATE_TICKET_PAGE, new RedirectToCreateTicketPageCommand());
		commands.put(CommandType.CREATE_TICKET, new CreateTicketCommand());
		commands.put(CommandType.BUY_TICKET, new BuyTicketCommand());
		commands.put(CommandType.SHOW_ALL_TICKET, new ShowAllTicketCommand());
		commands.put(CommandType.RETURN_TICKET, new ReturnTicketCommand());
		commands.put(CommandType.SHOW_SOLD_TICKETS, new ShowAllTicketToFlightCommand());
		
		commands.put(CommandType.ADD_PLANE, new AddPlaneCommand());
		commands.put(CommandType.SHOW_ALL_PLANES, new ShowAllPlanesCommand());

		commands.put(CommandType.REDIRECT, new RedirectToPageCommand());
		commands.put(CommandType.CHANGE_LANGUAGE, new ChangeLanguageCommand());
	}

	private static final class CommandFactoryInstanceHolder {
		private static final CommandFactory INSTANCE = new CommandFactory();
	}

	public static CommandFactory getInstance() {
		return CommandFactoryInstanceHolder.INSTANCE;
	}

	public Command getCommad(String commandName) {
		Command command = new RedirectToDefaultPageCommand();
		if (commandName != null) {
			try {
				command = commands.get(CommandType.valueOf(commandName.toUpperCase()));
			} catch (IllegalArgumentException e) {
				LOGGER.error("Illegal command name " + commandName);
			}
		}
		return command;
	}
}