package com.appdev.vvish;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;



import org.aopalliance.intercept.Invocation;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appdev.vvish.dao.FirebaseConnector;
import com.appdev.vvish.model.GroupTest;
import com.appdev.vvish.model.Groups;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
		try {
			tgm.Staticmethod();			
		} catch (ClientHandlerException | UniformInterfaceException | ParseException  | JSONException | IOException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String Staticmethod() throws  UniformInterfaceException, ParseException, JSONException, IOException {
		
		Client client = Client.create();
		// For URL Add Connector Name/Delete
		WebResource webResource = client.resource("https://vvish-new.firebaseio.com/Groups.json?auth=c42gihQ8uqKMNdlzbYi3xYMiBJL5l2ROSrklrf2a");

		//String location = "https://vvish-new.firebaseio.com/Groups.json?auth=c42gihQ8uqKMNdlzbYi3xYMiBJL5l2ROSrklrf2a";
		ClientResponse response = webResource.type("application/json").accept("application/json")
				.get(ClientResponse.class); // Getting status & tasks for a connector demo-connector/status
		
		log.info( "response :    "+response.getEntity(String.class));
		log.info( "responseStatus :"+response.getStatus());
		//log.info( "response :"+response.getEntity(GroupTest.class));
		 response.getType() ;
		List<GroupTest>   result = (List<GroupTest>) response.getEntity(GroupTest.class);
		log.info( "result :    "+result.toString()); 
//		StringBuilder output=new StringBuilder();
//		output.append(response.getEntity(String.class));
//		JSONParser parser = new JSONParser(); 
//		JSONObject json = (JSONObject) parser.parse(output.toString());
//		log.info("json"+json.toString());
		return "trial"; 
	}
	
	
}
