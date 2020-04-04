package by.training.karpilovich.lowcost.command;

public enum AttributeName {

	USER("user"), ROLE("role"), PAGE_FROM("page_from"), IS_LOGIN("isLogin"), LOCALE("locale"),
	ERROR_MESSAGE("errorMessage"), PAGE_TO("page"), FLIGHTS("flights"), CITIES("cities");

	private String name;

	private AttributeName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
