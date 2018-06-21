package uow.finalproject.webapp.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

@Entity(name="Persons")
@Indexed
public class Persons {
	
	@Id
	public int PersonID;
	@Field
	public String Lastname;
	@Field
	public String Firstname;
	public String Address;
	String City;
	
	public Persons () {}
	
	public Persons(int personID, String lastName, String firstName, String address, String city) {
		super();
		PersonID = personID;
		Lastname = lastName;
		Firstname = firstName;
		Address = address;
		City = city;
	}
	
	public int getPersonID() {
		return PersonID;
	}

	public void setPersonID(int personID) {
		PersonID = personID;
	}

	public String getLastName() {
		return Lastname;
	}

	public void setLastName(String lastName) {
		Lastname = lastName;
	}

	public String getFirstName() {
		return Firstname;
	}

	public void setFirstName(String firstName) {
		Firstname = firstName;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

}
