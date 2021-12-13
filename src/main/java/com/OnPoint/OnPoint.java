package com.OnPoint;

// Source: https://stackoverflow.com/questions/2309558/time-comparison

import com.OnPoint.DatabaseRelation.Appsql;
import com.OnPoint.Registration.userAuth;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Scanner;

import static java.lang.System.exit;
import com.sun.tools.javac.Main;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OnPoint {
    public static void main(String[] args) {
        SpringApplication.run(OnPoint.class, args);
//        Scanner scan = new Scanner(System.in);
//
//        // Mekanisme connect to database
//        Appsql appsql = new Appsql();
//        Connection conndb = appsql.connect();
//
//        //Signing up
//        userAuth signup = new userAuth();
//        signup.userLogin(conndb);
//
//        //menaruh account pada aplikasi yang dibuka
//        Account account = new Account();
//        account.getProfile().setUsername(signup.profile.getUsername());
//        account.getProfile().setEmail(signup.profile.getEmail());
//        account.getProfile().setPassword(signup.profile.getPassword());
//        account.getProfile().setRating(signup.profile.getRating());
//        //Reload data yang ada pada database menuju List yang dimiliki program
//        account.reloadActivity(conndb);
//        account.reloadFriends(conndb);
//        while (true) {
//            //running in background device
//            for (int index = 0 ; account.activityList.size() > index ; index++){
//                LocalDateTime timecheck = account.getActivity(index).getTime().toLocalDateTime();
//                Approval.checkPuncutal(LocalDateTime.now(), timecheck);
//            }
//
//            System.out.println("\n");
//            account.showActivity();
//            System.out.println("\n");
//            System.out.println("----OnPoint----");
//            System.out.println("1. Add schedule");
//            System.out.println("2. Remove schedule");
//            System.out.println("3. Edit schedule");
//            System.out.println("4. Profile");
//            System.out.println("5. Friends");
//            System.out.println("6. Invite to meeting");
//            System.out.println("7. Exit");
//            System.out.println("Menu: ");
//            int input = scan.nextInt();
//
//            switch (input) {
//                case 1:
//                    String addName = scan.nextLine();
//                    System.out.println("Activity name: ");
//                    addName = scan.nextLine();
//
//                    System.out.println("format time input yyyy-MM-dd HH:mm");
//                    System.out.println("appointment time: ");
//                    String addTime = scan.nextLine();
//
//
//                    account.addActivity(addName, addTime);
//                    break;
//                case 2:
//                    if (true) {
//                        System.out.println("Activity index: ");
//                        int activityIndex;
//                        activityIndex = scan.nextInt();
//                        account.removeActivity(activityIndex);
//                    }
//                    break;
//                case 3:
//                    if (true) {
//                        System.out.println("index: ");
//                        int indName = scan.nextInt();
//                        String updateName = scan.nextLine();
//                        System.out.println("Update activity name: ");
//                        updateName = scan.nextLine();
//                        System.out.println("format time input yyyy-MM-dd HH:mm");
//                        System.out.println("Update appointment time: ");
//                        String updateTime = scan.nextLine();
//                        account.editActivity(indName, updateName, updateTime);
//                    }
//                    break;
//                case 4:
//                    account.showProfile();
//                    break;
//                case 5:
//                    System.out.println("--Friends--");
//                    account.showFriends();
//                    System.out.println("1. Add friend");
//                    System.out.println("2. delete friend");
//                    System.out.println("\ninput integer");
//                    int in = scan.nextInt();
//                    switch (in) {
//                        case 1:
//                            String fr = scan.nextLine();
//                            System.out.println("Friend's username: ");
//                            fr = scan.nextLine();
//                            boolean findFriend = account.findFriends(conndb, fr);
//                            if (findFriend) {
//                                System.out.println("do you want add this friend?");
//                                System.out.println("press y for yes");
//                                System.out.println("press n for no");
//                                String AddOrNot = scan.nextLine();
//                                account.confFriends(conndb, AddOrNot);
//                            }
//                            break;
//                        case 2:
//                            System.out.println("index: ");
//                            int friendIndex;
//                            friendIndex = scan.nextInt();
//                            account.deleteFriend(friendIndex);
//                            break;
//
//                    }
//                    break;
//                case 6:
//                    System.out.println("choose a meeting");
//                    account.showActivity();
//                    int indexAct = scan.nextInt();
//
//                    System.out.println("choose a friends");
//                    account.showFriends();
//                    int nameFriend = scan.nextInt();
//
//                    try {
//                        account.inviteFriends(conndb, indexAct, nameFriend);
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//                case 7:
//                    //keluar
//                    scan.close();
//                    try {
//                        conndb.close();
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
//                    exit(0);
//                    break;
//            }
//            account.uploadActivity(conndb, account.getProfile().getUsername());
//            account.uploadFriend(conndb,account.getProfile().getUsername() );
//        }
//
    }
}