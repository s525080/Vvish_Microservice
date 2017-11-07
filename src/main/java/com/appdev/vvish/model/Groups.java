package com.appdev.vvish.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Groups  {
	private String creator;
	private String finalVideo;
	private String groupMatchKey;
	private String owner;
	private String photoUrl;
	private String role;
	private String videoUrl;
	private String groupId;
	private String title;
	private String description;
	private String type;
	private Date date;
	private Date fromdate;
	private Date modifiedDate;
	private long targetUser;
	private Date toDate;
	private ArrayList<String> mediaFiles;
	private List<Contacts> contacts;
	private List<Contacts> target;
	public Groups() {
		super();
	}
	public Groups(String creator, String finalVideo, String owner, String photoUrl, String role, String videoUrl,
			String type, Date date, Date fromdate, Date modifiedDate, long targetUser, Date toDate,
			ArrayList<String> mediaFiles, List<Contacts> contacts) {
		super();
		this.creator = creator;
		this.finalVideo = finalVideo;
		this.owner = owner;
		this.photoUrl = photoUrl;
		this.role = role;
		this.videoUrl = videoUrl;
		this.type = type;
		this.date = date;
		this.fromdate = fromdate;
		this.modifiedDate = modifiedDate;
		this.targetUser = targetUser;
		this.toDate = toDate;
		this.mediaFiles = mediaFiles;
		this.contacts = contacts;
	}
	@Override
	public String toString() {
		return "Group [creator=" + creator + ", finalVideo=" + finalVideo + ", photoUrl=" + photoUrl + ", role=" + role
				+ ", videoUrl=" + videoUrl + ", type=" + type + ", date=" + date + ", fromdate=" + fromdate
				+ ", modifiedDate=" + modifiedDate + ", targetUser=" + targetUser + ", toDate=" + toDate
				+ ", mediaFiles=" + mediaFiles + ", contacts=" + contacts + "]";
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getFinalVideo() {
		return finalVideo;
	}
	public void setFinalVideo(String finalVideo) {
		this.finalVideo = finalVideo;
	}
	public String getGroupMatchKey() {
		return groupMatchKey;
	}
	public void setGroupMatchKey(String groupMatchKey) {
		this.groupMatchKey = groupMatchKey;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getFromdate() {
		return fromdate;
	}
	public void setFromdate(Date fromdate) {
		this.fromdate = fromdate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public long getTargetUser() {
		return targetUser;
	}
	public void setTargetUser(long targetUser) {
		this.targetUser = targetUser;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public ArrayList<String> getMediaFiles() {
		return mediaFiles;
	}
	public void setMediaFiles(ArrayList<String> mediaFiles) {
		this.mediaFiles = mediaFiles;
	}
	public List<Contacts> getContacts() {
		return contacts;
	}
	public void setContacts(List<Contacts> contacts) {
		this.contacts = contacts;
	}
	
	
	
}
