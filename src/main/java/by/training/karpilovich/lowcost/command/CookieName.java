package by.training.karpilovich.lowcost.command;

public enum CookieName {
	
	EMAIL("email"), PASSWORD("password"), LOCALE("locale");
	
	private String name;
	
	private CookieName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

}
