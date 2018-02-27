package com.appdev.vvish.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appdev.vvish.constants.VVishConstants;
import com.appdev.vvish.dao.jersey.DBConnector;
import com.appdev.vvish.model.Group;
import com.appdev.vvish.model.Metamember;
import com.appdev.vvish.service.stitching.SettingsPath;

@Service
public class VVishService {

	private static final Logger LOG = LoggerFactory.getLogger(VVishService.class);
	
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
	
	public String generateSurpriseVideo(String groupId, String userId, List<String> mediaFiles) {
		String finalUrl = null;
		try {
			Arrays.stream(new File("./surprise_media").listFiles()).forEach(File::delete);
			ArrayList<String> videoFiles = new ArrayList<String>() ;
			ArrayList<String> imageFiles= new ArrayList<String>() ;

			//Segregate Video and Image files
			for(String media: mediaFiles){
	            String checkStr ="videos";
	            if(media.toLowerCase().contains(checkStr.toLowerCase())){
	                videoFiles.add(media);
	            }else{
	                imageFiles.add(media);
	            }
	        }

			Arrays.stream(new File("./tmp").listFiles()).forEach(File::delete);
			
			//new Code
			String crd = new File("./tmp/").getCanonicalPath();
			LOG.debug(crd);

			SettingsPath.WIN32_FFMPEG=new File("./lib/ffmpeg.exe").getCanonicalPath();
			SettingsPath.MENCODER=new File("./lib/mencoder.exe").getCanonicalPath();
			LOG.debug(new File(SettingsPath.WIN32_FFMPEG).exists() + "");

			String path = stitchingService.MergeUrlList(videoFiles);
			LOG.debug("path is"+path);
			finalUrl= firebaseStorage.uploadtoStorage(path, groupId, userId);
			
		} catch (Exception e) {
			LOG.error("Exception while generating Surprise Video .. ", e);
		}
		
		return finalUrl;
	}

	public String generateMemoriesVideo(String groupId, String userId, List<String> mediaFiles) {
		String finalUrl = null;
		try {
			Arrays.stream(new File("./tmp").listFiles()).forEach(File::delete);
			Collections.shuffle(mediaFiles);
			String path = imageStitchingService.generateImageVideo(mediaFiles);
			LOG.debug("path is"+path);
			finalUrl = firebaseStorage.uploadtoStorage(path, groupId, userId);

		} catch (Exception e) {
			LOG.error("Exception while generating Memorable Video .. "+ e);
		}

		return finalUrl;
	}
	
	public String generateSurpriseVideoFromFinalList(String key, String userKey, Group groupObj) {
		LOG.info("Entered generateSurpriseVideoFromFinalList");
		String url = generateSurpriseVideo(key, userKey, groupObj.getMediaFiles());

		if(!url.isEmpty()) {
			return insertUrl(key, userKey, url,groupObj);
		}
		
		return VVishConstants.FAILURE;
	}

	private String insertUrl(String key, String userKey, String url, Group groupObj) {

		dbConnector.insertVideoUrl(key, userKey, url, groupObj);
		for (Metamember member : groupObj.getMembersGroupId()) {
			LOG.info("hey" + member.getUid() + " and " + member.getGroupid());
			if (member.getGroupid() != "" && member.getUid() != "") {
				LOG.debug("Inside" + member.getUid() + " and " + member.getGroupid());
				dbConnector.insertVideoUrl(member.getUid(), member.getGroupid(), url, groupObj);
			}
		}
		return VVishConstants.SUCCESS;
	}

	public String generateMemoriesVideoFromFinalList(String key, String userKey, Group groupObj) throws Exception {
		LOG.info("Entered generateMemoriesVideoFromFinalList");
		String url=generateMemoriesVideo(key, userKey, groupObj.memoryMedia());
	
		return insertUrl(key, userKey, url, groupObj);
	}

}
