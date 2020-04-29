package by.training.karpilovich.lowcost.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.JSPParameter;
import by.training.karpilovich.lowcost.connection.ConnectionPool;
import by.training.karpilovich.lowcost.exception.ConnectionPoolException;
import by.training.karpilovich.lowcost.exception.RepositoryException;
import by.training.karpilovich.lowcost.factory.CommandFactory;
import by.training.karpilovich.lowcost.repository.CityRepository;
import by.training.karpilovich.lowcost.repository.impl.CityRepositoryImpl;

@WebServlet("")
public class ServletController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger(ServletController.class);

	@Override
	public void init() throws ServletException {
		ConnectionPool pool = ConnectionPool.getInstance();
		CityRepository repository = CityRepositoryImpl.getInstance();
		try {
			pool.init();
			repository.init();
		} catch (ConnectionPoolException | RepositoryException e) {
			LOGGER.error("Error while initializing servlet ", e);
		}
		super.init();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		handleRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		handleRequest(request, response);
	}

	private void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String commandParameterValue = request.getParameter(JSPParameter.COMMAND);
		CommandFactory factory = CommandFactory.getInstance();
		Command command = factory.getCommad(commandParameterValue);
		String page = command.execute(request, response);
		request.getRequestDispatcher(page).forward(request, response);
	}

	@Override
	public void destroy() {
		ConnectionPool pool = ConnectionPool.getInstance();
		try {
			pool.destroy();
		} catch (ConnectionPoolException e) {
			LOGGER.error("Error while destroeing servlet ", e);
		}
		super.destroy();
	}
}