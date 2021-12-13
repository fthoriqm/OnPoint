package com.OnPoint.DatabaseRelation;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Activity {
    String name;
    Timestamp time;

    //read/write database
    public Activity (String name, Timestamp time){
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