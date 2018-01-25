package uow.finalproject.webapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import uow.finalproject.webapp.entity.User;
import uow.finalproject.webapp.entityType.Nationality;


@Component
public class UserDAO {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public boolean registerNewUser(User usr) {
		Connection conn = null;
		String sql = "INSERT INTO User(username, password, nickname, firstname, lastname,verified,Nationality, PreferNation, DateOfBirth) VALUES(?,?,?,?,?,?,?,?,?);";
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, usr.getEmail());
			ps.setString(2, passwordEncoder.encode(usr.getPwd()));
			ps.setString(3, usr.getNickName());
			ps.setString(4, usr.getFirstName());
			ps.setString(5, usr.getLastName());
			ps.setString(6, "Y");
			ps.setString(7, usr.getNationality().getName());
			ps.setString(8, usr.getNationality().getName()); //////// ???????? Prefernation
			ps.setDate(9, new java.sql.Date(usr.getDOB().getTime()));
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
				Nationality nation = Nationality.valueOf(rs.getString("nationality"));
				String email = rs.getString("userName");
				
				user = new User(nickname,firstname,lastname,email);
				user.setNationality(nation);
			}
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return user;
	}
}
