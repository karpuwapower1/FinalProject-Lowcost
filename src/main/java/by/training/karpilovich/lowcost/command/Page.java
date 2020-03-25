package by.training.karpilovich.lowcost.command;

public enum Page {
	
	DEFAULT("/index.jsp"), SIGNIN("/jsp/signin.jsp"), SIGNUP("/jsp/signup.jsp");
	
	private String address;
	
	private Page(String address) {
		this.address = address;
	}
	
	public String getAddress() {
		return address;
	}

}
