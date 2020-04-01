package by.training.karpilovich.lowcost.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.Page;

public class DefaultCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		return Page.DEFAULT.getAddress();
	}

}
