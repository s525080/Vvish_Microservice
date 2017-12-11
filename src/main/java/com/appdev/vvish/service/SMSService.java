package com.appdev.vvish.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.appdev.vvish.constants.VVishConstants;
import com.appdev.vvish.model.MessageRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telesign.MessagingClient;
import com.telesign.RestClient;

@Service
public class SMSService {
	
	private static final Logger LOG = LoggerFactory.getLogger(SMSService.class);
	
	@Value("#{'${vvish.url}'}")
	private String vURL;
	
	@Value("#{'${sms.customer_id}'}")
	private String smsCustId;
	
	@Value("#{'${sms.api_key}'}")
	private String smsApiKey;
	
	@Value("#{'${sms.toName.vish}'}")
	private String toNameVish;
	
	@Value("#{'${sms.invite_msg}'}")
	private String inviteMsg;
	
	@Value("#{'${sms.final_video_msg}'}")
	private String finalVidMsg;

	public ResponseEntity<String> sendMessage(String msgDetails) {
		
		LOG.info("SendMessage - Details Received :: " + msgDetails);
		String response = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			MessageRequest msgReq = mapper.readValue(msgDetails.toString(), MessageRequest.class);
			
			if(VVishConstants.VISH.equalsIgnoreCase(msgReq.getMsgType())) {
				response = "Invalid Message Request.";
			} else {
				response = this.prepareAndSendMessage(msgReq);
			}

		} catch (Exception e) {
			LOG.error("Error while sending SMS..", e);
			response = "Message could not be delivered";
		}
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	public String prepareAndSendMessage(MessageRequest msgReq) throws Exception {
		
		String msg = null;
		if(VVishConstants.INVITE.equalsIgnoreCase(msgReq.getMsgType())) {
			msg = replaceNamesAndURL(msgReq.getToName(), msgReq.getFromPhNo(), msgReq.getFromName(), inviteMsg);
		} else if (VVishConstants.VISH.equalsIgnoreCase(msgReq.getMsgType())) {
			msg = replaceNamesAndURL(msgReq.getToName(), msgReq.getFromPhNo(), msgReq.getFromName(), finalVidMsg)
					.replace("$occassion", msgReq.getOccassion()).replace("$vVid", msgReq.getFinalVid());
		}
		
		return this.sendSMS(msgReq.getToPhNo(), msg);
	}
	
	private String replaceNamesAndURL(String toName, String fromPhNo, String fromName, String msg) {
		
		LOG.info("From : " + fromPhNo +" - "+ fromName + " :: To : "+ toName + " :: Message : "+ msg);
		
		String pMsg = msg.replace("$URL", vURL);
		
		//Add Name of the receiver if toName is not NULL
		if(toName != null) {
			String tVish = toNameVish.replace("$toName", toName);
			LOG.info("To Salutation :: "+ tVish);
			pMsg = tVish.concat(pMsg);
			LOG.info("Message - "+ pMsg);
		}
		
		//Replace Sender with Name if available or Phone Number
		if(fromName != null) {
			pMsg = pMsg.replace("$fromName", fromName);
			LOG.info("From Name is not NULL - "+ pMsg);
		} else if(fromPhNo != null) {
			pMsg.replaceAll("$fromName", fromPhNo);
			LOG.info("From Ph No is not NULL - "+ pMsg);
		}
		
		LOG.info("Final Message before Value replacement :: "+ pMsg);
		return pMsg;
	}
	
	public String sendSMS(String phoneNumber, String message) throws Exception {
		
		LOG.info("Sending \""+ message + "\" to "+ phoneNumber +" through SMS..");
		
		MessagingClient messagingClient = new MessagingClient(smsCustId, smsApiKey);
        RestClient.TelesignResponse msgResp = messagingClient.message(phoneNumber, message, "ARN", null);
        
        LOG.info("Returned Status Code :: "+ msgResp.statusCode);
        LOG.info("Body :: "+ msgResp.body);
        
        String response;
        if(msgResp.statusCode == 200) {
			response = "Message Delivered";
		} else {
			response = "Message Could not be delivered.";
		}
        
        return response;
	}
}
