package com.appdev.vvish.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.appdev.vvish.model.Group;
import com.appdev.vvish.service.SMSService;
import com.appdev.vvish.service.VVishService;

@RestController
@Configuration
public class Vvishcontroller {
	
	private static final Logger LOG = LoggerFactory.getLogger(Vvishcontroller.class);
	
	@Autowired
	VVishService vVService;
	
	@Autowired
	SMSService smsService;
	
	@PostMapping(value = "/generateVideo/{userId}/{groupId}", 
			produces = { MediaType.APPLICATION_JSON_VALUE },
			consumes = {MediaType.APPLICATION_JSON_VALUE} )
	public String getUserSelectedVideos(@PathVariable String userId, 
			@PathVariable String groupId, @RequestBody List<String> mediaFiles) {
		LOG.info("Generating Memorable Video from the Videos selected..");
		return vVService.generateMemoriesVideo(groupId, userId, mediaFiles);	
	}
	
	@PostMapping(value = "/generateSurpriseVideo/{userId}/{groupId}", 
			produces = { MediaType.APPLICATION_JSON_VALUE },
			consumes = {MediaType.APPLICATION_JSON_VALUE} )
	public  String getUserSelectedVideos(@PathVariable String userId, @PathVariable String groupId, 
			@RequestBody Group groupObj) {
		return vVService.generateSurpriseVideoFromFinalList(groupId,userId,groupObj);
	}
	
	@PostMapping(value = "/invite",
			consumes = { MediaType.APPLICATION_JSON_VALUE },
			produces = { MediaType.APPLICATION_JSON_VALUE } )
	public ResponseEntity<String> sendMessage(@RequestBody String msgDetails) {
		LOG.info("Received request to sendMessage - "+ msgDetails);
		return this.smsService.sendMessage(msgDetails);
	}
	
}
