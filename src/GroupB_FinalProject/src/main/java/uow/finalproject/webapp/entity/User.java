package uow.finalproject.webapp.entity;

import java.util.Date;

import uow.finalproject.webapp.entityType.Nationality;
import uow.finalproject.webapp.entityType.Suburb;

public class User {
	// Mandatory fields
	String nickName, firstName, lastName;
	String email;
	String pwd;
	String link;
	Address address;

	//optional fields
	String mobile;
	String photo;
	Nationality nationality;
	Date DOB;
	String[] potentialInterests;
	
	public User() {};
	
	public User(String nickName, String firstName, String lastName, String email, Nationality nation) {
		super();
		this.nickName = nickName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.nationality = nation;
	}
	
	public User(String nickName, String firstName, String lastName, String email, String pwd, Nationality nation) {
		super();
		this.nickName = nickName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.pwd = pwd;
		this.nationality = nation;
	}

	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Nationality getNationality() {
		return nationality;
	}
	public void setNationality(Nationality nationality) {
		this.nationality = nationality;
	}
	public Date getDOB() {
		return DOB;
	}
	public void setDOB(Date dOB) {
		DOB = dOB;
	}
	public String[] getPotentialInterests() {
		return potentialInterests;
	}
	public void setPotentialInterests(String[] potentialInterests) {
		this.potentialInterests = potentialInterests;
	}
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}
