package com.appdev.vvish.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.appdev.vvish.service.VVishService;

@RestController
@Configuration
public class Vvishcontroller {
	
	@Autowired
	VVishService vVService;
	
	/*@GetMapping(value = "/generate", 
			produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> getUserVideos(@RequestParam String groupId) {
		return vVService.generateVideo(groupId);
	}*/
	@PostMapping(value = "/generateVideo/{userId}/{groupId}", 
			produces = { MediaType.APPLICATION_JSON_VALUE },
			consumes = {MediaType.APPLICATION_JSON_VALUE} )
	public  String[] getUserSelectedVideos(@PathVariable String userId, @PathVariable String groupId, @RequestBody String[] mediaFiles) {
		vVService.generateVideo(groupId, userId, mediaFiles);
		
		return mediaFiles;	
	}
	
	//trial
	@GetMapping(value = "/generate", 
			produces =  MediaType.TEXT_PLAIN_VALUE)
	public  String getUser2() {
		return "RETURN";	
	}
	
	
	
}
