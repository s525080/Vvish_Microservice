package com.appdev.vvish.model;

import java.util.Date;

public class Group {

	private String groupId;
	private String title;
	private String description;
	private String type;
	private Date createdDate;
	private long targetUser;
	private Date targetDate;
	
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	public long getTargetUser() {
		return targetUser;
	}
	public void setTargetUser(long targetUser) {
		this.targetUser = targetUser;
	}
	
	public Date getTargetDate() {
		return targetDate;
	}
	public void setTargetDate(Date targetDate) {
		this.targetDate = targetDate;
	}
	
}
