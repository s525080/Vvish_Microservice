package com.appdev.vvish;

import com.appdev.vvish.dao.FirebaseConnector;
import com.appdev.vvish.service.VVishService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
