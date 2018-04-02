package uow.finalproject.webapp.entity;

import java.util.Date;

public class Purchased {

	private int purchasedID;
	private String userName;
	private int serviceID;
	private Date purchasedTime;
	boolean providerCheck, customerCheck;
	int quantity;
	String transferredFrom;
	
	public Purchased(int purchasedID, String userName, int serviceID, Date purchasedTime, boolean providerCheck,
			boolean customerCheck, int quantity, String transferredFrom) {
		super();
		this.purchasedID = purchasedID;
		this.userName = userName;
		this.serviceID = serviceID;
		this.purchasedTime = purchasedTime;
		this.providerCheck = providerCheck;
		this.customerCheck = customerCheck;
		this.quantity = quantity;
		this.transferredFrom = transferredFrom;
	}
	
	public int getPurchasedID() {
		return purchasedID;
	}

	public void setPurchasedID(int purchasedID) {
		this.purchasedID = purchasedID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getServiceID() {
		return serviceID;
	}

	public void setServiceID(int serviceID) {
		this.serviceID = serviceID;
	}

	public Date getPurchasedTime() {
		return purchasedTime;
	}

	public void setPurchasedTime(Date purchasedTime) {
		this.purchasedTime = purchasedTime;
	}

	public boolean isProviderCheck() {
		return providerCheck;
	}

	public void setProviderCheck(boolean providerCheck) {
		this.providerCheck = providerCheck;
	}

	public boolean isCustomerCheck() {
		return customerCheck;
	}

	public void setCustomerCheck(boolean customerCheck) {
		this.customerCheck = customerCheck;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getTransferredFrom() {
		return transferredFrom;
	}

	public void setTransferredFrom(String transferredFrom) {
		this.transferredFrom = transferredFrom;
	}

}
