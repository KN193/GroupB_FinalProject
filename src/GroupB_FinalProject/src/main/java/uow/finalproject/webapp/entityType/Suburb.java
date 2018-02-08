package uow.finalproject.webapp.entityType;

public enum Suburb {
	NSW("NSW"), //New South Wales
	QLD("QLD"), //Queensland
	SA("SA"), //South Australia
	TAS("TAS"), //Tasmania
	VIC("VIC"), //Victoria
	WA("WA"); //Western Australia
	
	private String name;
	Suburb(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
