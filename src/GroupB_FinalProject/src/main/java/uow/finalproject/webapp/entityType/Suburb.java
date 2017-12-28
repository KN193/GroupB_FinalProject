package uow.finalproject.webapp.entityType;

public enum Suburb {
	NSW("New South Wales"),
	QLD("Queensland"),
	SA("South Australia"),
	TAS("Tasmania"),
	VIC("Victoria"),
	WA("Western Australia");
	
	private String name;
	Suburb(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
