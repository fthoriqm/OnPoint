package com.OnPoint.DatabaseRelation;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Activity {
    String name;
    String desc;
    Timestamp time;

    //read/write database
    public Activity (String name, String desc, Timestamp time){
        this.desc = desc;
        this.name = name;
        this.time = time;
    }

    public String getName() {
        return this.name;
    }
    public Timestamp getTime() {
        return this.time;
    }
}