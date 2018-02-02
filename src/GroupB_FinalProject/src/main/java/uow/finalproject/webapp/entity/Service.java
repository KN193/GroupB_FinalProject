package uow.finalproject.webapp.entity;

import java.util.Date;

import uow.finalproject.webapp.entityType.Nationality;
import uow.finalproject.webapp.entityType.Suburb;

public class Service {
	// Mandatory fields
	String provider;
	String name;
	float currentPrice, originalPrice;
	String description;
	int capacity;
	int deal;
	Date registerDate;
	String img;

//	Nationality nationality;
	String nationality;
	int rank;
	String type;
	
	public Service(String provider, String name, float currentPrice, float originalPrice, String description,
			int capacity, int deal, Date registerDate, String nationality, int rank, String type, String img) {
		super();
		this.provider = provider;
		this.name = name;
		this.currentPrice = currentPrice;
		this.originalPrice = originalPrice;
		this.description = description;
		this.capacity = capacity;
		this.deal = deal;
		this.registerDate = registerDate;
		this.nationality = nationality;
		this.rank = rank;
		this.type = type;
		this.img = img;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(float currentPrice) {
		this.currentPrice = currentPrice;
	}
	public float getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(float originalPrice) {
		this.originalPrice = originalPrice;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public int getDeal() {
		return deal;
	}
	public void setDeal(int deal) {
		this.deal = deal;
	}
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	
}
