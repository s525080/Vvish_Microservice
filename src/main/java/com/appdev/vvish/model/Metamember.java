package com.appdev.vvish.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Metamember {

	private String uid;
	private String groupid;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	public Metamember(String uid, String groupid) {
		super();
		this.uid = uid;
		this.groupid = groupid;
	}
	public Metamember() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Metamember [uid=" + uid + ", groupid=" + groupid + "]";
	}
    
	
	
	
	
	
}