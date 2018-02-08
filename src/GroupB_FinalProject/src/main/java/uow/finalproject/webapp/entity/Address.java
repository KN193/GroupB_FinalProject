package uow.finalproject.webapp.entity;

import uow.finalproject.webapp.entityType.Suburb;

public class Address {

	private String type;
	private String country;
	private int zipCode;
	private Suburb suburb;
	String street;
	String city;
	int houseNumber, unitNumber;
	
	public Address(String type, String country, int zipCode, Suburb suburb, String street, String city, int houseNumber,
			int unitNumber) {
		super();
		this.type = type;
		this.country = country;
		this.zipCode = zipCode;
		this.suburb = suburb;
		this.street = street;
		this.city = city;
		this.houseNumber = houseNumber;
		this.unitNumber = unitNumber;
	}
	public int getZipCode() {
		return zipCode;
	}
	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}
	public Suburb getSuburb() {
		return suburb;
	}
	public void setSuburb(Suburb suburb) {
		this.suburb = suburb;
	}
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(int houseNumber) {
		this.houseNumber = houseNumber;
	}

	public int getUnitNumber() {
		return unitNumber;
	}

	public void setUnitNumber(int unitNumber) {
		this.unitNumber = unitNumber;
	}

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
}
