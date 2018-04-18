package uow.finalproject.webapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uow.finalproject.webapp.entity.Message;
import uow.finalproject.webapp.entity.Service;
import uow.finalproject.webapp.entity.User;


@Component
public class MessageDAO {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private UserDAO userDAO;
	
	
	public ArrayList<Message> findMessageBySenderAndReceiver(String senderName, String receiverName) {
		Connection conn = null;
		ArrayList<Message> messages = new ArrayList<Message>();
		String sql = "SELECT * FROM CheckBoxMessage WHERE (sender=? AND receiver=?) OR (receiver=? AND sender=?);";
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, senderName);
			ps.setString(2, receiverName);
			ps.setString(3, senderName);
			ps.setString(4, receiverName);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int messageID = rs.getInt("MessageID");
				String sender = rs.getString("sender");
				String receiver = rs.getString("receiver");
				String content = rs.getString("content");
				Date sendDate = rs.getTimestamp("sendTime");
				boolean read = rs.getBoolean("readMessage");
				
				Message service = new Message(messageID,userDAO.findUser(sender),userDAO.findUser(receiver),content,sendDate,read);
				messages.add(service);
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return messages;
	}
	
	public ArrayList<Message> findAllMessageByReceiver(String receiverName) {
		Connection conn = null;
		ArrayList<Message> messages = new ArrayList<Message>();
		String sql = "SELECT * FROM CheckBoxMessage WHERE receiver=? and sendTime=(SELECT max(sendTime) FROM CheckBoxMessage WHERE receiver=?)";
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, receiverName);
			ps.setString(2, receiverName);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int messageID = rs.getInt("MessageID");
				String sender = rs.getString("sender");
				String receiver = rs.getString("receiver");
				String content = rs.getString("content");
				Date sendDate = rs.getTimestamp("sendTime");
				boolean read = rs.getBoolean("readMessage");
				
				Message service = new Message(messageID,userDAO.findUser(sender),userDAO.findUser(receiver),content,sendDate,read);
				messages.add(service);
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return messages;
	}
	
	public int insertNewMessage(Message msg) {
		Connection conn = null;
		String sql = "INSERT INTO CheckBoxMessage(sender, receiver, content, sendTime, readMessage) VALUES(?,?,?,?,?);";
		try {
		conn = dataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, msg.getSender().getEmail());
			ps.setString(2, msg.getReceiver().getEmail());
			ps.setString(3, msg.getContent());
			ps.setDate(4, new java.sql.Date(msg.getSendTime().getTime()));
			ps.setBoolean(5, false);
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
