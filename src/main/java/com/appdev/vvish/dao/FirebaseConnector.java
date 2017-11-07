package com.appdev.vvish.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.appdev.vvish.model.Groups;
import com.appdev.vvish.model.GroupUser;


@RestController
@Configuration
public class FirebaseConnector {
	Logger log = LoggerFactory.getLogger(FirebaseConnector.class);
	
	private String URL = "https://vvish-new.firebaseio.com";
	private String AUTH_TOKEN = "?auth=c42gihQ8uqKMNdlzbYi3xYMiBJL5l2ROSrklrf2a";
	
	private String groupUserRoot = "/UserGroups.json";
	private String userRoot = "/Users.json";
	private String groupRoot = "/Groups.json";
	private RestTemplate restTemplate = new RestTemplate();
	
	public List<Groups> getUsersByGroup() throws Exception {
	log.info("get user method");
		final String targetURL = URL+groupRoot+AUTH_TOKEN;
 		return this.callService(targetURL, HttpMethod.GET, Groups.class);

		
	}
	public List<GroupUser> getGroup(String groupId) throws Exception {
		
		String targetURL = URL+groupUserRoot+AUTH_TOKEN+"&orderBy=\"role\"&equalTo=\"Owner\"";
		
		return this.callService(targetURL, HttpMethod.GET, GroupUser.class);
		
	}
	
	@SuppressWarnings("unchecked")
	private <T> List<T> callService(String url, HttpMethod methodType, Class<T> responseObj) {
		log.info("check call serv");
		// set headers
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.setContentType(MediaType.APPLICATION_JSON);
        
		HttpEntity<String> entity = new HttpEntity<>(headers);
		
		// send request and parse result
		return restTemplate.exchange(url, methodType, entity, List.class).getBody();
	}

}
