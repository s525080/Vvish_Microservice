package com.appdev.vvish;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.aopalliance.intercept.Invocation;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appdev.vvish.dao.FirebaseConnector;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;





public class TestGroupModel {
	final Logger log = LoggerFactory.getLogger(FirebaseConnector.class);

	public static void main(String[] args)  {
		// TODO Auto-generated method stub
		TestGroupModel tgm=new TestGroupModel();
		JSONObject json=null;
		try {
			json=tgm.Staticmethod();			
		} catch (ClientHandlerException | UniformInterfaceException |  IOException | ParseException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<JSONObject> typeList=tgm.sortbyType(json);

	}

	public  ArrayList<JSONObject> sortbyType(JSONObject json) {
		ArrayList<JSONObject> typeList=new ArrayList<JSONObject>();

		ArrayList<JSONObject> supriseList=new ArrayList<JSONObject>();
		ArrayList<JSONObject> memoriesList=new ArrayList<JSONObject>();
		ArrayList<JSONObject> capsuleList=new ArrayList<JSONObject>();

		JSONObject obj = new JSONObject(json);
		Set<String> set = obj.keySet();
		//String key = null;
		for(String key:set) {   
			log.info( "key :"+key);
			JSONObject value= (JSONObject) obj.get(key);
			log.info( "value :"+value);
			Set<String> userSet = value.keySet();
			log.info( "userSet :"+userSet);
			
			for(String userKey:userSet) { 
				log.info( "userKey :"+userKey);
				JSONObject userValue= (JSONObject) obj.get(key);
				log.info( "userValue :"+userValue);
				Set<String> groupKeys = userValue.keySet();
				
				for(String groupKey:groupKeys) {
					log.info( "groupKey :"+groupKey);
					String groupValue=  (String) obj.get(groupKey);
					log.info( "groupValue :"+groupValue);
					
					//Set<String> internalKeys = groupValue.keySet();
					
//					for(String content:internalKeys) { 
//						
//						log.info( "content :"+content);
//						if (content.equalsIgnoreCase("type")) {
//							log.info( "entered content:"+content);
//						}
//					}
				}
			}
		}

		return typeList;
	}
	public JSONObject Staticmethod() throws  UniformInterfaceException,  IOException, ParseException {

		Client client = Client.create();
		// For URL Add Connector Name/Delete
		WebResource webResource = client.resource("https://vvish-new.firebaseio.com/Groups.json?auth=c42gihQ8uqKMNdlzbYi3xYMiBJL5l2ROSrklrf2a");

		//String location = "https://vvish-new.firebaseio.com/Groups.json?auth=c42gihQ8uqKMNdlzbYi3xYMiBJL5l2ROSrklrf2a";
		ClientResponse response = webResource.type("application/json").accept("application/json")
				.get(ClientResponse.class); // Getting status & tasks for a connector demo-connector/status

		//	log.info( "response :    "+response.getEntity(String.class));
		log.info( "responseStatus :"+response.getStatus());
		//log.info( "response :"+response.getEntity(GroupTest.class));

		//StringBuilder output=new StringBuilder();
		String output=response.getEntity(String.class);
		log.info( "response :    "+output);
		JSONParser parser = new JSONParser(); 
		JSONObject json = (JSONObject) parser.parse(output);
		log.info("json"+json.toString());
		return json; 
	}


}
