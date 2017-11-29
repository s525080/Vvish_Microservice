package com.appdev.vvish.service;

import com.appdev.vvish.dao.jersey.DBConnector;
import com.appdev.vvish.model.Group;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;


@Service
public class VVishService {

	@Autowired
	DBConnector dbConnector;
	@Autowired
	VideoStitchingService videoStichService;
	@Autowired
	FirebaseStorage firebaseStorage;
	final Logger log = LoggerFactory.getLogger(VVishService.class);
	
	public String generateSurpriseVideo(String groupId, String userId, List<String> mediaFiles) {
		String finalUrl = null;
		try {
			Arrays.stream(new File("./surprise_media").listFiles()).forEach(File::delete);
			ArrayList<String> videoFiles = new ArrayList<String>() ;
			ArrayList<String> imageFiles= new ArrayList<String>() ;
			
			for(String media: mediaFiles){
				System.out.println("inside");
				String checkStr ="videos";
				
				if(media.toLowerCase().contains(checkStr.toLowerCase())){
					videoFiles.add(media);
				}else{
					imageFiles.add(media);
				}
			}
			System.out.println(videoFiles.toString());
			System.out.println(imageFiles.toString());
			Arrays.stream(new File("./tmp").listFiles()).forEach(File::delete);
			videoStichService.createVideoTextFile(videoFiles);
			videoStichService.createImageTextFile(imageFiles);
			videoStichService.surpriseFlow("./surprise_media");
			System.out.println("user id" +userId);
			System.out.println("group id" +groupId);
			finalUrl= firebaseStorage.uploadtoStorage("./surprise_media/final_video.mp4", groupId, userId);
			
		} catch (InterruptedException| IOException e) {
			e.printStackTrace();
		}
		
		return finalUrl;
	}
	
	public String generateMemoriesVideo(String groupId, String userId, List<String> mediaFiles) {
		String finalUrl = null;
		try {
			Arrays.stream(new File("./tmp").listFiles()).forEach(File::delete);
			videoStichService.createMediaTextFile(mediaFiles);
			videoStichService.memoriesFlow(2);
			finalUrl = firebaseStorage.uploadtoStorage("./tmp/img_finalvideo.mp4", groupId, userId);

		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}

		return finalUrl;
	}
	
	public String generateSurpriseVideoFromFinalList(String key, String userKey, Group groupObj) throws ParseException {
		log.info("Entered generateSurpriseVideoFromFinalList");
		String url=generateSurpriseVideo(key, userKey, groupObj.getMediaFiles());
		String output = dbConnector.insertVideoUrl(key, userKey, url);
		return output;
	}

	public String generateMemoriesVideoFromFinalList(String key, String userKey, Group groupObj) throws ParseException {
		log.info("Entered generateMemoriesVideoFromFinalList");
		String url=generateMemoriesVideo(key, userKey, groupObj.getMediaFiles());
		String output = dbConnector.insertVideoUrl(key, userKey, url);
		return output;
	}

}
