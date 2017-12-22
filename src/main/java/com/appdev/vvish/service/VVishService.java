package com.appdev.vvish.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appdev.vvish.dao.jersey.DBConnector;
import com.appdev.vvish.model.Group;
import com.appdev.vvish.service.stitching.Main;
import com.appdev.vvish.service.stitching.SettingsPath;


@Service
public class VVishService {

	@Autowired
	DBConnector dbConnector;
	@Autowired
	VideoStitchingService videoStichService;
	@Autowired
	StitchingService stitchingService;
	@Autowired
	ImageStitchingService imageStitchingService;
	@Autowired
	FirebaseStorage firebaseStorage;
	final Logger log = LoggerFactory.getLogger(VVishService.class);
	
	public String generateSurpriseVideo(String groupId, String userId, List<String> mediaFiles) throws Exception {
		String finalUrl = null;
		try {
			Arrays.stream(new File("./surprise_media").listFiles()).forEach(File::delete);
			ArrayList<String> videoFiles = new ArrayList<String>() ;
			ArrayList<String> imageFiles= new ArrayList<String>() ;
			segregateVideosImages(mediaFiles, videoFiles, imageFiles);
			Arrays.stream(new File("./tmp").listFiles()).forEach(File::delete);
//			videoStichService.createVideoTextFile(videoFiles);
//			videoStichService.createImageTextFile(imageFiles);
//			videoStichService.surpriseFlow("./surprise_media");
//			
			
			//new Code
			String crd = "./tmp/";
			System.out.println(new File(crd).getCanonicalPath());
			crd=new File(crd).getCanonicalPath();
			SettingsPath.WIN32_FFMPEG=new File("./lib/ffmpeg.exe").getCanonicalPath();
			
			SettingsPath.MENCODER=new File("./lib/mencoder.exe").getCanonicalPath();
			System.out.println(new File(SettingsPath.WIN32_FFMPEG).exists());
//			ArrayList<String> videoList = new ArrayList<>();
//			videoList.add("https://firebasestorage.googleapis.com/v0/b/vvish-new.appspot.com/o/videos%2FAcyS4U1uhPZRZTUYAvNv6otUbHH2%2F-L-m224sP1hdzPANjaTp?alt=media&token=32227b95-6bdb-4506-88e5-4920285828b8");
//			videoList.add("https://firebasestorage.googleapis.com/v0/b/vvish-new.appspot.com/o/videos%2FAcyS4U1uhPZRZTUYAvNv6otUbHH2%2F-L-m1brVDmg56feIssOB?alt=media&token=f7773d73-9f07-4d9d-a334-838d6b299d24");
////			videoList.add("https://firebasestorage.googleapis.com/v0/b/vvish-new.appspot.com/o/videos%2FAcyS4U1uhPZRZTUYAvNv6otUbHH2%2F-L-m1CjXivskZDBv6Kqj?alt=media&token=b9ba3164-dc5c-4ae4-8d16-b54d665a6336");

			String path = stitchingService.MergeUrlList(videoFiles);
			System.out.println("path is"+path);
			finalUrl= firebaseStorage.uploadtoStorage(path, groupId, userId);
			
		} catch (InterruptedException| IOException e) {
			e.printStackTrace();
		}
		
		return finalUrl;
	}

	private void segregateVideosImages(List<String> mediaFiles, ArrayList<String> videoFiles, ArrayList<String> imageFiles) {
		log.info("Entered segregateVideosImages");

		for(String media: mediaFiles){
            String checkStr ="videos";
            if(media.toLowerCase().contains(checkStr.toLowerCase())){
                videoFiles.add(media);
            }else{
                imageFiles.add(media);
            }
        }
	}

	public String generateMemoriesVideo(String groupId, String userId, List<String> mediaFiles) throws Exception {
		String finalUrl = null;
		try {
			Arrays.stream(new File("./tmp").listFiles()).forEach(File::delete);
//			videoStichService.createMediaTextFile(mediaFiles);
//			videoStichService.memoriesFlow(2);
			Collections.shuffle(mediaFiles);
			String path = imageStitchingService.generateImageVideo(mediaFiles);
			System.out.println("path is"+path);
			finalUrl = firebaseStorage.uploadtoStorage(path, groupId, userId);
			//finalUrl = firebaseStorage.uploadtoStorage("./tmp/img_finalvideo.mp4", groupId, userId);

		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}

		return finalUrl;
	}
	
	public String generateSurpriseVideoFromFinalList(String key, String userKey, Group groupObj) throws Exception {
		log.info("Entered generateSurpriseVideoFromFinalList");
		System.out.println("Entered generateSurpriseVideoFromFinalList");
		String url=generateSurpriseVideo(key, userKey, groupObj.getMediaFiles());
		return insertUrl(key, userKey, url);
	}

	private String insertUrl(String key, String userKey, String url) throws ParseException {
		return dbConnector.insertVideoUrl(key, userKey, url);
	}

	public String generateMemoriesVideoFromFinalList(String key, String userKey, Group groupObj) throws Exception {
		log.info("Entered generateMemoriesVideoFromFinalList");
		String url=generateMemoriesVideo(key, userKey, groupObj.getMediaFiles());
		return insertUrl(key, userKey, url);
	}

}
