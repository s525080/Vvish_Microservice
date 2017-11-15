package com.appdev.vvish.dao;

import com.appdev.vvish.model.GroupTest;
import com.appdev.vvish.model.GroupUser;
import com.appdev.vvish.model.Groups;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
@Configuration
public class FirebaseConnector {
	Logger log = LoggerFactory.getLogger(FirebaseConnector.class);
	
	private static String URL = "https://vvish-new.firebaseio.com";
	private static String AUTH_TOKEN = "?auth=c42gihQ8uqKMNdlzbYi3xYMiBJL5l2ROSrklrf2a";
	
	private static String groupUserRoot = "/UserGroups.json";
	private static String userRoot = "/Users.json";
	private static String groupRoot = "/Groups.json";
	private static RestTemplate restTemplate = new RestTemplate();
	
	public static List<Groups> getUsersByGroup() throws Exception {
		final String targetURL = URL+groupRoot+AUTH_TOKEN;
 		return callService(targetURL, HttpMethod.GET, Groups.class);

		
	}
	public List<GroupUser> getGroup(String groupId) throws Exception {
		
		String targetURL = URL+groupRoot+AUTH_TOKEN;
		
		return callService(targetURL, HttpMethod.GET, GroupUser.class);
		
	}
	
	@SuppressWarnings("unchecked")
	private static  <T> List<T> callService(String url, HttpMethod methodType, Class<T> responseObj) {
		// set headers
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.setContentType(MediaType.APPLICATION_JSON);
        
		HttpEntity<String> entity = new HttpEntity<>(headers);
		
		// send request and parse result
		ResponseEntity<GroupTest> res =restTemplate.getForEntity(url, GroupTest.class);
		return restTemplate.exchange(url, methodType, entity, List.class).getBody();
	}

}
