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
    Profile login (@RequestParam String emailoruser, @RequestParam String password) {
        if (authPass(password, false) && (authUsername(emailoruser, false) || authEmail(emailoruser, false))) {
            account.getProfile().setLogin(emailoruser);
            account.reloadFriends(conndb);
            return this.account.getProfile();
        }
        return null;
    }
    @PostMapping("/register")
    boolean register(@RequestParam String name, @RequestParam String email, @RequestParam String password) {
        if (authPass(password, true) && (authUsername(name, true) && authEmail(email, true))) {
            account.getProfile().setRegister(name, email, password);
            return true;
        }
        return false;
    }
    @GetMapping("/findFriends")
    String findFriend(@RequestParam String friendName) {
        String friend = account.findFriends(conndb, friendName);
        System.out.println("yessss"+friend);
        if (friend.equals(friendName)){
            return friend;
        }
        return "not found";
    }
    @PostMapping("/addFriends")
    ArrayList<People> addFriends(@RequestParam boolean confirmation) {
        if(confirmation){
            account.addFriend(conndb);
        }else{
            account.friends.remove(account.friends.size()-1);
        }
        return account.friends;
    }
    @GetMapping("/Profile")
    public Profile showProfile() {
        return account.getProfile();
    }

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

//    GsonBuilder gsonBuilder = new GsonBuilder();
//
//    // This is the main class for using Gson. Gson is typically used by first constructing a Gson instance and then invoking toJson(Object) or fromJson(String, Class) methods on it.
//    // Gson instances are Thread-safe so you can reuse them freely across multiple threads.
//    Gson gson = gsonBuilder.create();
//
//    String JSONObject = gson.toJson(crunchify);
//    log("\nConverted JSONObject ==> " + JSONObject);
//
//    Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
//    String prettyJson = prettyGson.toJson(crunchify);
}
