package uow.finalproject.webapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import uow.finalproject.webapp.entity.Address;
import uow.finalproject.webapp.entity.User;
import uow.finalproject.webapp.entityType.Nationality;
import uow.finalproject.webapp.entityType.Suburb;


@Component
public class UserDAO {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public boolean registerNewUser(User usr) {
		Connection conn = null;
		String sql = "INSERT INTO User(username, password, nickname, firstname, lastname,verified,Nationality, PreferNation, DateOfBirth, mobile) VALUES(?,?,?,?,?,?,?,?,?,?);";
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, usr.getEmail());
//			ps.setString(2, passwordEncoder.encode(usr.getPwd()));
			ps.setString(2, usr.getPwd());
			ps.setString(3, usr.getNickName());
			ps.setString(4, usr.getFirstName());
			ps.setString(5, usr.getLastName());
			ps.setString(6, "Y");
			ps.setString(7, usr.getNationality().getName());
			ps.setString(8, usr.getNationality().getName()); //////// ???????? Prefernation
			ps.setDate(9, new java.sql.Date(usr.getDOB().getTime()));
			ps.setString(10, usr.getMobile());
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		sql = "INSERT INTO Address(owner, type, country, state, ZIPCode,city,street, houseNumber, unitNumber) VALUES(?,?,?,?,?,?,?,?,?);";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, usr.getEmail());
			
			Address registeredAddress = usr.getAddress();
			ps.setString(2, registeredAddress.getType()); // default register user is home's address
			ps.setString(3, usr.getNationality().getName());
			ps.setString(4, registeredAddress.getSuburb().getName());
			ps.setInt(5,registeredAddress.getZipCode());
			ps.setString(6, registeredAddress.getCity());
			ps.setString(7,registeredAddress.getStreet()); 
			ps.setInt(8, registeredAddress.getHouseNumber());
			ps.setInt(9, registeredAddress.getUnitNumber());
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	public User findUser(String usrName) {
		Connection conn = null;
		User user = null;
		Address address = null;
		String sql = "SELECT * FROM User WHERE userName=?;";
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, usrName);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				String nickname = rs.getString("nickname");
				String firstname = rs.getString("firstname");
				String lastname = rs.getString("lastname");
				String photo = rs.getString("photo");
				String mobile = rs.getString("mobile");
				Nationality nation = Nationality.valueOf(rs.getString("nationality"));
				String email = rs.getString("userName");
				Date dob = rs.getTimestamp("DateOfBirth");
				
				user = new User(nickname,firstname,lastname,email,nation);
				user.setPhoto(photo);
				user.setDOB(dob);
				user.setMobile(mobile);
			}
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		sql = "SELECT * FROM Address WHERE owner=?;";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, usrName);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				String type = rs.getString("type");
				int zipCode = rs.getInt("ZIPCode");
				String country = rs.getString("country");
				String city = rs.getString("city");
				String street = rs.getString("street");
				Suburb suburb = Suburb.valueOf(rs.getString("state"));
				int houseNumber = rs.getInt("houseNumber");
				int unitNumber = rs.getInt("unitNumber");
				
				address = new Address(type, country, zipCode, suburb, street, city, houseNumber, unitNumber);
			}
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		user.setAddress(address);
		return user;
	}

	public boolean updateUser(User usr) {
		Connection conn = null;
		String sql = "UPDATE User SET nickname=?, firstname=?, lastname=?,Nationality=?, PreferNation=?, DateOfBirth=?, mobile=? WHERE username=?;";
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, usr.getNickName());
			ps.setString(2, usr.getFirstName());
			ps.setString(3, usr.getLastName());
			ps.setString(4, usr.getNationality().getName());
			ps.setString(5, usr.getNationality().getName()); //////// ???????? Prefernation
			ps.setDate(6, new java.sql.Date(usr.getDOB().getTime()));
			ps.setString(7, usr.getMobile());
			ps.setString(8, usr.getEmail());
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		sql = "UPDATE Address SET country=?, state=?, ZIPCode=?, city=?, street=?, houseNumber=?, unitNumber=? WHERE owner=?;";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			
			Address registeredAddress = usr.getAddress();
			ps.setString(1, usr.getNationality().getCountryName());
			ps.setString(2, registeredAddress.getSuburb().getName());
			ps.setInt(3,registeredAddress.getZipCode());
			ps.setString(4, registeredAddress.getCity());
			ps.setString(5,registeredAddress.getStreet()); 
			ps.setInt(6, registeredAddress.getHouseNumber());
			ps.setInt(7, registeredAddress.getUnitNumber());
			ps.setString(8, usr.getEmail());
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public int changePassword(String userName, String pwd, String newPwd) {
		Connection conn = null;
		String sql = "SELECT * FROM User WHERE username=? AND password=?;";
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			ps.setString(2, pwd);
			ResultSet rs = ps.executeQuery();
			
			if (!rs.next()) {
				return 1; // no combination of username and password 
			}
			
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			return 2; // other internal error
		}
		
		sql = "UPDATE User SET password=? WHERE username=?";
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, newPwd);
			ps.setString(2, userName);
			ps.executeUpdate();
			
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			return 2; // other internal error
		}
		return 0; // successfully
	}
}
