package uow.finalproject.webapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import uow.finalproject.webapp.entity.User;


@Component
public class UserDAO {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public boolean registerNewUser(User usr) {
		Connection conn = null;
		String sql = "INSERT INTO User(username, password, nickname, firstname, lastname,verified,Nationality, PreferNation) VALUES(?,?,?,?,?,?,?,?);";
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, usr.getEmail());
			ps.setString(2, passwordEncoder.encode(usr.getPwd()));
			ps.setString(3, usr.getNickName());
			ps.setString(4, usr.getFirstName());
			ps.setString(5, usr.getLastName());
			ps.setBoolean(6, true);
			ps.setString(7, usr.getNationality());
			ps.setString(8, usr.getNationality()); //////// ???????? Prefernation
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
