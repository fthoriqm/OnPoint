package com.OnPoint.Controller;

import com.OnPoint.Account;
import com.OnPoint.DatabaseRelation.Appsql;
import com.OnPoint.DatabaseRelation.Profile;
import com.OnPoint.People;
import com.OnPoint.dbJson.JsonAutowired;
import com.OnPoint.dbJson.JsonTable;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;
import java.sql.Connection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/account")
public class AccountController {
    Gson gson = new Gson();
    public static final String REGEX_EMAIL = "^[a-zA-Z0-9&_*~]+(?:\\.[a-zA-Z0-9&_*~]+)*@[A-Za-z0-9][a-zA-Z0-9]+(?:\\.[a-zA-Z0-9-]+)*$";
    public static final String REGEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";
    public static final Pattern REGEX_PATTERN_EMAIL = Pattern.compile(REGEX_EMAIL);
    public static final Pattern REGEX_PATTERN_PASSWORD = Pattern.compile(REGEX_PASSWORD);
    Account currentAccount = null;
    Account account;

    Appsql appsql = new Appsql();
    Connection conndb = appsql.connect();

    @PostMapping("/login")
    Account login
            (
            @RequestParam String username,
            @RequestParam String password
            )
    {
        account = new Account(username, username, password);
        if (authPass(password, false) && (authUsername(username, false) || authEmail(username, false))) {
            account.getProfile().setLogin(username);
            account.reloadFriends(conndb);
            account.reloadActivity(conndb);

            return account;
        }
        return null;
    }

    @PostMapping("/register")
    Account register
            (
                    @RequestParam String name,
                    @RequestParam String email,
                    @RequestParam String password
            )
    {
        currentAccount = new Account(name, email, password);
        if (authPass(password, true) && (authUsername(name, true) && authEmail(email, true))) {
            currentAccount.getProfile().setRegister(name, email, password);
            return currentAccount;
            }
        return null;
    }

//    @GetMapping("/reloadFriends")
//    void reloadFriends(){
//        account.reloadFriends(conndb);
//    }
//
//    @PostMapping("/findFriends")
//    String findFriend
//            (
//                    @RequestParam String friendName
//            )
//    {
//        People friend = account.findFriends(conndb, friendName);
//        System.out.println(friend);
//        if (friend != null){
//            account.uploadFriend(conndb);
//            return friend.getUsername();
//        }
//        return "null";
//    }
//
//    @PostMapping("/addFriends")
//    People addFriends
//            (
//                    @RequestParam String friend,
//                    @RequestParam boolean confirmation
//            )
//    {
//        if(confirmation) {
//            People add = account.addFriend(account.findFriends(conndb, friend));
//            if (add != null) {
//                account.uploadFriend(conndb);
//                return add;
//            }
//        }
//        return null;
//    }
//
//    @PostMapping("/showFriends")
//    ArrayList<People> showFriends(){
//        return account.friends;
//    }
//
//    @PostMapping("/deleteFriends")
//    void deleteFriend
//            (
//                    @RequestParam int index
//            )
//    {
//        account.deleteFriend(index);
//        account.uploadFriend(conndb);
//    }
//
//    @PostMapping("/inviteMeet")
//    void inviteMeet
//            (
//                    @RequestParam int indexAct,
//                    @RequestParam int nameFriend
//            )
//    {
//        account.inviteFriends(conndb, indexAct, nameFriend);
//        account.uploadFriend(conndb);
//    }
//
//    @PostMapping("/reloadFriends")
//    void uploadFriends(){
//        account.uploadFriend(conndb);
//    }
//
    @GetMapping("/Profile")
    public Profile showProfile() {
        return currentAccount.getProfile();
    }
//
//    //Account Creation and Authentication
//
    //login & register verification
    public boolean authEmail(String email, boolean registerMode){
        //setelah dibandingkan dapat dimasukkan datanya ke dalam database
        //pakai set...()

        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (registerMode && !currentAccount.getProfile().checkEmail(email) && matcher.matches()){
            currentAccount.getProfile().checkEmail(email);
            return true;
        } else if (!registerMode && matcher.matches() && currentAccount.getProfile().checkEmail(email)){
            currentAccount.getProfile().checkEmail(email);
            return true;
        } else {
            return false;
        }

        //2 pembanding dari regex dan database yang diambil dari Profile
        //belum
    }
    public boolean authPass(String pass, boolean registerMode){
        //setelah dibandingkan dapat dimasukkan datanya ke dalam database
        //pakai set...()
        String regex =  "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–{}:;',?/*~$^+=<>]).{8,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pass);
        if (registerMode && matcher.matches()){
            account.getProfile().setPassword(pass);
            return true;
        }
        else if (!registerMode && matcher.matches() && account.getProfile().checkPassword(pass)){
            account.getProfile().setPassword(pass);
            return true;
        } else {
            return false;
        }
        //2 pembanding dari regex dan database yang diambil dari Profile
        //belum
    }
    public boolean authUsername(String username, boolean registerMode){
        if(registerMode && !account.getProfile().checkUsername(username)){
            account.getProfile().setUsername(username);
            return true;
        } else if ( !registerMode && account.getProfile().checkUsername(username)){
            account.getProfile().setUsername(username);
            return true;
        }else {
            return false;
        }
    }
}
