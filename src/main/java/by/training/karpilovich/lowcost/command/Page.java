package by.training.karpilovich.lowcost.command;

public enum Page {

	DEFAULT("/index.jsp"), SIGN_IN("/jsp/signin.jsp"), SIGN_UP("/jsp/signup.jsp"), RESULT("//TODO");

	private String address;

	private Page(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

}
