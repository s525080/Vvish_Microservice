package com.appdev.vvish.service;

import com.appdev.vvish.dao.jersey.DBConnector;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;


@Service
public class VVishService {

	DBConnector dbConnector;
	@Autowired
	VideoStitchingService videoStichService;
	final Logger log = LoggerFactory.getLogger(VVishService.class);
	
	public String generateSurpriseVideo(String groupId, String userId, String[] mediaFiles) {
		
		try {
//			videoStichService.createMediaTextFile(mediaFiles);
//			videoStichService.stitchImagesToVideo("./tmp/")String[] mediaFiles = {"https://firebasestorage.googleapis.com/v0/b/vvish-new.appspot.com/o/videos%2Fa.mp4?alt=media&token=b3abbc6f-701f-49fb-8785-5ca9b71ff282","https://firebasestorage.googleapis.com/v0/b/vvish-new.appspot.com/o/videos%2FOqwFJpBzckf9k07PguoCAfWr5c52%2F-Kyct6m4HkW1EBjjHQE2?alt=media&token=9a4ecef9-08e2-440a-8ebf-4582dd525085","https://firebasestorage.googleapis.com/v0/b/vvish-new.appspot.com/o/images%2Fj6besXtHhIgeBAY28tpAngbqMY63%2F-KxyKl0eh7iB14HILpdu%2Fwedding-pictures-26813-27529-hd-wallpapers.jpg?alt=media&token=8aa63d5c-0e5c-40bd-ba8f-39f469b175fb","https://firebasestorage.googleapis.com/v0/b/vvish-new.appspot.com/o/images%2Fj6besXtHhIgeBAY28tpAngbqMY63%2F-KxyKl0eh7iB14HILpdu%2FWendy_Erwin_WED_0578.jpg?alt=media&token=7723d1d4-cd64-441c-8ad4-61e544609937","https://firebasestorage.googleapis.com/v0/b/vvish-new.appspot.com/o/images%2Fj6besXtHhIgeBAY28tpAngbqMY63%2F-KxyKl0eh7iB14HILpdu?alt=media&token=18e99141-6f05-46f2-b87c-1c58ac62812d","https://firebasestorage.googleapis.com/v0/b/vvish-new.appspot.com/o/images%2Fj6besXtHhIgeBAY28tpAngbqMY63%2F-KxyKl0eh7iB14HILpdu%2Fbanner-home-02.jpg?alt=media&token=55763dd5-c646-4d10-8a8d-bad7ece8eaa7","https://firebasestorage.googleapis.com/v0/b/vvish-new.appspot.com/o/videos%2FLightStreaksDeepBlueHD.mp4?alt=media&token=bac58fc3-8bb7-4691-aa8c-e5cca946f0e6"};
	//		String[] mediaFiles = {"https://firebasestorage.googleapis.com/v0/b/vvish-new.appspot.com/o/videos%2Fa.mp4?alt=media&token=b3abbc6f-701f-49fb-8785-5ca9b71ff282","https://firebasestorage.googleapis.com/v0/b/vvish-new.appspot.com/o/videos%2FOqwFJpBzckf9k07PguoCAfWr5c52%2F-Kyct6m4HkW1EBjjHQE2?alt=media&token=9a4ecef9-08e2-440a-8ebf-4582dd525085","https://firebasestorage.googleapis.com/v0/b/vvish-new.appspot.com/o/images%2Fj6besXtHhIgeBAY28tpAngbqMY63%2F-KxyKl0eh7iB14HILpdu%2Fwedding-pictures-26813-27529-hd-wallpapers.jpg?alt=media&token=8aa63d5c-0e5c-40bd-ba8f-39f469b175fb","https://firebasestorage.googleapis.com/v0/b/vvish-new.appspot.com/o/images%2Fj6besXtHhIgeBAY28tpAngbqMY63%2F-KxyKl0eh7iB14HILpdu%2FWendy_Erwin_WED_0578.jpg?alt=media&token=7723d1d4-cd64-441c-8ad4-61e544609937","https://firebasestorage.googleapis.com/v0/b/vvish-new.appspot.com/o/images%2Fj6besXtHhIgeBAY28tpAngbqMY63%2F-KxyKl0eh7iB14HILpdu?alt=media&token=18e99141-6f05-46f2-b87c-1c58ac62812d","https://firebasestorage.googleapis.com/v0/b/vvish-new.appspot.com/o/images%2Fj6besXtHhIgeBAY28tpAngbqMY63%2F-KxyKl0eh7iB14HILpdu%2Fbanner-home-02.jpg?alt=media&token=55763dd5-c646-4d10-8a8d-bad7ece8eaa7","https://firebasestorage.googleapis.com/v0/b/vvish-new.appspot.com/o/videos%2FLightStreaksDeepBlueHD.mp4?alt=media&token=bac58fc3-8bb7-4691-aa8c-e5cca946f0e6"};

			Arrays.stream(new File("./surprise_media").listFiles()).forEach(File::delete);
			ArrayList<String> videoFiles = new ArrayList<String>() ;
			ArrayList<String> imageFiles= new ArrayList<String>() ;
			int i = 0;
			for(String media: mediaFiles){
				System.out.println("inside");
				String checkStr ="videos";
				i++;
				if(media.toLowerCase().contains(checkStr.toLowerCase())){
					videoFiles.add(media);
				}else{
					imageFiles.add(media);
				}
			}

			//
			System.out.println(videoFiles.toString());
			System.out.println(imageFiles.toString());
			Arrays.stream(new File("./tmp").listFiles()).forEach(File::delete);
			videoStichService.createVideoTextFile(videoFiles);
			videoStichService.createImageTextFile(imageFiles);
			videoStichService.surpriseFlow("./surprise_media");
			
			
		} catch (InterruptedException| IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String generateMemoriesVideo(String groupId, String userId, String[] mediaFiles) {
		
		try {
	//		String[] mediaFiles = {"https://firebasestorage.googleapis.com/v0/b/vvish-new.appspot.com/o/images%2Fj6besXtHhIgeBAY28tpAngbqMY63%2F-KxyKl0eh7iB14HILpdu%2Fwedding-pictures-26813-27529-hd-wallpapers.jpg?alt=media&token=8aa63d5c-0e5c-40bd-ba8f-39f469b175fb","https://firebasestorage.googleapis.com/v0/b/vvish-new.appspot.com/o/images%2Fj6besXtHhIgeBAY28tpAngbqMY63%2F-KxyKl0eh7iB14HILpdu%2FWendy_Erwin_WED_0578.jpg?alt=media&token=7723d1d4-cd64-441c-8ad4-61e544609937","https://firebasestorage.googleapis.com/v0/b/vvish-new.appspot.com/o/images%2Fj6besXtHhIgeBAY28tpAngbqMY63%2F-KxyKl0eh7iB14HILpdu?alt=media&token=18e99141-6f05-46f2-b87c-1c58ac62812d","https://firebasestorage.googleapis.com/v0/b/vvish-new.appspot.com/o/images%2Fj6besXtHhIgeBAY28tpAngbqMY63%2F-KxyKl0eh7iB14HILpdu%2Fbanner-home-02.jpg?alt=media&token=55763dd5-c646-4d10-8a8d-bad7ece8eaa7"};

		     Arrays.stream(new File("./tmp").listFiles()).forEach(File::delete);

			videoStichService.createMediaTextFile(mediaFiles);


			videoStichService.createImageVideo(2);
			
			
		} catch (InterruptedException| IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String generateSurpriseVideoFromFinalList(String key, String userKey, java.util.List<JSONObject> finalList) throws ParseException {
		// TODO Auto-generated method stub
		ArrayList<String> mediaList=new ArrayList<String>();

		for(JSONObject capsules:finalList) {
			Set<String> groupKeys = capsules.keySet();
			for(String groupKey:groupKeys) {
				//log.info( "groupKey :"+groupKey);				
				if (groupKey.equalsIgnoreCase("mediaFiles") ) {
					log.info( "entered content:"+groupKey);
					mediaList.add(capsules.get(groupKey).toString());
				}
			}
		}
		String[] mediaFiles = mediaList.toArray(new String[0]);
		String url=generateSurpriseVideo(key, userKey, mediaFiles);
		System.out.println("Generated video ");
		System.out.println("Before PUT:" +key+"userKey :"+userKey+"URL :"+url);
		String output = dbConnector.insertVideoUrl(key, userKey, url);
		System.out.println("URL"+url);
		return output;
	}

	public String generateMemoriesVideoFromFinalList(String key, String userKey, java.util.List<JSONObject> finalList) throws ParseException {
		ArrayList<String> mediaList=new ArrayList<String>();

		for(JSONObject capsules:finalList) {
			Set<String> groupKeys = capsules.keySet();
			for(String groupKey:groupKeys) {
				//log.info( "groupKey :"+groupKey);				
				if (groupKey.equalsIgnoreCase("mediaFiles") ) {
					log.info( "entered content:"+groupKey);
					mediaList.add(capsules.get(groupKey).toString());
				}
				if (groupKey.equalsIgnoreCase("contacts") ) {
					JSONObject contactDetails=new JSONObject();
					contactDetails=(JSONObject) capsules.get(groupKey);
					
					log.info( "entered mediaFiles contacts:"+contactDetails.get("mediaFiles").toString());
					mediaList.add(contactDetails.get("mediaFiles").toString());
				}
			}
		}
		String[] mediaFiles = mediaList.toArray(new String[0]);
		String url=generateMemoriesVideo(key, userKey, mediaFiles);
		System.out.println("Generated video ");
		System.out.println("Before PUT:" +key+"userKey :"+userKey+"URL :"+url);
		String output = dbConnector.insertVideoUrl(key, userKey, url);
		System.out.println("URL"+url);
		return output;
	}

}
