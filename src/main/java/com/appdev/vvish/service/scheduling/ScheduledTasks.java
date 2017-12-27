package com.appdev.vvish.service.scheduling;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.appdev.vvish.dao.jersey.DBConnector;

@Component
public class ScheduledTasks {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    DBConnector connector;

    //@Scheduled(fixedRate = 90000)
    @Scheduled(cron = "* 0/6 * * *")
    public void reportCurrentTime() throws Exception {
        System.out.println("The time is now {}"+ dateFormat.format(new Date()));
        connector.fetchGroups();
    }
    
}
