package com.appdev.vvish.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.appdev.vvish.dao.FirebaseConnector;
import com.appdev.vvish.dao.jersey.DBConnector;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;


@Service
public class VVishService {

	DBConnector dbConnector;
	VideoStitchingService videoStichService;
	final Logger log = LoggerFactory.getLogger(VVishService.class);
	
	public String generateVideo(String groupId, String userId, String[] mediaFiles) {
		
		try {
			videoStichService.createMediaTextFile(mediaFiles);
			videoStichService.stitchImagesToVideo("./tmp/");
			Storage storage = StorageOptions.newBuilder()
	                .setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("/assets/serviceAccountKey.json")))
	                .build()
	                .getService();
		
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
		String url=generateVideo(key, userKey, mediaFiles);
		System.out.println("Generated video ");
		System.out.println("Before PUT:" +key+"userKey :"+userKey+"URL :"+url);
		String output = dbConnector.insertVideoUrl(key, userKey, url);
		System.out.println("URL"+url);
		return output;
	}

	public String generateMemoriesVideoFromFinalList(String key, String userKey, java.util.List<JSONObject> finalList) throws ParseException {
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
				if (groupKey.equalsIgnoreCase("contacts") ) {
					JSONObject contactDetails=new JSONObject();
					contactDetails=(JSONObject) capsules.get(groupKey);
					
					log.info( "entered mediaFiles contacts:"+contactDetails.get("mediaFiles").toString());
					mediaList.add(contactDetails.get("mediaFiles").toString());
				}
			}
		}
		String[] mediaFiles = mediaList.toArray(new String[0]);
		String url=generateVideo(key, userKey, mediaFiles);
		System.out.println("Generated video ");
		System.out.println("Before PUT:" +key+"userKey :"+userKey+"URL :"+url);
		String output = dbConnector.insertVideoUrl(key, userKey, url);
		System.out.println("URL"+url);
		return output;
	}

}
