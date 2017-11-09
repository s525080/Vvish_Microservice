package com.appdev.vvish;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.aopalliance.intercept.Invocation;
import org.json.simple.JSONArray;
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
	final Calendar cal = Calendar.getInstance();
	 final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	 
	public static void main(String[] args)  {
		// TODO Auto-generated method stub
		TestGroupModel callClient=new TestGroupModel();
		   
		JSONObject json=null;
		try {
			json=callClient.callgetMethod();	
			callClient.callpostMethod();
			callClient.callputMethod();
			callClient.calldeleteMethod();
		} catch (ClientHandlerException | UniformInterfaceException |  IOException | ParseException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<JSONObject> typeList=callClient.sortbyType(json);

	}

	private void calldeleteMethod() {
		// TODO Auto-generated method stub
	Client client = Client.create();	
	WebResource webResource = client.resource("https://vvish-new.firebaseio.com/Groups.json?auth=c42gihQ8uqKMNdlzbYi3xYMiBJL5l2ROSrklrf2a");
		//String location = "https://vvish-new.firebaseio.com/Groups.json?auth=c42gihQ8uqKMNdlzbYi3xYMiBJL5l2ROSrklrf2a";
	ClientResponse response = webResource.type("application/json").accept("application/json")
				.delete(ClientResponse.class); 
	}

	private void callputMethod() {
		// TODO Auto-generated method stub
		
	}

	private void callpostMethod() {
		// TODO Auto-generated method stub
		
	}

	public  ArrayList<JSONObject> sortbyType(JSONObject json) {
		ArrayList<JSONObject> typeList=new ArrayList<JSONObject>();
		ArrayList<JSONObject> finalList=new ArrayList<JSONObject>();

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
				JSONObject userValue= (JSONObject) value.get(userKey);
				log.info( "userValue :"+userValue);
				//log.info( "userValue :"+userVal);
				Set<String> groupKeys = userValue.keySet();


				for(String groupKey:groupKeys) {
					log.info( "groupKey :"+groupKey);

					//					String groupValue=(String) userValue.get(groupKey);
					//					log.info( "groupValue :"+groupValue);

					//groupKey.equalsIgnoreCase("role")&& userValue.get(groupKey).toString().equalsIgnoreCase("Owner")
					if (groupKey.equalsIgnoreCase("type")&& userValue.get(groupKey).toString().equalsIgnoreCase("Memories") ) {
						log.info( "entered content:"+groupKey);
						memoriesList.add(userValue);
						typeList=getOwnerList(memoriesList);
						finalList=memoriesFilterByDate(typeList);
					}else if(groupKey.equalsIgnoreCase("type")&& userValue.get(groupKey).toString().equalsIgnoreCase("Surprise")) {
						supriseList.add(userValue);
						typeList=getOwnerList(supriseList);
						memoriesFilterByDate(typeList);
						finalList=surpriseFilterByDate(typeList);
					}else if(groupKey.equalsIgnoreCase("type")&& userValue.get(groupKey).toString().equalsIgnoreCase("Capsule")) {
						capsuleList.add(userValue);
						typeList=getOwnerList(capsuleList);
					}

				}
			}			

		}
	//	log.info("currentDate :"+sysdate);
		log.info("memoriesList :"+memoriesList.size());
		log.info("supriseList :"+supriseList.size());
		log.info("capsuleList :"+capsuleList.size());
		log.info("typeList :"+typeList.size());
		return typeList;
	}


	private ArrayList<JSONObject> surpriseFilterByDate(ArrayList<JSONObject> list) {
		ArrayList<JSONObject> typeList=new ArrayList<JSONObject>();

		for(JSONObject capsules:list) {
			Set<String> groupKeys = capsules.keySet();
			for(String groupKey:groupKeys) {
				log.info( "groupKey :"+groupKey);
				 cal.add(Calendar.DATE, -1);
				String deliveryDate= dateFormat.format(cal.getTime());
				  log.info("yesterday date  :"+ dateFormat.format(cal.getTime()));
				if (groupKey.equalsIgnoreCase("todate")&& capsules.get(groupKey).toString().equalsIgnoreCase(deliveryDate) ) {
					log.info( "entered content:"+groupKey);
					typeList.add(capsules);
				}
			}
		}	
		return typeList;
	}

	private ArrayList<JSONObject> memoriesFilterByDate(ArrayList<JSONObject> list) {
		ArrayList<JSONObject> typeList=new ArrayList<JSONObject>();

		for(JSONObject capsules:list) {
			Set<String> groupKeys = capsules.keySet();
			for(String groupKey:groupKeys) {
				log.info( "groupKey :"+groupKey);
				 
				  cal.add(Calendar.DATE, +2);
				  String deliveryDate =dateFormat.format(cal.getTime());
				  log.info("tomorrow date  :"+ dateFormat.format(cal.getTime()));
				if (groupKey.equalsIgnoreCase("todate")&& capsules.get(groupKey).toString().equalsIgnoreCase(deliveryDate) ) {
					log.info( "entered content:"+groupKey);
					typeList.add(capsules);
				}
			}
		}	
		return typeList;
	}

	public ArrayList<JSONObject> getOwnerList(ArrayList<JSONObject> list) {
		ArrayList<JSONObject> typeList=new ArrayList<JSONObject>();

		for(JSONObject capsules:list) {
			Set<String> groupKeys = capsules.keySet();
			for(String groupKey:groupKeys) {
				log.info( "groupKey :"+groupKey);
				
				if (groupKey.equalsIgnoreCase("role")&& capsules.get(groupKey).toString().equalsIgnoreCase("Owner") ) {
					log.info( "entered content:"+groupKey);
					typeList.add(capsules);
				}
			}
		}	
		return typeList;
	}

	

	public JSONObject callgetMethod() throws  UniformInterfaceException,  IOException, ParseException {

		Client client = Client.create();
		
		WebResource webResource = client.resource("https://vvish-new.firebaseio.com/Groups.json?auth=c42gihQ8uqKMNdlzbYi3xYMiBJL5l2ROSrklrf2a");

		//String location = "https://vvish-new.firebaseio.com/Groups.json?auth=c42gihQ8uqKMNdlzbYi3xYMiBJL5l2ROSrklrf2a";
		ClientResponse response = webResource.type("application/json").accept("application/json")
				.get(ClientResponse.class); 
		log.info( "responseStatus :"+response.getStatus());
		String output=response.getEntity(String.class);
		log.info( "response :    "+output);
		JSONParser parser = new JSONParser(); 
		JSONObject json = (JSONObject) parser.parse(output);
		log.info("json"+json.toString());
		return json; 
	}


}
