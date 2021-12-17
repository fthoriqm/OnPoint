package com.OnPoint.Controller;

import com.OnPoint.Account;
import com.OnPoint.DatabaseRelation.Activity;
import com.OnPoint.DatabaseRelation.Appsql;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/activity")
public class ActivityController {
    Account account = null;
    Appsql appsql = new Appsql();
    Connection conndb = appsql.connect();

    @GetMapping("{username}/reloadActivity")
    List<Activity> reloadActivity(@PathVariable String username){
        account = new Account();
        account.getProfile().setLogin(username);
        account.reloadActivity(conndb);
        return account.activityList;
    }

    @PostMapping("/addActivity")
    Activity addActivity(
            @RequestParam String activityName,
            @RequestParam String desc,
            @RequestParam String activityTime) {
        account.addActivity(activityName, desc, activityTime);
        account.uploadActivity(conndb, account.getProfile().getUsername());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime timeIn = LocalDateTime.parse(activityTime, formatter);
        Timestamp timestamp = Timestamp.valueOf(timeIn);
        return new Activity (activityName, desc, timestamp);
    }
    @GetMapping("{username}/showActivity")
    ArrayList<Activity> showActivity (@PathVariable String username){
        account.getProfile().setLogin(username);
        account.showActivity();
        return account.activityList;
    }
    @DeleteMapping("/deleteActivity")
    void deleteActivity (@RequestParam int index) {
        account.removeActivity(index);
        account.uploadActivity(conndb, account.getProfile().getUsername());
    }
    @PostMapping ("/editActivity")
    Activity editActivity(
            @RequestParam int index,
            @RequestParam String activityName,
            @RequestParam String desc,
            @RequestParam String activityTime){
        account.uploadActivity(conndb, account.getProfile().getUsername());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime timeIn = LocalDateTime.parse(activityTime, formatter);
        Timestamp timestamp = Timestamp.valueOf(timeIn);
        account.editActivity(index, activityName, desc, activityTime);
        return new Activity(activityName, desc, timestamp);
    }

    @PostMapping("{username}/uploadActivity")
    boolean uploadActivity(@PathVariable String username){
        account = new Account();
        account.getProfile().setLogin(username);
        return account.uploadActivity(conndb, username);
    }
}