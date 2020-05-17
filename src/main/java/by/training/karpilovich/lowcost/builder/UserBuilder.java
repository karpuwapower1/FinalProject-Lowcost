package by.training.karpilovich.lowcost.builder;

import java.math.BigDecimal;

import by.training.karpilovich.lowcost.entity.Role;
import by.training.karpilovich.lowcost.entity.User;

public class UserBuilder {

	private User user;

	public UserBuilder() {
		user = new User();
	}

	public UserBuilder setUserEmail(String email) {
		user.setEmail(email);
		return this;
	}

	public UserBuilder setUserPassword(String password) {
		user.setPassword(password);
		return this;
	}

	public UserBuilder setUserFirstName(String firstName) {
		user.setFirstName(firstName);
		return this;
	}

	public UserBuilder setUserLastName(String lastName) {
		user.setLastName(lastName);
		return this;
	}

	public UserBuilder setUserRole(Role role) {
		user.setRole(role);
		return this;
	}

	public UserBuilder setBalanceAmount(BigDecimal balanceAmount) {
		user.setBalanceAmount(balanceAmount);
		return this;
	}

	public User getUser() {
		return user;
	}
}