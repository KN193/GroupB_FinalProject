package uow.finalproject.webapp.entity;


import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.NumericField;

@Entity(name="Service")
@Indexed
public class Service implements Comparable<Service>{
	// Mandatory fields
	@Id
	int serviceID;
	
	String provider;
	
	@Field
	String name;

	double Currentprice;

	double Originalprice;
	
	@Field
	String description;

	int capacity;
	
	@Column(name="number_of_deal")
	int Deal;
	
	Date Registertime;
	
	@Column(name="image")
	String img;

	String nationality;
	
	@Column(name="rank")
	int rank;
	
	String type;
	
	public Service() {}
	
	public Service(int serviceID, String provider, String name, double currentPrice, double originalPrice, String description,
			int capacity, int deal, Date registerDate, String nationality, int rank, String type, String img) {
		super();
		this.serviceID = serviceID;
		this.provider = provider;
		this.name = name;
		this.Currentprice = currentPrice;
		this.Originalprice = originalPrice;
		this.description = description;
		this.capacity = capacity;
		this.Deal = deal;
		this.Registertime = registerDate;
		this.nationality = nationality;
		this.rank = rank;
		this.type = type;
		this.img = img;
	}
	public int getServiceID() {
		return serviceID;
	}
	public void setServiceID(int serviceID) {
		this.serviceID = serviceID;
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
	
	public double getCurrentPrice() {
		return Currentprice;
	}
	public void setCurrentPrice(double currentPrice) {
		this.Currentprice = currentPrice;
	}
	
	public double getOriginalPrice() {
		return Originalprice;
	}
	public void setOriginalPrice(double originalPrice) {
		this.Originalprice = originalPrice;
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
		return Deal;
	}
	public void setDeal(int deal) {
		this.Deal = deal;
	}
	public Date getRegisterDate() {
		return Registertime;
	}
	public void setRegisterDate(Date registerDate) {
		this.Registertime = registerDate;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public int getrRank() {
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
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return ((Service)obj).getServiceID() == this.serviceID;
	}
	@Override
	public int compareTo(Service o) {
		if (o.getServiceID() == this.serviceID) {
			return -1;
		}
		return 1;
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return Objects.hash(serviceID,provider,name);
	}
}
