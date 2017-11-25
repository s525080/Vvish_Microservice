package com.appdev.vvish.service.scheduling;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.appdev.vvish.dao.jersey.DBConnector;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    DBConnector connector;

    @Scheduled(fixedRate = 90000)
    public void reportCurrentTime() throws IOException, ParseException {
        System.out.println("The time is now {}"+ dateFormat.format(new Date()));
        connector.fetchGroups();
    }
    
}
