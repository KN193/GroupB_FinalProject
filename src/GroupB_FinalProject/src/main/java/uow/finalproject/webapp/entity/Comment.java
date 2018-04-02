package uow.finalproject.webapp.entity;

import java.util.Date;

import uow.finalproject.webapp.entityType.Nationality;
import uow.finalproject.webapp.entityType.Suburb;

public class Comment {
	// Mandatory fields
	int serviceID;
	int commentID;
	int previousComment;
	User commenter;
	String commentInfo;
	boolean visible;
	Date commentTime;
	
	public Comment(int serviceID, int commentID, int previousComment, User commenter, String commentInfo, boolean visible,
			Date commentTime) {
		super();
		this.serviceID = serviceID;
		this.commentID = commentID;
		this.previousComment = previousComment;
		this.commenter = commenter;
		this.commentInfo = commentInfo;
		this.visible = visible;
		this.commentTime = commentTime;
	}
	
	public Comment(int serviceID, User commenter, String commentInfo, boolean visible, Date commentTime) {
		this.serviceID = serviceID;
		this.commenter = commenter;
		this.commentInfo = commentInfo;
		this.visible = visible;
		this.commentTime = commentTime;
	}

	public int getServiceID() {
		return serviceID;
	}
	public void setServiceID(int serviceID) {
		this.serviceID = serviceID;
	}
	public int getCommentID() {
		return commentID;
	}
	public void setCommentID(int commentID) {
		this.commentID = commentID;
	}
	public User getCommenter() {
		return commenter;
	}
	public void setCommenter(User commenter) {
		this.commenter = commenter;
	}
	public String getCommentInfo() {
		return commentInfo;
	}
	public void setCommentInfo(String commentInfo) {
		this.commentInfo = commentInfo;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public Date getCommentTime() {
		return commentTime;
	}
	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}
}
