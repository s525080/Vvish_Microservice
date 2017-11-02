package com.appdev.vvish.dao;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.appdev.vvish.model.GroupUser;


@Component
public class FirebaseConnector {
	
	private String URL = "https://vvish-91286.firebaseio.com/";
	private String AUTH_TOKEN = "?auth=MYi72wQwEqT7iMXJiBTBXUEzaL3Cr2ezKqDwnUIM";
	
	private String groupUserRoot = "/UserGroups.json";
	private String userRoot = "/Users.json";
	private String groupRoot = "/Groups.json";
	
	private RestTemplate restTemplate = new RestTemplate();
	
	public List<GroupUser> getUsersByGroup(String groupId) throws Exception {
	
		String targetURL = URL+groupUserRoot+AUTH_TOKEN+"&orderBy=\"role\"&equalTo=\"Owner\"";
		
		return this.callService(targetURL, HttpMethod.GET, GroupUser.class);
		
	}
	
	@SuppressWarnings("unchecked")
	private <T> List<T> callService(String url, HttpMethod methodType, Class<T> responseObj) {
		
		// set headers
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.setContentType(MediaType.APPLICATION_JSON);
        
		HttpEntity<String> entity = new HttpEntity<>(headers);
		
		// send request and parse result
		return restTemplate.exchange(url, methodType, entity, List.class).getBody();
	}

}
