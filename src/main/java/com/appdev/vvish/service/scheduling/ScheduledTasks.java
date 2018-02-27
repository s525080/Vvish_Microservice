package com.appdev.vvish.service.scheduling;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.appdev.vvish.dao.jersey.DBConnector;

@Component
public class ScheduledTasks {
	
	private static final Logger LOG = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    DBConnector connector;

    //@Scheduled(fixedRate = 90000)
    //Runs Every 6 hrs
    @Scheduled(cron = "0 0/1 * * * ?")
    public void reportCurrentTime() throws Exception {
        LOG.debug("The time is now {}"+ dateFormat.format(new Date()));
        connector.fetchAndProcessGroups();
    }
    
}
