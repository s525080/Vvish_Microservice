package com.appdev.vvish.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.appdev.vvish.service.VVishService;

@RestController
@Configuration
public class Vvishcontroller {
	
	@Autowired
	VVishService vVService;
	
	@GetMapping(value = "/generate", 
			produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> getUserVideos(@RequestParam String groupId) {
		return vVService.generateVideo(groupId);
	}

}
