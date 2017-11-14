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
import org.springframework.beans.factory.annotation.Autowired;

import com.appdev.vvish.dao.FirebaseConnector;
import com.appdev.vvish.model.Groups;
import com.appdev.vvish.service.VVishService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

public class TestGroupModel {
	final Logger log = LoggerFactory.getLogger(FirebaseConnector.class);
	VVishService vVService=new VVishService();
	static FirebaseConnector connector=new FirebaseConnector();
	public static void main(String[] args) throws Exception   {
		TestGroupModel callClient=new TestGroupModel();
		JSONObject json=null;
		//List<Groups> grp = connector.getUsersByGroup();
//		try {
//			//json=callClient.callgetMethod();	
//		} catch (ClientHandlerException | UniformInterfaceException| ParseException |IOException    e) {
//			e.printStackTrace();
//		}
	}


}
