package com.OnPoint.DatabaseRelation;

import com.OnPoint.Rating;

import javax.sound.midi.SysexMessage;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Profile{
    private String username;
    private String email;
    private String password;
    private double rating;
    public Rating rate = new Rating();

    ArrayList<Boolean> confriend = new ArrayList<>();

    Appsql appsql = new Appsql();
    Connection conndb = appsql.connect();

//    public Profile(String username, String email, String password, double rating){
//        this.username = username;
//        this.email = email;
//        this.password = password;
//        this.rating = rating;
//    }

    public boolean checkEmail(String email){
        boolean email_exist = false;
        try {
            String sql = "SELECT email from Profile WHERE email = ?";
            PreparedStatement st = conndb.prepareStatement(sql);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                email_exist = true;
            }
        } catch (SQLException except) {
            System.out.println("Connection Failed");
            except.printStackTrace();
        }
        return email_exist;
    }
    public boolean checkUsername(String username){
        boolean username_exist = false;
        try {
            String sql = "SELECT username from Profile WHERE username = ?";
            PreparedStatement st = conndb.prepareStatement(sql);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                username_exist = true;
            }
        } catch (SQLException except) {
            System.out.println("Connection Failed");
            except.printStackTrace();
        }
        return username_exist;
    }
    public boolean checkPassword(String password){
        boolean password_exist = false;
        try {
            String sql = "SELECT password from Profile WHERE password = md5(?)";
            PreparedStatement st = conndb.prepareStatement(sql);
            st.setString(1, password);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                password_exist = true;
            }
        } catch (SQLException except) {
            System.out.println("Connection Failed");
            except.printStackTrace();
        }
        return password_exist;
    }

    public void setLogin(String username){
        try {
            String sql = "SELECT * from Profile WHERE username = ?";
            PreparedStatement statement = conndb.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                setEmail((rs.getString("email")));
                setUsername((rs.getString("username")));
                setPassword((rs.getString("password")));
                setRating((rs.getDouble("rating")));
            }
        } catch (SQLException except) {
            System.out.println("Connection Failed");
            except.printStackTrace();
        }
    }

    public void setRegister(String username, String email, String password) {
        try {
            String sql = "INSERT INTO profile VALUES (nextval('profile_no_seq'::regclass) ,?, ?, md5(?), ?)";
            PreparedStatement statement = conndb.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.setDouble(4, 5.0);

            int rows = statement.executeUpdate();
            if (rows > 0) {
                System.out.println("Profile Added");
            }
        } catch (SQLException except) {
            System.out.println("Connection Failed");
            except.printStackTrace();
        }
    }

    //database


    public ArrayList<Boolean> getConfirm(){
        //friends confirmation boolean from database
        //save the boolean to variable
        //pengecekan database di profile
        //select confirmation from profile where = "--username--"
        //--username-- adalah user yang ingin merubah activity
        //username dari getName()
        //hasilnya diletakkan di String/list
        return confriend;
    }
    public void removeConfirm(){
        //pakai insert into pada database untuk confirm saja
        //seluruhnya jadi false
        //mencari berdasarkan username yang ingin merubah activity
        //username dari getName()
    }
    public void setRating(double rating) {
        this.rating = rating;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public double getRating() {
        return rate.getRate();
    }
    public String getUsername() {
        return this.username;
    }
    public String getEmail() {
        return this.email;
    }
    public String getPassword() {
        return this.password;
    }
}