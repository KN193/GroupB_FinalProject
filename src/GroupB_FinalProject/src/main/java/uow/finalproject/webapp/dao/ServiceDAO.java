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
public class ServiceDAO {

	@Autowired
	private DataSource dataSource;
	
	
	public ArrayList<Service> findServiceByProvider(String providerName) {
		Connection conn = null;
		ArrayList<Service> services = new ArrayList<Service>();
		String sql = "SELECT * FROM Service WHERE provider=?;";
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, providerName);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int serviceID = rs.getInt("serviceID");
				String provider = rs.getString("provider");
				String name = rs.getString("name");
				float crrPrice = rs.getFloat("currentPrice");
				float oriPrice = rs.getFloat("originalPrice");
				String description = rs.getString("description");
				int capacity = rs.getInt("capacity");
				int deal = rs.getInt("number_of_deal");
				int rank = rs.getInt("rank");
				String type = rs.getString("type");
				
				String nation = rs.getString("nationality");
				String img = rs.getString("image");
//				Nationality nation = Nationality.valueOf(rs.getString("nationality"));
				Date registerDate = rs.getTimestamp("RegisterTime");
				Service service = new Service(serviceID, provider, name, crrPrice, oriPrice, description, capacity, deal, registerDate, nation, rank, type, img);
				services.add(service);
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return services;
	}
	
	public ArrayList<Service> findAllService() {
		Connection conn = null;
		ArrayList<Service> services = new ArrayList<Service>();
		String sql = "SELECT * FROM Service;";
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int serviceID = rs.getInt("serviceID");
				String provider = rs.getString("provider");
				String name = rs.getString("name");
				float crrPrice = rs.getFloat("currentPrice");
				float oriPrice = rs.getFloat("originalPrice");
				String description = rs.getString("description");
				int capacity = rs.getInt("capacity");
				int deal = rs.getInt("number_of_deal");
				int rank = rs.getInt("rank");
				String type = rs.getString("type");
				
				String nation = rs.getString("nationality");
				String img = rs.getString("image");
//				Nationality nation = Nationality.valueOf(rs.getString("nationality"));
				Date registerDate = rs.getTimestamp("RegisterTime");
				Service service = new Service(serviceID, provider, name, crrPrice, oriPrice, description, capacity, deal, registerDate, nation, rank, type, img);
				services.add(service);
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return services;
	}
	
	public ArrayList<Service> findNumberOfService(int num) {
		Connection conn = null;
		ArrayList<Service> services = new ArrayList<Service>();
		String sql = "SELECT * FROM Service LIMIT ?;";
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, num);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int serviceID = rs.getInt("serviceID");
				String provider = rs.getString("provider");
				String name = rs.getString("name");
				float crrPrice = rs.getFloat("currentPrice");
				float oriPrice = rs.getFloat("originalPrice");
				String description = rs.getString("description");
				int capacity = rs.getInt("capacity");
				int deal = rs.getInt("number_of_deal");
				int rank = rs.getInt("rank");
				String type = rs.getString("type");
				
				String nation = rs.getString("nationality");
				String img = rs.getString("image");
//				Nationality nation = Nationality.valueOf(rs.getString("nationality"));
				Date registerDate = rs.getTimestamp("RegisterTime");
				Service service = new Service(serviceID, provider, name, crrPrice, oriPrice, description, capacity, deal, registerDate, nation, rank, type, img);
				services.add(service);
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return services;
	}

	public Service findServiceByID(int serviceID) {
		Connection conn = null;
		Service service = null;
		String sql = "SELECT * FROM Service WHERE serviceID=?;";
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, serviceID);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				String provider = rs.getString("provider");
				String name = rs.getString("name");
				float crrPrice = rs.getFloat("currentPrice");
				float oriPrice = rs.getFloat("originalPrice");
				String description = rs.getString("description");
				int capacity = rs.getInt("capacity");
				int deal = rs.getInt("number_of_deal");
				int rank = rs.getInt("rank");
				String type = rs.getString("type");
				
				String nation = rs.getString("nationality");
				String img = rs.getString("image");
//				Nationality nation = Nationality.valueOf(rs.getString("nationality"));
				Date registerDate = rs.getTimestamp("RegisterTime");
				service = new Service(serviceID, provider, name, crrPrice, oriPrice, description, capacity, deal, registerDate, nation, rank, type, img);
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return service;
	}
	
	public HashMap<Purchased, Service> findPurchasedService(String userName) {
		Connection conn = null;
		HashMap<Purchased, Service> purchasedServices = new HashMap<Purchased, Service>();
		String sql = "SELECT * FROM Purchased WHERE userName=?;";
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int purchasedID = rs.getInt("PurchasedID");
				int serviceID = rs.getInt("serviceID");
				int quantity = rs.getInt("quantity");
				String transferredFrom = rs.getString("transferredFrom");
				boolean providerCheck = rs.getBoolean("providerCheck");
				boolean customerCheck = rs.getBoolean("customerCheck");
				
				Date purchasedTime = rs.getTimestamp("purchasedTime");
				Purchased purchased = new Purchased(purchasedID, userName, serviceID, purchasedTime, providerCheck, customerCheck, quantity, transferredFrom);
				Service detail = findServiceByID(serviceID);
				
				purchasedServices.put(purchased, detail);
				
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return purchasedServices;
	}
	
	public int purchaseItems(User usr) {
		Connection conn = null;
		Iterator<Entry<Service, Integer>> it = usr.getShoppingCart().entrySet().iterator();
		String sql = "INSERT INTO Purchased(username, serviceID, purchasedTime, providerCheck, customerCheck,quantity) VALUES(?,?,?,?,?,?);";
		try {
		conn = dataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
	    while (it.hasNext()) {
	    		Map.Entry<Service, Integer> pair = (Map.Entry<Service, Integer>)it.next();
			ps.setString(1, usr.getEmail());
			ps.setInt(2, pair.getKey().getServiceID());
			ps.setDate(3, new java.sql.Date(new java.util.Date().getTime()));
			ps.setString(4, "Y");
			ps.setString(5, "Y");
			ps.setInt(6, pair.getValue());
	        ps.addBatch();
	        
	    }
		ps.executeBatch();
		ps.close();
		conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
		return 0;
	}
}
