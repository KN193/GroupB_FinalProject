package uow.finalproject.webapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uow.finalproject.webapp.entity.Comment;
import uow.finalproject.webapp.entity.User;


@Component
public class CommentDAO {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private UserDAO userDAO;
	
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
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return comments;
	}
	
}
