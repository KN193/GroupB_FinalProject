package uow.finalproject.webapp.entityType;

public enum Nationality {
	Australian("Australian", "Australia"),
	USA("USA", "United State"),
	British("British", "England"),
	Chinese("Chinese", "China"),
	French("French", "France"),
	Vietnam("Vietnam", "Vietnam"),
	SaudiArabia("Saudi Arabia", "Saudi Arabia"),
	Indian("Indian", "India"),
	Singapore("Singapore", "Singapore");
	
	private String name;
	private String countryName;
	Nationality(String name, String countryName) {
		this.name = name;
		this.countryName = countryName;
	}
	
	public String getName() {
		return name;
	}
	public String getCountryName() {
		return countryName;
	}
}
