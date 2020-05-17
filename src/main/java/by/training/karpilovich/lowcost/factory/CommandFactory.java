package by.training.karpilovich.lowcost.factory;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.CommandType;
import by.training.karpilovich.lowcost.command.impl.admin.city.AddCityCommand;
import by.training.karpilovich.lowcost.command.impl.admin.city.DeleteCityCommand;
import by.training.karpilovich.lowcost.command.impl.admin.city.ShowAllCitiesCommand;
import by.training.karpilovich.lowcost.command.impl.admin.city.UpdateCityCommand;
import by.training.karpilovich.lowcost.command.impl.admin.flight.AddDateCoefficientCommand;
import by.training.karpilovich.lowcost.command.impl.admin.flight.AddFlightCommand;
import by.training.karpilovich.lowcost.command.impl.admin.flight.AddPlaceCoefficientCommand;
import by.training.karpilovich.lowcost.command.impl.admin.flight.CancelFlightAddingCommand;
import by.training.karpilovich.lowcost.command.impl.admin.flight.CreateFlightCommand;
import by.training.karpilovich.lowcost.command.impl.admin.flight.DeleteFlightCommand;
import by.training.karpilovich.lowcost.command.impl.admin.flight.ShowAllFlightsCommand;
import by.training.karpilovich.lowcost.command.impl.admin.flight.ShowFlightsBetweenDatesCommand;
import by.training.karpilovich.lowcost.command.impl.admin.flight.ShowNextTwentyFourHoursFlightsCommand;
import by.training.karpilovich.lowcost.command.impl.admin.flight.UpdateFlightCommand;
import by.training.karpilovich.lowcost.command.impl.admin.plane.AddPlaneCommand;
import by.training.karpilovich.lowcost.command.impl.admin.plane.ShowAllPlanesCommand;
import by.training.karpilovich.lowcost.command.impl.admin.ticket.ShowAllTicketCommand;
import by.training.karpilovich.lowcost.command.impl.admin.ticket.ShowAllTicketToFlightCommand;
import by.training.karpilovich.lowcost.command.impl.general.ChangeLanguageCommand;
import by.training.karpilovich.lowcost.command.impl.general.ChangePasswordCommand;
import by.training.karpilovich.lowcost.command.impl.general.RestorePasswordCommand;
import by.training.karpilovich.lowcost.command.impl.general.SearchFlightCommand;
import by.training.karpilovich.lowcost.command.impl.general.SignInCommand;
import by.training.karpilovich.lowcost.command.impl.general.SignOutCommand;
import by.training.karpilovich.lowcost.command.impl.general.SignUpCommand;
import by.training.karpilovich.lowcost.command.impl.general.SortFlightsByDateCommand;
import by.training.karpilovich.lowcost.command.impl.general.SortFlightsByPriceCommand;
import by.training.karpilovich.lowcost.command.impl.redirect.RedirectToAddDateCoefficientPageCommand;
import by.training.karpilovich.lowcost.command.impl.redirect.RedirectToAddPlaceCoefficientPageCommand;
import by.training.karpilovich.lowcost.command.impl.redirect.RedirectToCreateFlightPageCommand;
import by.training.karpilovich.lowcost.command.impl.redirect.RedirectToCreateTicketPageCommand;
import by.training.karpilovich.lowcost.command.impl.redirect.RedirectToDefaultPageCommand;
import by.training.karpilovich.lowcost.command.impl.redirect.RedirectToPageCommand;
import by.training.karpilovich.lowcost.command.impl.redirect.RedirectToUpdateCityPageCommand;
import by.training.karpilovich.lowcost.command.impl.redirect.RedirectToUpdateFlightPage;
import by.training.karpilovich.lowcost.command.impl.user.BuyTicketCommand;
import by.training.karpilovich.lowcost.command.impl.user.CreateTicketCommand;
import by.training.karpilovich.lowcost.command.impl.user.DeleteUserCommand;
import by.training.karpilovich.lowcost.command.impl.user.DepositCommand;
import by.training.karpilovich.lowcost.command.impl.user.ReturnTicketCommand;
import by.training.karpilovich.lowcost.entity.Role;

public class CommandFactory {

	private static final Logger LOGGER = LogManager.getLogger(CommandFactory.class);

	private final Map<CommandType, Command> commands = new EnumMap<>(CommandType.class);
	private final Map<Role, EnumSet<CommandType>> commandsToRole = new EnumMap<>(Role.class);

	private CommandFactory() {
		initCommands();
		initCommandsToRole();
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

	public Set<CommandType> getCommandToTole(Role role) {
		return commandsToRole.get(role);
	}

	private void initCommands() {
		commands.put(CommandType.SIGN_IN, new SignInCommand());
		commands.put(CommandType.SIGN_OUT, new SignOutCommand());
		commands.put(CommandType.SIGN_UP, new SignUpCommand());
		commands.put(CommandType.DEPOSIT, new DepositCommand());
		commands.put(CommandType.DELETE_USER, new DeleteUserCommand());
		commands.put(CommandType.UPDATE_PASSWORD, new ChangePasswordCommand());
		commands.put(CommandType.RESTORE_PASSWORD, new RestorePasswordCommand());

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

	private void initCommandsToRole() {
		commandsToRole.put(Role.GUEST, initGuestsCommands());
		commandsToRole.put(Role.USER, initUsersCommands());
		commandsToRole.put(Role.ADMIN, initAdminsCommands());
	}

	private EnumSet<CommandType> initGuestsCommands() {
		return EnumSet.of(CommandType.SIGN_IN, CommandType.SIGN_UP, CommandType.RESTORE_PASSWORD,
				CommandType.SEARCH_FLIGHT, CommandType.SORT_FLIGHTS_BY_DEPARTURE_DATE,
				CommandType.SORT_FLIGHTS_BY_TICKET_PRICE, CommandType.CREATE_TICKET, CommandType.REDIRECT,
				CommandType.CHANGE_LANGUAGE);
	}

	private EnumSet<CommandType> initUsersCommands() {
		return EnumSet.of(CommandType.SIGN_OUT, CommandType.DEPOSIT, CommandType.DELETE_USER,
				CommandType.UPDATE_PASSWORD, CommandType.SEARCH_FLIGHT, CommandType.SORT_FLIGHTS_BY_DEPARTURE_DATE,
				CommandType.SORT_FLIGHTS_BY_TICKET_PRICE, CommandType.REDIRECT_TO_CREATE_TICKET_PAGE,
				CommandType.CREATE_TICKET, CommandType.BUY_TICKET, CommandType.SHOW_ALL_TICKET,
				CommandType.RETURN_TICKET, CommandType.REDIRECT, CommandType.CHANGE_LANGUAGE);
	}

	private EnumSet<CommandType> initAdminsCommands() {
		return EnumSet.of(CommandType.SIGN_OUT, CommandType.UPDATE_PASSWORD, CommandType.ADD_CITY,
				CommandType.SHOW_ALL_CITIES, CommandType.DELETE_CITY, CommandType.REDIRECT_TO_UPDATE_CITY_PAGE,
				CommandType.UPDATE_CITY, CommandType.REDIRECT_TO_CREATE_FLIGTH_PAGE, CommandType.CREATE_FLIGHT,
				CommandType.REDIRECT_TO_ADD_PLACE_COEFFICIENT_PAGE, CommandType.REDIRECT_TO_ADD_DATE_COEFFICIENT_PAGE,
				CommandType.ADD_PLACE_COEFFICIENT, CommandType.ADD_DATE_COEFFICIENT, CommandType.ADD_FLIGHT,
				CommandType.CANCEL_FLIGHT_ADDING, CommandType.SHOW_ALL_FLIGHTS, CommandType.SEARCH_FLIGHT,
				CommandType.SORT_FLIGHTS_BY_DEPARTURE_DATE, CommandType.SORT_FLIGHTS_BY_TICKET_PRICE,
				CommandType.REDIRECT_TO_UPDATE_FLIGHT_PAGE, CommandType.UPDATE_FLIGHT, CommandType.DELETE_FLIGHT,
				CommandType.SHOW_NEXT_TWENTY_FOUR_HOURS_FLIGHTS, CommandType.SHOW_FLIGHTS_BETWEEN_DATES,
				CommandType.SHOW_SOLD_TICKETS, CommandType.ADD_PLANE, CommandType.SHOW_ALL_PLANES, CommandType.REDIRECT,
				CommandType.CHANGE_LANGUAGE);
	}
}