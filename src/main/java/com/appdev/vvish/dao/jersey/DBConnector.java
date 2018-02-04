package com.appdev.vvish.dao.jersey;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.appdev.vvish.model.Group;
import com.appdev.vvish.model.Metamember;
import com.appdev.vvish.service.VVishService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Component
@Configuration
public class DBConnector {
    public static final String FIREBASE_URL = "https://vvish-new.firebaseio.com/Groups.json?auth=c42gihQ8uqKMNdlzbYi3xYMiBJL5l2ROSrklrf2a";
    public static final String MEDIA_TYPE = "application/json";
    @Autowired
    VVishService service;
    Logger log = LoggerFactory.getLogger(DBConnector.class);

    public JSONObject fetchGroups() throws Exception {

        ClientResponse response = getGetResponse();
        log.info("responseStatus :" + response.getStatus());
        String output = response.getEntity(String.class);
        response = null;
        JSONObject json = parseGetResponse(output);

        Calendar cal = Calendar.getInstance();
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String surpriseDate = getSurpriseDate(cal, dateFormat);
        String memoriesDate = getMemoriesDate(cal, dateFormat);
        log.info("json is"+json.toString());
        System.out.println("json is"+json.toString());
        sortbyType(json, memoriesDate, surpriseDate);
        return json;
    }

    public String insertVideoUrl(String userId, String groupId, String finalVideo,Group groupObj) throws ParseException {

        Client client = null;
        ClientResponse response = null;
        WebResource webResource = null;
        String input = "\"" + finalVideo + "\"";
        log.info("input :" + input);
        client = Client.create();
        String inputURL = "https://vvish-new.firebaseio.com/Groups/" + userId + "/" + groupId + "/finalVideo.json?auth=c42gihQ8uqKMNdlzbYi3xYMiBJL5l2ROSrklrf2a";
        log.info("inputURL :" + inputURL);
        webResource = client.resource(inputURL);
        response = webResource.type("application/json").accept("text/html").put(ClientResponse.class, input);
        String responses = response.getEntity(String.class);
        log.info("after put : :" + responses);
        return responses;
    }

    private String getMemoriesDate(Calendar cal, final DateFormat dateFormat) {
        cal.add(Calendar.DATE, -1);
        String memoriesDate = dateFormat.format(cal.getTime()).substring(0, 10);
        System.out.println("memoriesDate :" + memoriesDate);
        return memoriesDate;
    }

    private String getSurpriseDate(Calendar cal, final DateFormat dateFormat) {
        //Next Day Surprise List date
        cal.add(Calendar.DATE, +1);
        String surpriseDate = dateFormat.format(cal.getTime()).substring(0, 10);
        System.out.println("surpriseDate" + surpriseDate);
        return surpriseDate;
    }

    private JSONObject parseGetResponse(String output) throws ParseException {
        log.info("response :    " + output);
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(output);
        log.info("json" + json.toString());
        return json;
    }

    private ClientResponse getGetResponse() {
        Client client = Client.create();
        WebResource webResource = client.resource(FIREBASE_URL);
        ClientResponse response = webResource.type(MEDIA_TYPE).accept(MEDIA_TYPE)
                .get(ClientResponse.class);
        return response;
    }

    public String sortbyType(JSONObject json, String memoriesDate, String surpriseDate) throws Exception {

    	ObjectMapper mapper = new ObjectMapper();
        Group groupObj = null;
        List<JSONObject> capsuleList = new ArrayList<JSONObject>();
        JSONObject obj = new JSONObject(json);
        Set<String> set = obj.keySet();

        log.info("memoriesDate date  :" + memoriesDate);
        log.info("surpriseDate date  :" + surpriseDate);

        for (String key : set) {
            JSONObject value = (JSONObject) obj.get(key);
            Set<String> userSet = value.keySet();

            //log.info("value :" + value);
            log.info("key :" + key);
            log.info("userSet :" + userSet);

            for (String userKey : userSet) {
                JSONObject userValue = (JSONObject) value.get(userKey);
                mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
                groupObj = mapper.readValue(userValue.toJSONString(), Group.class);

                log.info("Group Object - "+ groupObj.toString());
                log.info("isValidSurpriseGroup - "+ groupObj.isValidSurpriseGroup());
                log.info("isValidMemoriesGroup - "+ groupObj.isValidMemoriesGroup());

                    if (groupObj.isValidMemoriesGroup()) {
                    	System.out.println("in generate memories");
                        service.generateMemoriesVideoFromFinalList(key,userKey,groupObj);
                    } else if (groupObj.isValidSurpriseGroup()) {
                    	System.out.println("in generate surprise");
                        service.generateSurpriseVideoFromFinalList(key,userKey,groupObj);
                    } else {
                    	System.out.println("in generate capsule");
                        capsuleList.add(userValue);
                        getOwnerList(capsuleList);
                    }
            }

        }
        log.info("capsuleList :" + capsuleList.size());

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


    public List<JSONObject> getOwnerList(List<JSONObject> inputlist) {
        List<JSONObject> ownerList = new ArrayList<JSONObject>();

        for (JSONObject capsules : inputlist) {
            Set<String> groupKeys = capsules.keySet();
            for (String groupKey : groupKeys) {
                //log.info( "groupKey :"+groupKey);

                if (groupKey.equalsIgnoreCase("role") && capsules.get(groupKey).toString().equalsIgnoreCase("Owner")) {
                    log.info("entered content:" + groupKey + "  value       :" + capsules.get(groupKey).toString());
                    ownerList.add(capsules);
                }
            }
        }
        return ownerList;
    }

}
