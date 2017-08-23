package com.appdev.vvish.model;

import java.util.Date;

public class VideoDetails {

	private String title;
	private String videoUrl;
	private Date date;
	
	public VideoDetails(String title, String videoUrl, Date date) {
		this.title = title;
		this.videoUrl = videoUrl;
		this.date = date;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
