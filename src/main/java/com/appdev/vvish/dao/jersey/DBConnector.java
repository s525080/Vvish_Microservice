package com.appdev.vvish.dao.jersey;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.appdev.vvish.model.Groups;
import com.appdev.vvish.service.VVishService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.appdev.vvish.model.GroupTest;
import com.appdev.vvish.model.GroupUser;

@Component
@Configuration
public class DBConnector {
	VVishService service;
	Logger log = LoggerFactory.getLogger(DBConnector.class);
	
	public JSONObject fetchGroups() throws  UniformInterfaceException,  IOException, ParseException {

		Client client = Client.create();
		WebResource webResource = client.resource("https://vvish-new.firebaseio.com/Groups.json?auth=c42gihQ8uqKMNdlzbYi3xYMiBJL5l2ROSrklrf2a");
		ClientResponse response = webResource.type("application/json").accept("application/json")
				.get(ClientResponse.class); 
		log.info( "responseStatus :"+response.getStatus());
		String output=response.getEntity(String.class);
		response=null;
		log.info( "response :    "+output);
		JSONParser parser = new JSONParser(); 
		JSONObject json = (JSONObject) parser.parse(output);
		log.info("json"+json.toString());

		Calendar cal = Calendar.getInstance();
		final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		cal.add(Calendar.DATE, -1);
		String surpriseDate=dateFormat.format(cal.getTime()).substring(0, 10);
		System.out.println("surpriseDate"+surpriseDate);
		cal.add(Calendar.DATE, +3);
		String memoriesDate =dateFormat.format(cal.getTime()).substring(0, 10);
		System.out.println("memoriesDate :"+memoriesDate);
		sortbyType(json, memoriesDate, surpriseDate);
		return json;
	}
	
	public  String sortbyType(JSONObject json,String memoriesDate, String surpriseDate) throws ParseException {

		ArrayList<JSONObject> supriseList=new ArrayList<JSONObject>();
		ArrayList<JSONObject> memoriesList=new ArrayList<JSONObject>();
		ArrayList<JSONObject> capsuleList=new ArrayList<JSONObject>();
		ArrayList<JSONObject> finalCapsuleList=new ArrayList<JSONObject>();
		String memoriesresponse;
		String surpriseresponse;
		log.info("memoriesDate date  :"+ memoriesDate);
		log.info("surpriseDate date  :"+ surpriseDate);
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
				//log.info( "userKey :"+userKey);
				JSONObject userValue= (JSONObject) value.get(userKey);
				//log.info( "userValue :"+userValue);
				
				Set<String> groupKeys = userValue.keySet();


				for(String groupKey:groupKeys) {
					//log.info( "groupKey :"+groupKey);

					if (groupKey.equalsIgnoreCase("type")&& userValue.get(groupKey).toString().equalsIgnoreCase("Memories") ) {
						log.info( "entered content:"+groupKey);
						memoriesList.add(userValue);
						memoriesresponse=filterMemories(memoriesList,key,userKey,memoriesDate);

					}else if(groupKey.equalsIgnoreCase("type")&& userValue.get(groupKey).toString().equalsIgnoreCase("Surprise")) {
						supriseList.add(userValue);
						surpriseresponse=fileterSuprises(supriseList,key,userKey,surpriseDate);

					}else if(groupKey.equalsIgnoreCase("type")&& userValue.get(groupKey).toString().equalsIgnoreCase("Capsule")) {
						capsuleList.add(userValue);
						finalCapsuleList=getOwnerList(capsuleList);
					}
				}
			}			

		}
		//	log.info("currentDate :"+sysdate);
		log.info("memoriesList :"+memoriesList.size());
		log.info("supriseList :"+supriseList.size());
		log.info("capsuleList :"+capsuleList.size());

		return "Final URL is obtained" ;
	}
	public String insertVideoUrl(String userId, String groupId, String finalVideo) throws ParseException {
		Client client =null;
		ClientResponse response = null;
		WebResource webResource=null;
		String input="\""+finalVideo+"\"";
		System.out.println("input :"+input);
		client = Client.create();
		String inputURL="https://vvish-new.firebaseio.com/Groups/"+userId+"/"+groupId+"/finalVideo.json?auth=c42gihQ8uqKMNdlzbYi3xYMiBJL5l2ROSrklrf2a";
		System.out.println("inputURL :"+inputURL);
		webResource = client.resource(inputURL);		
		response = webResource.type("application/json").accept("text/html").put(ClientResponse.class,input );
		String responses=response.getEntity(String.class);
		System.out.println("after put :"+responses);
		return responses;
	}



	public String callpostMethod(JSONObject inputJSON) {
		ClientResponse response = null;
		Client client = Client.create();
		WebResource webResource = client.resource("https://vvish-new.firebaseio.com/Groups.json?auth=c42gihQ8uqKMNdlzbYi3xYMiBJL5l2ROSrklrf2a");	
		response = webResource.type("application/json").accept("application/json").post(ClientResponse.class, inputJSON.toString());
		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}			
		return response.getEntity(String.class);
	}

	private String fileterSuprises(ArrayList<JSONObject> surpriseList, String key, String userKey,String surpriseDate ) throws ParseException {
		// TODO Auto-generated method stub
		ArrayList<JSONObject> surpriseTypeList=new ArrayList<JSONObject>();		
		ArrayList<JSONObject> finalList=new ArrayList<JSONObject>();
		String	output="No Owner for particular Date";
		int surpriseTypeListSize=surpriseTypeList.size();
		surpriseTypeList=surpriseFilterByDate( surpriseList,surpriseDate);
		if(surpriseTypeList.size()>surpriseTypeListSize) {
			System.out.println("System.out.println(surpriseTypeList.size());"+surpriseTypeList.size());
		
			finalList=getOwnerList(surpriseTypeList);
		//	finalList=surpriseFilterByDate( surpriseTypeList,surpriseDate);
			int finalsurpriseListSize=finalList.size();
			if(finalList.size()>finalsurpriseListSize) {
				System.out.println("surprise finalList"+finalList.size());
				
		 output=service.generateSurpriseVideoFromFinalList(key,userKey,finalList);
			}
		}

		return output;
	}

	private String filterMemories(ArrayList<JSONObject> memoriesList, String key, String userKey,String memoriesDate) throws ParseException {
		// TODO Auto-generated method stub
		ArrayList<JSONObject> memoriesTypeList=new ArrayList<JSONObject>();
		ArrayList<JSONObject> finalList=new ArrayList<JSONObject>();
		String	output="No Owner for particular date";
		int memoriesTypeListSize= memoriesTypeList.size();
		memoriesTypeList=memoriesFilterByDate(memoriesList, memoriesDate);
		if(memoriesTypeList.size()>memoriesTypeListSize) {
		System.out.println("memoriesTypeList.size()"+memoriesTypeList.size());			
		finalList=getOwnerList(memoriesTypeList);
		int finalListSize=finalList.size();
		if(finalList.size()>finalListSize) {
			System.out.println("memories finalList"+finalList.size());
		//finalList=memoriesFilterByDate(memoriesTypeList, memoriesDate);
			output=service.generateMemoriesVideoFromFinalList(key,userKey,finalList);
		}
		}
		return output;

	}
	
	public ArrayList<JSONObject> getOwnerList(ArrayList<JSONObject> inputlist) {
		ArrayList<JSONObject> ownerList=new ArrayList<JSONObject>();

		for(JSONObject capsules:inputlist) {
			Set<String> groupKeys = capsules.keySet();
			for(String groupKey:groupKeys) {
				//log.info( "groupKey :"+groupKey);

				if (groupKey.equalsIgnoreCase("role")&& capsules.get(groupKey).toString().equalsIgnoreCase("Owner") ) {
					log.info( "entered content:"+groupKey+"  value       :"+capsules.get(groupKey).toString() );
					ownerList.add(capsules);
				}
			}
		}	
		return ownerList;
	}
	private ArrayList<JSONObject> memoriesFilterByDate(ArrayList<JSONObject> list,String memoriesDate) {
		ArrayList<JSONObject> typeList=new ArrayList<JSONObject>();

		for(JSONObject capsules:list) {
			Set<String> groupKeys = capsules.keySet();
			for(String groupKey:groupKeys) {
				//log.info( "groupKey :"+groupKey);				
				if (groupKey.equalsIgnoreCase("todate")&& capsules.get(groupKey).toString().substring(0, 10).equalsIgnoreCase(memoriesDate) ) {
					log.info( "entered content:"+groupKey);
					typeList.add(capsules);
				}
			}
		}	
		return typeList;
	}

	private ArrayList<JSONObject> surpriseFilterByDate(ArrayList<JSONObject> list,String surpriseDate) {
		ArrayList<JSONObject> typeList=new ArrayList<JSONObject>();
		for(JSONObject capsules:list) {
			Set<String> groupKeys = capsules.keySet();
			for(String groupKey:groupKeys) {
				//log.info( "groupKey :"+groupKey);				
				
				if (groupKey.equalsIgnoreCase("todate")&& capsules.get(groupKey).toString().substring(0, 10).equalsIgnoreCase(surpriseDate) ) {
					log.info( "entered content:"+groupKey+surpriseDate);
					typeList.add(capsules);
				}
			}
		}	
		return typeList;
	}
}
