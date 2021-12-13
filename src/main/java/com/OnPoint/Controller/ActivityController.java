package com.OnPoint.Controller;

import com.OnPoint.DatabaseRelation.Activity;
import com.OnPoint.DatabaseRelation.Appsql;
import org.springframework.web.bind.annotation.*;
import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import static com.OnPoint.Account.account;

@RestController
@RequestMapping("/activity")

public class ActivityController {
    Appsql appsql = new Appsql();
    Connection conndb = appsql.connect();

    public ActivityController (){
        account.reloadActivity(conndb);
    }

    @PostMapping("/addActivity")
    Activity addActivity(@RequestParam String activityName, @RequestParam String activityTime) {
        account.addActivity(activityName, activityTime);
        account.uploadActivity(conndb, account.getProfile().getUsername());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime timeIn = LocalDateTime.parse(activityTime, formatter);
        Timestamp timestamp = Timestamp.valueOf(timeIn);
        return new Activity (activityName, timestamp);
    }
    @GetMapping("/showActivity")
    ArrayList<Activity> showActivity (){
        account.showActivity();
        return account.activityList;
    }
    @DeleteMapping("/deleteActivity")
    void deleteActivity (@RequestParam int index) {
        account.removeActivity(index);
        account.uploadActivity(conndb, account.getProfile().getUsername());
    }
    @PostMapping ("/editActivity")
    Activity editActivity(@RequestParam int index, @RequestParam String activityName, @RequestParam String activityTime){
        account.uploadActivity(conndb, account.getProfile().getUsername());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime timeIn = LocalDateTime.parse(activityTime, formatter);
        Timestamp timestamp = Timestamp.valueOf(timeIn);
        account.editActivity(index, activityName, activityTime);
        return new Activity(activityName, timestamp);
    }
}