package uow.finalproject.webapp.entity;

import java.util.Date;

import uow.finalproject.webapp.entityType.Nationality;
import uow.finalproject.webapp.entityType.Suburb;

public class Message implements Comparable<Message> {
	// Mandatory fields
	int messageID;
	User sender;
	User receiver;
	String content;
	Date sendTime;
	boolean read;
	
	public Message(int messageID, User sender, User receiver, String content, Date sendTime, boolean read) {
		super();
		this.messageID = messageID;
		this.sender = sender;
		this.receiver = receiver;
		this.content = content;
		this.sendTime = sendTime;
		this.read = read;
	}
	public int getMessageID() {
		return messageID;
	}
	public void setMessageID(int messageID) {
		this.messageID = messageID;
	}
	public User getSender() {
		return sender;
	}
	public void setSender(User sender) {
		this.sender = sender;
	}
	public User getReceiver() {
		return receiver;
	}
	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public boolean isRead() {
		return read;
	}
	public void setRead(boolean read) {
		this.read = read;
	}
	@Override
	public int compareTo(Message o) {
		// TODO Auto-generated method stub
		return sendTime.compareTo(o.getSendTime());
	}
	
}
