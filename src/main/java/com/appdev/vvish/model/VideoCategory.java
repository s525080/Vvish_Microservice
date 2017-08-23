package com.appdev.vvish.model;

import java.util.List;

public class VideoCategory {
	
	private String name;
	private List<VideoDetails> videos;
	
	public VideoCategory(String name, List<VideoDetails> videos) {
		this.name = name;
		this.videos = videos;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<VideoDetails> getVideos() {
		return videos;
	}

	public void setVideos(List<VideoDetails> videos) {
		this.videos = videos;
	}
	
}
