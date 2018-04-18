package uow.finalproject.webapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uow.finalproject.webapp.entity.Purchased;
import uow.finalproject.webapp.entity.Service;
import uow.finalproject.webapp.entity.User;


@Component
public class FavoriteDAO {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private ServiceDAO serviceDAO;
	
	
	public ArrayList<Service> findFavoriteByUser(String userName) {
		Connection conn = null;
		ArrayList<Service> services = new ArrayList<Service>();
		String sql = "SELECT * FROM Favourites WHERE username=?;";
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int serviceID = rs.getInt("serviceID");
				Service s = serviceDAO.findServiceByID(serviceID);
				services.add(s);
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return services;
	}
	
	public int deleteFavoriteByUser(String usrName, int serviceID) {
		Connection conn = null;
		String sql = "DELETE FROM Favourites WHERE serviceID=? AND username=?;";
		try {
		conn = dataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, serviceID);
		ps.setString(2, usrName);
		ps.execute();
		
		ps.close();
		conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
		return 0;
	}

	public int insertFavoriteByUser(String usrName, int serviceID) {
		Connection conn = null;
		String sql = "INSERT INTO Favourites(username, serviceID) VALUES(?,?);";
		try {
		conn = dataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, usrName);
			ps.setInt(2, serviceID);
		ps.execute();
		
		ps.close();
		conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
		return 0;
	}
}
