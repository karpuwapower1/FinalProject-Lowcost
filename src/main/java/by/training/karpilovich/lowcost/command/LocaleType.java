package by.training.karpilovich.lowcost.command;

public enum LocaleType {
	
	RU("ru", "RU"), EN("en", "US");
	
	private String language;
	private String country;
	
	private LocaleType(String language, String country) {
		this.language = language;
		this.country = country;
	}

	public String getLanguage() {
		return language;
	}

	public String getCountry() {
		return country;
	}

}
