// Source: https://www.w3schools.com/java/java_arraylist.asp

package com.OnPoint;

import com.OnPoint.DatabaseRelation.Activity;
import com.OnPoint.DatabaseRelation.Profile;
import com.OnPoint.dbJson.JsonAutowired;
import com.OnPoint.dbJson.JsonTable;
import com.google.gson.Gson;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class Account {
    private final Profile profile = new Profile();
    public ArrayList<Activity> activityList = new ArrayList<>();
    public ArrayList<People> friends = new ArrayList<>();

    public Account(){}
    public Account(String username, String email, String password){
        this.profile.setUsername(username);
        this.profile.setEmail(email);
        this.profile.setPassword(password);
        this.profile.setRating(5.0);
    }
    public Account(String username, String email, String password, double rating){
        this.profile.setUsername(username);
        this.profile.setEmail(email);
        this.profile.setPassword(password);
        this.profile.setRating(rating);
    }

//    Profile Management
    public Profile getProfile(){
        return this.profile;
    }

    //Activity CRUD
    public void reloadActivity(Connection connect){
        try {
            String sql = "SELECT activity_name, description, start_time FROM activities WHERE isuser = ?";
            PreparedStatement st = connect.prepareStatement(sql);
            st.setString(1, profile.getUsername());
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                String activity = rs.getString("activity_name");
                Timestamp time  = rs.getTimestamp("start_time");
                String desc = rs.getString("description");
                activityList.add(new Activity(activity,desc, time));
            }
        } catch (SQLException except) {
            System.out.println("Connection Failed on reloadActivity");
            except.printStackTrace();
        }
    }
    public void showActivity(){
        if(activityList.size() <= 0) {
            System.out.println("You are free from any schedule :)");
        } else{
            System.out.println("--Appointment--");
            for(int i = 0; i < activityList.size(); i++) {
                System.out.println(activityList.get(i).getName() + " " + activityList.get(i).getTime());
            }
        }
    }
    public ArrayList<Activity> addActivity(String activityName, String desc, String activityTime){
        String timeInput = activityTime;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime timeIn = LocalDateTime.parse(timeInput, formatter);
        Timestamp timestamp = Timestamp.valueOf(timeIn);

        activityList.add(new Activity(activityName,desc, timestamp));
        return activityList;
    }
    public ArrayList<Activity> removeActivity(int index){
        activityList.remove(index-1);
        return activityList;

    }
    public ArrayList<Activity> editActivity(int index, String activityName, String desc, String activityTime){
        if (activityList.size() <= 0){
            System.out.println("You are free from any schedule :)");
        }else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            Timestamp timeIn = Timestamp.valueOf(LocalDateTime.parse(activityTime, formatter));
            activityList.set(index-1, new Activity(activityName, desc, timeIn));

            return activityList;

        }
        return activityList;
    }
    public boolean uploadActivity(Connection connect, String issuer){
        try {
            String sqlDel = "DELETE FROM activities WHERE isuser = ?";
            PreparedStatement stDel = connect.prepareStatement(sqlDel);
            stDel.setString(1, issuer);
            int rowDel = stDel.executeUpdate();
            if (rowDel>0){
                for (int i = 0 ; i < activityList.size() ; i++) {
                    String sqlIn = "INSERT INTO activities (activity_name,  start_time, isuser) VALUES (?,?,?)";
                    PreparedStatement stIn = connect.prepareStatement(sqlIn);
                    stIn.setString(1, activityList.get(i).getName());
                    stIn.setTimestamp(2, activityList.get(i).getTime());
                    stIn.setString(3, issuer);

                    int rowIn = stIn.executeUpdate();
                    if (rowIn > 0) {
                        return true;
                    }
                }
            }
        } catch (SQLException except) {
            System.out.println("Connection Failed on uploadActivity");
            except.printStackTrace();
        }
        return false;
    }
    public Activity getActivity(int index) {
        return activityList.get(index);
    }

    //Friends CRUD
//    public boolean approvalFriend(){
//        profile.Friends();
//        return true;
//    }
    public void reloadFriends(Connection connect) {
        System.out.println(getProfile().getUsername());
        try {
            String sql = "SELECT profile.username, profile.email, profile.password, profile.rating FROM friends INNER JOIN profile ON friends.username = ? AND friends.friend = profile.username";
            PreparedStatement st = connect.prepareStatement(sql);
            st.setString(1, getProfile().getUsername());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String nameF = rs.getString("username");
                String emailF = rs.getString("email");
                String passF = rs.getString("password");
                double rateF = rs.getDouble("rating");
                friends.add(new People(nameF, emailF, passF, rateF));
            }

        } catch (SQLException except) {
            System.out.println("Connection Failed");
            except.printStackTrace();
        }
    }
    public void showFriends(){
        for(People friend : friends){
            System.out.println(friend.getUsername());
        }
    }
    public People findFriends(Connection connect, String fr) {
        String nameF = null;
        String emailF= null;
        String passF= null;
        double rateF = 0.0;
        try {
            String sql = "SELECT * from profile where username = ?";
            PreparedStatement st = connect.prepareStatement(sql);
            st.setString(1, fr);
            ResultSet rs = st.executeQuery();
            if(rs.next()) {
                nameF = rs.getString("username");
                emailF = rs.getString("email");
                passF = rs.getString("password");
                rateF = rs.getDouble("rating");
            }
        } catch (SQLException except) {
            System.out.println("Connection Failed");
            except.printStackTrace();
        }
        System.out.println("name "+ nameF);
        if (nameF != null) {
            return new People(nameF, emailF, passF, rateF);
        }
        return null;
    }
    public People addFriend(People friend){
        boolean booldup = false;
        if (friend != null) {
            for (People fr : friends) {
                if (fr.getUsername().equals(friend.getUsername())) {
                        booldup = true;
                        System.out.println("you already add this friend");
                        break;
                }
            }
            if (booldup) {
                return null;
            } else {
                friends.add(friend);
                System.out.println(friends.size());
                return friend;
            }
        }
        return null;
    }
    public void deleteFriend(int friendIndex){
        if (friends.size() > 0) {
            System.out.println("you just blocked "+ friends.get(friendIndex).getUsername());
            friends.remove(friendIndex);
        }
    }
    public void inviteFriends(Connection connect, int indexAct, int nameFriend) {
        String friend = friends.get(nameFriend).getUsername();
        try {
            String sql = "INSERT INTO activities VALUES(?, ?, ?)";
            PreparedStatement st = connect.prepareStatement(sql);
            st.setString(1, activityList.get(indexAct).getName());
            st.setTimestamp(2, activityList.get(indexAct).getTime());
            st.setString(3, friend);

            int rows = st.executeUpdate();
            if (rows > 0) {
                System.out.println("Invite Successfully");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void uploadFriend(Connection connect){
        try {
            String sqlDelU = "DELETE FROM friends WHERE username = ?";
            PreparedStatement stDelU = connect.prepareStatement(sqlDelU);
            stDelU.setString(1, getProfile().getUsername());
            int rowDelU = stDelU.executeUpdate();

            String sqlDelF = "DELETE FROM friends WHERE friend = ?";
            PreparedStatement stDelF = connect.prepareStatement(sqlDelF);
            stDelF.setString(1, getProfile().getUsername());
            int rowDelF = stDelF.executeUpdate();

            for (int i = 0 ; i < friends.size() ; i++) {
                try {
                    String sql = "INSERT INTO friends VALUES(?, ?)";
                    PreparedStatement st = connect.prepareStatement(sql);
                    st.setString(1, getProfile().getUsername());
                    st.setString(2, friends.get(i).getUsername());

                    String sql2 = "INSERT INTO friends VALUES(?, ?)";
                    PreparedStatement st2 = connect.prepareStatement(sql2);
                    st2.setString(1, friends.get(i).getUsername());
                    st2.setString(2, getProfile().getUsername());

                    int rows = st.executeUpdate();
                    int rows2 = st2.executeUpdate();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if (rowDelU > 0 && rowDelF > 0) {
                System.out.println("Database Successfully Uploaded");
            }
        } catch (SQLException except) {
            System.out.println("Connection Failed on uploadFriend");
            except.printStackTrace();
        }

    }

    //pengecekan konfirmasi dari friend
    // public boolean approvalFriend(){
    //     String cancel = scan.nextLine();

    //     int confirm = 0;         //jumlah konfirmasi
    //     int i = 0;               //indeks
    //     boolean check = false;    //hasil boolean
    //     ArrayList<Boolean> friends = new ArrayList<Boolean>(profile.getConfirm());//membuat arraylist untuk boolean confirm dari database
    //     // looping sampai kondisi yang ditentukan
    //     while(i >= 0){
    //         if(i >= friends.size()){    //apabila sama sizenya dibikin jadi 0 lagi
    //             i = 0;
    //         }if (friends.get(i)) {      //apabila true pada arraylist boolean
    //             confirm += 1;
    //         }if (confirm >= (friends.size() * 0.7)){    //minimal jumlah confirm adalah anggota * 70%
    //             System.out.println("you can change activity now");
    //         }if(confirm == friends.size()){             //apabila semua anggota sudah konfirmasi maka keluar
    //             friends = null;
    //             confirm = 0;
    //             check = true;
    //             break;
    //         }if(cancel == "c" || cancel == "C"){        //tidak jadi merubah
    //             friends = null;
    //             confirm = 0;
    //             break;
    //         }

    //         i++;
    //     }
    //     return true;
    // }

//     public String getCurrentTime(){
//         LocalDateTime in = LocalDateTime.now();
//         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//         System.out.println(in.format(formatter));

// //         LocalTime obj = LocalTime.now();
// //         DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
// //         String result = obj.format(format);
// //         System.out.println(result);

// //         LocalDate date = LocalDate.now();
// //         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
// //         String text = date.format(formatter);
// //         LocalDate parsedDate = LocalDate.parse(text, formatter);
// //         System.out.println(parsedDate);

//         return result;
//     }
}