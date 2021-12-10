// Source: https://www.w3schools.com/java/java_arraylist.asp

package com.OnPoint;

import com.OnPoint.DatabaseRelation.Activity;
import com.OnPoint.DatabaseRelation.Profile;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Account {
    private final Profile profile = new Profile();
    public ArrayList<Activity> activityList = new ArrayList<>();
    public ArrayList<People> friends = new ArrayList<>();

    //    Profile Management
    public Profile getProfile(){
        return this.profile;
    }

    public void showProfile() {
        System.out.println(getProfile().getUsername());
        System.out.println(getProfile().getEmail());
        System.out.println(getProfile().getPassword());
        System.out.println(getProfile().getRating());
        System.out.println(getProfile().rate.getTotalMeet());

    }

    //Activity CRUD
    public void reloadActivity(Connection connect){
        try {
            String sql = "SELECT activity_name,start_time FROM activities WHERE isuser = ?";
            PreparedStatement st = connect.prepareStatement(sql);
            st.setString(1, profile.getUsername());
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                String activity = rs.getString("activity_name");
                Timestamp time  = rs.getTimestamp("start_time");
                activityList.add(new Activity(activity,time));
            }
        } catch (SQLException except) {
            System.out.println("Connection Failed on reloadActivity");
            except.printStackTrace();
        }
    }
    public void showActivity(){
        if(activityList.size() <= 0) {
            System.out.println("You are free from any schedule :)");
        }else{
            System.out.println("--Appointment--");
            for(int i = 0; i < activityList.size(); i++) {
                System.out.println(activityList.get(i).getName() + " " + activityList.get(i).getTime());
            }
        }
    }
    public ArrayList<Activity> addActivity(String activityName, String activityTime){
        String timeInput = activityTime;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime timeIn = LocalDateTime.parse(timeInput, formatter);
        Timestamp timestamp = Timestamp.valueOf(timeIn);

        activityList.add(new Activity(activityName, timestamp));
        return activityList;
    }
    public ArrayList<Activity> removeActivity(int index){
        activityList.remove(index-1);
        return activityList;

    }
    public ArrayList<Activity> editActivity(int index, String activityName, String activityTime){
        if (activityList.size() <= 0){
            System.out.println("You are free from any schedule :)");
        }else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            Timestamp timeIn = Timestamp.valueOf(LocalDateTime.parse(activityTime, formatter));
            activityList.set(index-1, new Activity(activityName, timeIn));

            return activityList;

        }
        return activityList;

    }
    public void uploadActivity(Connection connect, String issuer){
        try {
            String sqlDel = "DELETE FROM activities WHERE isuser = ?";
            PreparedStatement stDel = connect.prepareStatement(sqlDel);
            stDel.setString(1, issuer);
            int rowDel = stDel.executeUpdate();

            for (int i = 0 ; i < activityList.size() ; i++) {
                String sqlIn = "INSERT INTO activities (activity_name,  start_time, isuser) VALUES (?,?,?)";
                PreparedStatement stIn = connect.prepareStatement(sqlIn);
                stIn.setString(1, activityList.get(i).getName());
                stIn.setTimestamp(2, activityList.get(i).getTime());
                stIn.setString(3, issuer);

                int rowIn = stIn.executeUpdate();
                if (rowIn > 0) {
                    System.out.println("Database Successfully Uploaded");
                }
            }

            System.out.println(activityList.size());
            if (rowDel > 0) {
                System.out.println("Database Successfully Uploaded");
            }
        } catch (SQLException except) {
            System.out.println("Connection Failed on uploadActivity");
            except.printStackTrace();
        }

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
        try {
            String sql = "SELECT profile.username, profile.email, profile.password, profile.rating FROM friends INNER JOIN profile ON friends.username = ? AND friends.friend = profile.username";
            PreparedStatement st = connect.prepareStatement(sql);
            st.setString(1, profile.getUsername());
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

    public String findFriends(Connection connect, String fr) {
        String nameF = null;
        String emailF= null;
        String passF= null;
        double rateF = 0.0;
        boolean friend_found = false;
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
                friend_found = true;
                friends.add(new People(nameF, emailF, passF, rateF));
            }
            if(friend_found){
                System.out.println("Your friend? " + nameF);
            }else{
                nameF = "Can't find your friend";
            }
        } catch (SQLException except) {
            System.out.println("Connection Failed");
            except.printStackTrace();
        }
        System.out.println(friends);
//        String chosenFriends = friends.get(friends.size()-1).getUsername();
//        for(People pp: friends){
//            if (chosenFriends.equals(pp.getUsername())){
//                friends.remove(friends.size()-1);
//                nameF = "you already add this friend";
//            }
//        }
        return nameF;
    }
    public void addFriend(Connection connect){

        try {
            String sql = "INSERT INTO friends VALUES(?, ?)";
            PreparedStatement st = connect.prepareStatement(sql);
            st.setString(1, profile.getUsername());
            st.setString(2, friends.get((friends.size()-1)).getUsername());

            String sql2 = "INSERT INTO friends VALUES(?, ?)";
            PreparedStatement st2 = connect.prepareStatement(sql2);
            st2.setString(1, friends.get((friends.size()-1)).getUsername());
            st2.setString(2, profile.getUsername());

            int rows = st.executeUpdate();
            int rows2 = st2.executeUpdate();

            if (rows > 0 && rows2 > 0) {
                System.out.println("you add "+ friends.get((friends.size()-1)).getUsername() + " as friend");

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void deleteFriend(int friendIndex){
        System.out.println("you just blocked "+ friends.get(friendIndex).getUsername());
        friends.remove(friendIndex);
    }
    public void inviteFriends(Connection connect, int indexAct, int nameFriend) throws SQLException {
        String friend = friends.get(nameFriend).getUsername();

        String sql = "INSERT INTO activities VALUES(?, ?, ?)";
        PreparedStatement st = connect.prepareStatement(sql);
        st.setString(1, activityList.get(indexAct).getName());
        st.setTimestamp(2, activityList.get(indexAct).getTime());
        st.setString(3, friend);

        int rows = st.executeUpdate();
        if (rows > 0){
            System.out.println("Invite Successfully");
        }

    }
    public void uploadFriend(Connection connect, String issuer){
        try {
            String sqlDelU = "DELETE FROM friends WHERE username = ?";
            PreparedStatement stDelU = connect.prepareStatement(sqlDelU);
            stDelU.setString(1, issuer);
            int rowDelU = stDelU.executeUpdate();

            String sqlDelF = "DELETE FROM friends WHERE friend = ?";
            PreparedStatement stDelF = connect.prepareStatement(sqlDelF);
            stDelF.setString(1, issuer);
            int rowDelF = stDelF.executeUpdate();

            for (int i = 0 ; i < friends.size() ; i++) {
                addFriend(connect);
            }
            if (rowDelU > 0 && rowDelF > 0) {
                System.out.println("Database Successfully Uploaded");
            }
        } catch (SQLException except) {
            System.out.println("Connection Failed on uploadFriend");
            except.printStackTrace();
        }

    }
    public void confFriends(Connection connect, String addornot) {
        switch (addornot){
            case "y":
                System.out.println("add friend");
                addFriend(connect);
                break;
            case "n":
                System.out.println("erased friend");
                friends.remove((friends.size()-1));
                break;
        }
    }
}