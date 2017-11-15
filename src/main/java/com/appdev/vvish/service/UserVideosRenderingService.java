package com.appdev.vvish.service;

import com.appdev.vvish.model.VideoCategory;
import com.appdev.vvish.model.VideoDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserVideosRenderingService {

	public List<VideoCategory> getUserVideos() {
		
		List<VideoCategory> videoCategories = new ArrayList<>();
		
		List<VideoDetails> videoList = new ArrayList<>();
		videoList.add(new VideoDetails("Abishai Bday", "https://www.youtube.com/watch?v=HYunGRPQiJY", new Date()));
		videoList.add(new VideoDetails("Vincent Bday", "https://www.youtube.com/watch?v=HYunGRPQiJY", new Date()));
		videoList.add(new VideoDetails("Benny Bday", "https://www.youtube.com/watch?v=HYunGRPQiJY", new Date()));
		videoList.add(new VideoDetails("Sam Bday", "https://www.youtube.com/watch?v=HYunGRPQiJY", new Date()));
		videoCategories.add(new VideoCategory("For Me!", videoList));
		
		videoList = new ArrayList<>();
		videoList.add(new VideoDetails("Abhishek's Marraige", "https://www.youtube.com/watch?v=HYunGRPQiJY", new Date()));
		videoList.add(new VideoDetails("Naveen Bday", "https://www.youtube.com/watch?v=HYunGRPQiJY", new Date()));
		videoCategories.add(new VideoCategory("Videos I Initiated!", videoList));
		
		videoList = new ArrayList<>();
		videoList.add(new VideoDetails("Abhishai Birthday", "https://www.youtube.com/watch?v=HYunGRPQiJY", new Date()));
		videoCategories.add(new VideoCategory("Birthdays!", videoList));
		
		videoList = new ArrayList<>();
		videoList.add(new VideoDetails("Abhishek's Marraige", "https://www.youtube.com/watch?v=HYunGRPQiJY", new Date()));
		videoList.add(new VideoDetails("Naveen Bday", "https://www.youtube.com/watch?v=HYunGRPQiJY", new Date()));
		videoCategories.add(new VideoCategory("Anniversaries!", videoList));
		
		videoList = new ArrayList<>();
		videoList.add(new VideoDetails("Abhishek's Marraige", "https://www.youtube.com/watch?v=HYunGRPQiJY", new Date()));
		videoList.add(new VideoDetails("Naveen Bday", "https://www.youtube.com/watch?v=HYunGRPQiJY", new Date()));
		videoCategories.add(new VideoCategory("Graduation!", videoList));
		
		videoList = new ArrayList<>();
		videoList.add(new VideoDetails("Abhishek's Marraige", "https://www.youtube.com/watch?v=HYunGRPQiJY", new Date()));
		videoList.add(new VideoDetails("Naveen Bday", "https://www.youtube.com/watch?v=HYunGRPQiJY", new Date()));
		videoCategories.add(new VideoCategory("Miscellaneous!", videoList));
		
		return videoCategories;
	}
}
