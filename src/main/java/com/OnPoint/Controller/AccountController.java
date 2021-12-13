package com.OnPoint.Controller;

import com.OnPoint.Account;
import com.OnPoint.DatabaseRelation.Appsql;
import com.OnPoint.DatabaseRelation.Profile;
import com.OnPoint.People;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/account")
public class AccountController {
    Account account = new Account();

    Appsql appsql = new Appsql();
    Connection conndb = appsql.connect();

    @PostMapping("/login")
    Profile login
            (
            @RequestParam String emailoruser,
            @RequestParam String password
            )
    {
        if (authPass(password, false) && (authUsername(emailoruser, false) || authEmail(emailoruser, false))) {
            account.getProfile().setLogin(emailoruser);
            return account.getProfile();
        }
        return null;
    }

    @PostMapping("/register")
    boolean register
            (
                    @RequestParam String name,
                    @RequestParam String email,
                    @RequestParam String password
            )
    {
        if (authPass(password, true) && (authUsername(name, true) && authEmail(email, true))) {
            account.getProfile().setRegister(name, email, password);
            return true;
        }
        return false;
    }

    @GetMapping("/reloadFriends")
    void reloadFriends(){
        account.reloadFriends(conndb);
    }

    @PostMapping("/findFriends")
    String findFriend
            (
                    @RequestParam String friendName
            )
    {
        People friend = account.findFriends(conndb, friendName);
        System.out.println(friend);
        if (friend != null){
            account.uploadFriend(conndb);
            return friend.getUsername();
        }
        return "null";
    }

    @PostMapping("/addFriends")
    People addFriends
            (
                    @RequestParam String friend,
                    @RequestParam boolean confirmation
            )
    {
        if(confirmation) {
            People add = account.addFriend(account.findFriends(conndb, friend));
            if (add != null) {
                account.uploadFriend(conndb);
                return add;
            }
        }
        return null;
    }

    @PostMapping("/showFriends")
    ArrayList<People> showFriends(){
        return account.friends;
    }

    @PostMapping("/deleteFriends")
    void deleteFriend
            (
                    @RequestParam int index
            )
    {
        account.deleteFriend(index);
        account.uploadFriend(conndb);
    }

    @PostMapping("/inviteMeet")
    void inviteMeet
            (
                    @RequestParam int indexAct,
                    @RequestParam int nameFriend
            )
    {
        account.inviteFriends(conndb, indexAct, nameFriend);
        account.uploadFriend(conndb);
    }

    @PostMapping("/reloadFriends")
    void uploadFriends(){
        account.uploadFriend(conndb);
    }

    @PostMapping("/Profile")
    public Profile showProfile() {
        account.showProfile();
        return account.getProfile();
    }

    //Account Creation and Authentication

    //login & register verification
    public boolean authEmail(String email, boolean registerMode){
        //setelah dibandingkan dapat dimasukkan datanya ke dalam database
        //pakai set...()

        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (registerMode && !account.getProfile().checkEmail(email) && matcher.matches()){
            return true;
        } else if (!registerMode && matcher.matches() && account.getProfile().checkEmail(email)){
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
        String regex =  "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pass);
        if (registerMode && matcher.matches()){
            return true;
        }
        else if (!registerMode && matcher.matches() && account.getProfile().checkPassword(pass)){
            return true;
        } else {
            return false;
        }
        //2 pembanding dari regex dan database yang diambil dari Profile
        //belum
    }
    public boolean authUsername(String username, boolean registerMode){
        if(registerMode && !account.getProfile().checkUsername(username)){
            return true;
        } else if ( !registerMode && account.getProfile().checkUsername(username)){
            return true;
        }else {
            return false;
        }
    }
}
