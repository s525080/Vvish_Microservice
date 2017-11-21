package com.appdev.vvish.controller;

import com.appdev.vvish.service.VVishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
		vVService.generateMemoriesVideo(groupId, userId, mediaFiles);
		
		return mediaFiles;	
	}
	
	//trial
	@GetMapping(value = "/generate", 
			produces =  MediaType.TEXT_PLAIN_VALUE)
	public  String getUser2() {
		return "RETURN";	
	}
	
	
	
}
