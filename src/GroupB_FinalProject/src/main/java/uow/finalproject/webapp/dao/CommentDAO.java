package uow.finalproject.webapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uow.finalproject.webapp.entity.Comment;
import uow.finalproject.webapp.entity.Service;
import uow.finalproject.webapp.entity.User;


@Component
public class CommentDAO {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private ServiceDAO serviceDAO;
	
	public ArrayList<Comment> findCommentByServiceID(int serviceID) {
		Connection conn = null;
		ArrayList<Comment> comments = new ArrayList<Comment>();
		String sql = "SELECT * FROM Comment WHERE serviceID=?;";
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, serviceID);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int commentID = rs.getInt("commentID");
				int previousComment = rs.getInt("previousComment");
				String commenterID = rs.getString("commenter");
				User commenter = userDAO.findUser(commenterID);
				String commentInfo = rs.getString("commentInfo");
				boolean visible = rs.getBoolean("visible");
				Date commentTime = rs.getTimestamp("commentTime");
				Comment comment = new Comment(serviceID, commentID, previousComment, commenter, commentInfo, visible, commentTime);
				comments.add(comment);
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return comments;
	}
	
	public HashMap<Service, Comment> findAllCommentByCommenter(String userName) {
		Connection conn = null;
		HashMap<Service, Comment> comments = new HashMap<Service, Comment>();
		String sql = "SELECT * FROM Comment WHERE commenter=?;";
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int commentID = rs.getInt("commentID");
				int serviceID = rs.getInt("serviceID");
				int previousComment = rs.getInt("previousComment");
				String commenterID = rs.getString("commenter");
				User commenter = userDAO.findUser(commenterID);
				String commentInfo = rs.getString("commentInfo");
				boolean visible = rs.getBoolean("visible");
				Date commentTime = rs.getTimestamp("commentTime");
				Comment comment = new Comment(serviceID, commentID, previousComment, commenter, commentInfo, visible, commentTime);
				Service service = serviceDAO.findServiceByID(serviceID);
				comments.put(service, comment);
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return comments;
	}
	
	public boolean addNewComment(Comment cmt) {
		Connection conn = null;
		String sql = "INSERT INTO Comment(serviceID, commenter, commentInfo, commentTime) VALUES(?,?,?,?);";
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, cmt.getServiceID());
			ps.setString(2, cmt.getCommenter().getEmail());
			ps.setString(3, cmt.getCommentInfo());
			ps.setDate(4, new java.sql.Date(cmt.getCommentTime().getTime()));
			ps.executeUpdate();
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	public void deleteComment(int serviceID) {
		Connection conn = null;
		String sql = "INSERT INTO Comment(serviceID, commenter, commentInfo, commentTime) VALUES(?,?,?,?);";
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
//			ps.setInt(1, cmt.getServiceID());
//			ps.setString(2, cmt.getCommenter().getEmail());
//			ps.setString(3, cmt.getCommentInfo());
//			ps.setDate(4, new java.sql.Date(cmt.getCommentTime().getTime()));
			ps.executeUpdate();
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
//			return false;
		}
		
//		return true;
	}
	
}
