package com.appdev.vvish.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageRequest {
	
	private String toPhNo;
	private String toName;
	private String fromPhNo;
	private String fromName;
	private String occassion;
	private String finalVid;
	private String msgType;
	
	public String getToPhNo() {
		return toPhNo;
	}
	public void setToPhNo(String toPhNo) {
		this.toPhNo = toPhNo;
	}
	public String getToName() {
		return toName;
	}
	public void setToName(String toName) {
		this.toName = toName;
	}
	public String getFromPhNo() {
		return fromPhNo;
	}
	public void setFromPhNo(String fromPhNo) {
		this.fromPhNo = fromPhNo;
	}
	public String getFromName() {
		return fromName;
	}
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	public String getOccassion() {
		return occassion;
	}
	public void setOccassion(String occassion) {
		this.occassion = occassion;
	}
	public String getFinalVid() {
		return finalVid;
	}
	public void setFinalVid(String finalVid) {
		this.finalVid = finalVid;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

}
