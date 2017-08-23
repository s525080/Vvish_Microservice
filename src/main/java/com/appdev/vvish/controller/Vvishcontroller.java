package com.appdev.vvish.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appdev.vvish.model.VideoCategory;
import com.appdev.vvish.service.UserVideosRenderingService;

@RestController
@Configuration
public class Vvishcontroller {
	
	@Autowired
	UserVideosRenderingService uVRService;
	
	@GetMapping(value = "/retrieveUserVideos", 
			produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<VideoCategory>> getUserVideos() {
		return ResponseEntity.status(HttpStatus.OK).body(this.uVRService.getUserVideos());
	}

}
