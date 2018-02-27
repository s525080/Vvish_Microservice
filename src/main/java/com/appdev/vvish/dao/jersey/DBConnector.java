package com.appdev.vvish.dao.jersey;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.appdev.vvish.model.Group;
import com.appdev.vvish.service.VVishService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Component
@Configuration
public class DBConnector {
	
	private static final Logger LOG = LoggerFactory.getLogger(DBConnector.class);
	
    public static final String FIREBASE_URL = "https://vvish-new.firebaseio.com/Groups.json?auth=c42gihQ8uqKMNdlzbYi3xYMiBJL5l2ROSrklrf2a";
    public static final String MEDIA_TYPE = "application/json";
    
    @Autowired
    VVishService service;
    
    public JSONObject fetchAndProcessGroups() throws Exception {

    	Client client = Client.create();
        WebResource webResource = client.resource(FIREBASE_URL);
        ClientResponse response = webResource.type(MEDIA_TYPE).accept(MEDIA_TYPE).get(ClientResponse.class);
        LOG.info("Response Status :" + response.getStatus());
        
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(response.getEntity(String.class));
        LOG.debug("JSON is - "+json.toString());
        
        sortbyType(json);
        
        return json;
    }

    public String insertVideoUrl(String userId, String groupId, String finalVideo, Group groupObj) {

        String input = "\"" + finalVideo + "\"";
        LOG.info("input :" + input);
        Client client = Client.create();
        String inputURL = "https://vvish-new.firebaseio.com/Groups/" + userId + "/" + groupId + "/finalVideo.json?auth=c42gihQ8uqKMNdlzbYi3xYMiBJL5l2ROSrklrf2a";
        LOG.info("inputURL :" + inputURL);
        ClientResponse clientResp = client.resource(inputURL).type("application/json").accept("text/html").put(ClientResponse.class, input);
        String response = clientResp.getEntity(String.class);
        LOG.info("after put : :" + response);
        
        return response;
    }

    @SuppressWarnings("unchecked")
	public String sortbyType(JSONObject json) throws Exception {

    	ObjectMapper mapper = new ObjectMapper();
        List<JSONObject> capsuleList = new ArrayList<JSONObject>();
        
        JSONObject obj = new JSONObject(json);
        Set<String> set = obj.keySet();
        Group groupObj = null;
        for (String key : set) {
            JSONObject value = (JSONObject) obj.get(key);
            Set<String> userSet = value.keySet();

            LOG.info("key :" + key);
            LOG.info("userSet :" + userSet);

            for (String userKey : userSet) {
                JSONObject userValue = (JSONObject) value.get(userKey);
                mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
                groupObj = mapper.readValue(userValue.toJSONString(), Group.class);

                LOG.info("Group Object - "+ groupObj.toString());
                LOG.info("isValidSurpriseGroup - "+ groupObj.isValidSurpriseGroup());
                LOG.info("isValidMemoriesGroup - "+ groupObj.isValidMemoriesGroup());

                if (groupObj.isValidMemoriesGroup()) {
                	LOG.info("in generate memories");
                    service.generateMemoriesVideoFromFinalList(key, userKey, groupObj);
                } else if (groupObj.isValidSurpriseGroup()) {
                	LOG.info("in generate surprise");
                    service.generateSurpriseVideoFromFinalList(key, userKey, groupObj);
                } else {
                	LOG.info("in generate capsule");
                    capsuleList.add(userValue);
                    getOwnerList(capsuleList);
                    LOG.info("capsuleList :" + capsuleList.size());
                }
            }

        }

        return "Final URL is obtained";
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

    @SuppressWarnings("unchecked")
	public List<JSONObject> getOwnerList(List<JSONObject> inputlist) {
        List<JSONObject> ownerList = new ArrayList<JSONObject>();

        for (JSONObject capsules : inputlist) {
            Set<String> groupKeys = capsules.keySet();
            for (String groupKey : groupKeys) {
                //log.info( "groupKey :"+groupKey);

                if (groupKey.equalsIgnoreCase("role") && capsules.get(groupKey).toString().equalsIgnoreCase("Owner")) {
                    LOG.info("entered content:" + groupKey + "  value       :" + capsules.get(groupKey).toString());
                    ownerList.add(capsules);
                }
            }
        }
        return ownerList;
    }

}
