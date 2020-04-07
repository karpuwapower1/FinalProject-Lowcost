package by.training.karpilovich.lowcost.command;

public enum Page {

	DEFAULT("/index.jsp"), SIGN_IN("/jsp/signin.jsp"), SIGN_UP("/jsp/signup.jsp"), ADD_CITY("/jsp/add_city.jsp"),
	ALL_CITIES("/jsp/all_cities.jsp"), RESULT("//TODO"), INTERNAL_ERROR("TODO");

	private String address;

	private Page(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

}
