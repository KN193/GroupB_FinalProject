package uow.finalproject.webapp.entityType;

public enum Nationality {
	Australian("Australian"),
	USA("USA"),
	British("British"),
	Chinese("Chinese"),
	French("French"),
	Vietnam("Vietnam"),
	Indian("Indian");
	
	private String name;
	Nationality(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
