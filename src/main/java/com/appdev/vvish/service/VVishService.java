package com.appdev.vvish.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.appdev.vvish.dao.FirebaseConnector;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;


@Service
public class VVishService {

	@Autowired
	FirebaseConnector firebaseConnector;
	@Autowired
	VideoStitchingService videoStichService;
	
	public ResponseEntity<String> generateVideo(String groupId, String userId, String[] mediaFiles) {
		
		try {
			videoStichService.createMediaTextFile(mediaFiles);
			
			videoStichService.stitchImagesToVideo("./tmp/");
			
			Storage storage = StorageOptions.newBuilder()
	                .setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("/assets/serviceAccountKey.json")))
	                .build()
	                .getService();
			 
			//StorageReference riversRef = storage.child("images/"+file.getLastPathSegment());
			//uploadTask = riversRef.putFile(file);
			
		
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
}
