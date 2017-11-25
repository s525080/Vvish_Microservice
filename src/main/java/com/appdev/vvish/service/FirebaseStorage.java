package com.appdev.vvish.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.appdev.vvish.dao.FirebaseConnector;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.WriteChannel;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.cloud.StorageClient;

@Component
public class FirebaseStorage {
	
	public String uploadtoStorage(String path,String groupId, String userId) throws IOException {
		 Storage storage = null;
		 String finalurl;
		final  Logger log = LoggerFactory.getLogger(FirebaseConnector.class);
			
			FileInputStream serviceAccount = new FileInputStream("./assets/serviceAccountKey.json");

			FirebaseOptions options = new FirebaseOptions.Builder()
			    .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
			    .setStorageBucket("vvish-new.appspot.com")
			    .build();
			FirebaseApp.initializeApp(options);
			
			
			Bucket bucket = StorageClient.getInstance().bucket();
			log.info("bucket is "+bucket);
			
			 storage = StorageOptions.newBuilder().setProjectId("vvish-new")
					.setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("./assets/serviceAccountKey.json")))
					.build()
					.getService();
			 
			String blobName = userId+groupId;
			
			
			
			
			FileInputStream inputStream = new FileInputStream(new File(path));
			BlobInfo blobInfo = BlobInfo.newBuilder(bucket.getName(), blobName)
					.setContentType("video/mp4")
					.setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER))))
					.build();
			log.info("bucket name is "+bucket.getName());
			BlobId blobId = BlobId.of(bucket.getName(), blobName);
			try (WriteChannel writer = storage.writer(blobInfo)) {
				byte[] buffer = new byte[1024];
				int limit;
				try {
					while ((limit = inputStream.read(buffer)) >= 0) {
						writer.write(ByteBuffer.wrap(buffer, 0, limit));
					}

				} catch (Exception ex) {
					// handle exception
				}finally {
					writer.close();
				}
			}
			log.info("link is "+storage.get(blobId).getSelfLink());
			log.info("link is "+storage.get(blobId).getMediaLink());
			
			finalurl = storage.get(blobId).getMediaLink();
		
		return finalurl;
	}
	
	 /**
	   * Checks that the file extension is supported.
	   */
	  private void checkFileExtension(String fileName) throws ServletException {
	    if (fileName != null && !fileName.isEmpty() && fileName.contains(".")) {
	      String[] allowedExt = { ".mp4", ".mov", ".MP4", ".gif" };
	      for (String ext : allowedExt) {
	        if (fileName.endsWith(ext)) {
	          return;
	        }
	      }
	      throw new ServletException("file must be an video");
	    }
	  }
	  // [END checkFileExtension]

}
