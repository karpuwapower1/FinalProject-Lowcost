package by.training.karpilovich.lowcost.builder;

import by.training.karpilovich.lowcost.entity.Role;
import by.training.karpilovich.lowcost.entity.User;

public class UserBuilder {

	private User user;

	public UserBuilder() {
		user = new User();
	}

	public void setUserEmail(String email) {
		user.setEmail(email);
	}

	public void setUserPassword(String password) {
		user.setPassword(password);
	}

	public void setUserFirstName(String firstName) {
		user.setFirstName(firstName);
	}

	public void setUserLastName(String lastName) {
		user.setLastName(lastName);
	}

	public void setUserRole(Role role) {
		user.setRole(role);
	}

	public User getUser() {
		return user;
	}

}
