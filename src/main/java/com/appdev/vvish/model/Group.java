package com.appdev.vvish.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.appdev.vvish.constants.VVishConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Group {
	
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private String role;
	private String type;
	private String todate;
	private String finalVideo;
	private List<String> mediaFiles;
	private List<Contacts> contacts;
	private List<Contacts> target;
	private List<Metamember> membersGroupId;
	
	public boolean isValidSurpriseGroup() throws ParseException {
		return ((VVishConstants.SURPRISE_GROUP.equalsIgnoreCase(this.type)) && isFinalVideoNotGenerated() &&
				this.isOwner() && this.isTime(VVishConstants.SURPRISE_GROUP));
	}
	
	public boolean isValidMemoriesGroup() throws ParseException {
		return ((VVishConstants.MEMORIES_GROUP.equalsIgnoreCase(this.type)) && isFinalVideoNotGenerated() &&
				this.isOwner() && this.isTime(VVishConstants.MEMORIES_GROUP));
	}
	
	public boolean isOwner() {
		return VVishConstants.GROUP_OWNER.equalsIgnoreCase(this.role);
	}
	
	public boolean isFinalVideoNotGenerated() {
		return (this.finalVideo.isEmpty());
	}
	
	public boolean isTime(String groupType) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		
		if(VVishConstants.SURPRISE_GROUP.equalsIgnoreCase(this.type)) {
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		if(VVishConstants.MEMORIES_GROUP.equalsIgnoreCase(this.type)) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		
		
		return (sdf.format(cal.getTime()).equals(sdf.format(this.getTodate())));
	}
	
	public List<String> memoryMedia() {
		
		List<String> mediaList = new ArrayList<>();
		for(Contacts contact : this.contacts) {
			mediaList.addAll(contact.getMediaFiles());
		}
		
		return mediaList;
	}
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public Date getTodate() throws ParseException {
		System.out.println("date is"+this.sdf.parse(todate));
		return this.sdf.parse(todate);
	}
	
	public void setTodate(String todate) {
		this.todate = todate;
	}
	
	public String getFinalVideo() {
		return finalVideo;
	}

	public void setFinalVideo(String finalVideo) {
		this.finalVideo = finalVideo;
	}

	public List<String> getMediaFiles() {
		return mediaFiles;
	}
	public void setMediaFiles(List<String> mediaFiles) {
		this.mediaFiles = mediaFiles;
	}
	
	public List<Contacts> getContacts() {
		return contacts;
	}
	public void setContacts(List<Contacts> contacts) {
		this.contacts = contacts;
	}
	
	public List<Contacts> getTarget() {
		return target;
	}
	public void setTarget(List<Contacts> target) {
		this.target = target;
	}
	
	

	public List<Metamember> getMembersGroupId() {
		return membersGroupId;
	}

	public void setMembersGroupId(List<Metamember> membersGroupId) {
		this.membersGroupId = membersGroupId;
	}

	@Override
	public String toString() {
		return "Group [Role=" + role + ", Type=" + type + ", ToDate=" + todate + ", finalVideo="
				+ finalVideo +" membersGroupId"+membersGroupId.toString()+ "]";
	}
	
}