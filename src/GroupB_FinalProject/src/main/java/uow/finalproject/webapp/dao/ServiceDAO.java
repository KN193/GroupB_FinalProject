package uow.finalproject.webapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uow.finalproject.webapp.entity.Service;


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
				Service service = new Service(provider, name, crrPrice, oriPrice, description, capacity, deal, registerDate, nation, rank, type, img);
				services.add(service);
			}
			ps.close();
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
				Service service = new Service(provider, name, crrPrice, oriPrice, description, capacity, deal, registerDate, nation, rank, type, img);
				services.add(service);
			}
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return services;
	}
}
