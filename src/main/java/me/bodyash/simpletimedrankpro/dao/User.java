package me.bodyash.simpletimedrankpro.dao;

public class User {
	private String userName;
	private long untilDate;
	private long fromDate;
	private String promotedRank;
	private String oldRank;
	private int status;
	
	public User(){
		
	}
	
	public User(String userName, long untilDate, long fromDate, String promotedRank, String oldRank, int status){
		this.userName = userName;
		this.untilDate = untilDate;
		this.fromDate = fromDate;
		this.promotedRank = promotedRank;
		this.oldRank = oldRank;
		this.status = status;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public long getUntilDate() {
		return untilDate;
	}
	public void setUntilDate(long untilDate) {
		this.untilDate = untilDate;
	}
	public long getFromDate() {
		return fromDate;
	}
	public void setFromDate(long fromDate) {
		this.fromDate = fromDate;
	}
	public String getPromotedRank() {
		return promotedRank;
	}
	public void setPromotedRank(String promotedRank) {
		this.promotedRank = promotedRank;
	}
	public String getOldRank() {
		return oldRank;
	}
	public void setOldRank(String oldRank) {
		this.oldRank = oldRank;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

}
