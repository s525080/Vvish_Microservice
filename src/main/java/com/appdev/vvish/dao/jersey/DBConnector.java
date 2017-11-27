package com.appdev.vvish.dao.jersey;

import com.appdev.vvish.model.Group;
import com.appdev.vvish.service.VVishService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

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
        sortbyType(json, memoriesDate, surpriseDate);
        return json;
    }

    public String insertVideoUrl(String userId, String groupId, String finalVideo) throws ParseException {
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
        
        List<JSONObject> supriseList = new ArrayList<JSONObject>();
        List<JSONObject> memoriesList = new ArrayList<JSONObject>();
        List<JSONObject> capsuleList = new ArrayList<JSONObject>();
        List<JSONObject> finalCapsuleList = new ArrayList<JSONObject>();
        String memoriesresponse;
        String surpriseresponse;
        log.info("memoriesDate date  :" + memoriesDate);
        log.info("surpriseDate date  :" + surpriseDate);
        JSONObject obj = new JSONObject(json);
        Set<String> set = obj.keySet();
        //String key = null;
        for (String key : set) {
            log.info("key :" + key);
            JSONObject value = (JSONObject) obj.get(key);
            log.info("value :" + value);
            Set<String> userSet = value.keySet();
            log.info("userSet :" + userSet);

            for (String userKey : userSet) {
                //log.info( "userKey :"+userKey);
                JSONObject userValue = (JSONObject) value.get(userKey);
                //log.info( "userValue :"+userValue);

                Set<String> groupKeys = userValue.keySet();
                groupObj = mapper.readValue(userValue.toJSONString(), Group.class);

                log.info("Group Object - "+ groupObj.toString());

                log.info("isValidSurpriseGroup - "+ groupObj.isValidSurpriseGroup());

                log.info("isValidMemoriesGroup - "+ groupObj.isValidMemoriesGroup());

                for (String groupKey : groupKeys) {
                    //log.info( "groupKey :"+groupKey);

                    if (groupKey.equalsIgnoreCase("type") && userValue.get(groupKey).toString().equalsIgnoreCase("Memories")) {
                        log.info("entered content:" + groupKey);
                        memoriesList.add(userValue);
                        memoriesresponse = filterMemories(memoriesList, key, userKey, memoriesDate);

                    } else if (groupKey.equalsIgnoreCase("type") && userValue.get(groupKey).toString().equalsIgnoreCase("Surprise")) {
                        supriseList.add(userValue);
                        surpriseresponse = fileterSuprises(supriseList, key, userKey, surpriseDate);

                    } else if (groupKey.equalsIgnoreCase("type") && userValue.get(groupKey).toString().equalsIgnoreCase("Capsule")) {
                        capsuleList.add(userValue);
                        finalCapsuleList = getOwnerList(capsuleList);
                    }
                }
            }

        }
        //	log.info("currentDate :"+sysdate);
        log.info("memoriesList :" + memoriesList.size());
        log.info("supriseList :" + supriseList.size());
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

    private String fileterSuprises(List<JSONObject> surpriseList, String key, String userKey, String surpriseDate) throws ParseException {
        List<JSONObject> surpriseTypeList = new ArrayList<JSONObject>();
        List<JSONObject> finalList = new ArrayList<JSONObject>();
        String output = "No Owner for particular Date";
        int surpriseTypeListSize = surpriseTypeList.size();
        surpriseTypeList = surpriseFilterByDate(surpriseList, surpriseDate);
        if (surpriseTypeList.size() > surpriseTypeListSize) {
            finalList = getOwnerList(surpriseTypeList);
            output = service.generateSurpriseVideoFromFinalList(key, userKey, finalList);
            
        }

        return output;
    }

    private String filterMemories(List<JSONObject> memoriesList, String key, String userKey, String memoriesDate) throws ParseException {
        List<JSONObject> memoriesTypeList = new ArrayList<JSONObject>();
        List<JSONObject> finalList = new ArrayList<JSONObject>();
        String output = "No Owner for particular date";
        int memoriesTypeListSize = memoriesTypeList.size();
        memoriesTypeList = memoriesFilterByDate(memoriesList, memoriesDate);
        if (memoriesTypeList.size() > memoriesTypeListSize) {
            System.out.println("memoriesTypeList.size()" + memoriesTypeList.size());
            finalList = getOwnerList(memoriesTypeList);
            int finalListSize = finalList.size();
            if (finalList.size() > finalListSize) {
                System.out.println("memories finalList" + finalList.size());
                finalList = memoriesFilterByDate(memoriesTypeList, memoriesDate);
                output = service.generateMemoriesVideoFromFinalList(key, userKey, finalList);
            }
        }
        return output;

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

    private List<JSONObject> memoriesFilterByDate(List<JSONObject> list, String memoriesDate) {
        List<JSONObject> typeList = new ArrayList<JSONObject>();

        for (JSONObject capsules : list) {
            Set<String> groupKeys = capsules.keySet();
            for (String groupKey : groupKeys) {
                //log.info( "groupKey :"+groupKey);
                if (groupKey.equalsIgnoreCase("todate") && capsules.get(groupKey).toString().substring(0, 10).equalsIgnoreCase(memoriesDate)) {
                    log.info("entered content:" + groupKey);
                    typeList.add(capsules);
                }
            }
        }
        return typeList;
    }

    private List<JSONObject> surpriseFilterByDate(List<JSONObject> list, String surpriseDate) {
        List<JSONObject> typeList = new ArrayList<JSONObject>();
        for (JSONObject capsules : list) {
            Set<String> groupKeys = capsules.keySet();
            for (String groupKey : groupKeys) {
                //log.info( "groupKey :"+groupKey);

                if (groupKey.equalsIgnoreCase("todate") && capsules.get(groupKey).toString().substring(0, 10).equalsIgnoreCase(surpriseDate)) {
                    log.info("entered content:" + groupKey + surpriseDate);
                    typeList.add(capsules);
                }
            }
        }
        return typeList;
    }
}
