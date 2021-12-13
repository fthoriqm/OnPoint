//// Source: https://stackoverflow.com/questions/2309558/time-comparison
//
//package com.OnPoint;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.Scanner;
//
//public class App {
//    String currentTime;
//
//    public static void main (String[] args) throws SQLException {
//        //Mengkoneksikan dengan postgresql
//        Appsql appsql = new Appsql();
//        Connection conndb = appsql.connect();
//
//        Scanner scan = new Scanner(System.in);
//        //menaruh account pada aplikasi yang dibuka
//        Account account = new Account();
//        // Mekanisme connect to database dalam lingkup "try"
//
//            // System.out.println(account.approvalFriend()); DEBUG
//
//            for(int i = 1 ; i > 0 ; i++){
//                System.out.println("\n");
//                account.showActivity();
//                System.out.println("\n");
//
//                System.out.println("----OnPoint----");
//                System.out.println("1. Add schedule");
//                System.out.println("2. Remove schedule");
//                System.out.println("3. Edit schedule");
//                System.out.println("4. Profile");
//                System.out.println("5. Friends");
//                System.out.println("6. Exit");
//                System.out.println("Menu: ");
//                int input = scan.nextInt();
//
//            switch (input){
//                case 1:
//                    if (true) {
//                        System.out.println("Activity name: ");
//                        String addName = scan.nextLine();
//                        addName = scan.nextLine();
//                        System.out.println("format time input yyyy-MM-dd HH:mm");
//                        System.out.println("appointment time: ");
//                        String addTime = scan.nextLine();
//
//                        account.addActivity(addName, addTime, conndb);
//                    }
//                    break;
//                case 2:
//                    if (true) {
//                        System.out.println("index: ");
//                        int indexName = scan.nextInt();
//
//                        account.removeActivity(indexName);
//                    }
//                    break;
//                case 3:
//                    if (true) {
//                        System.out.println("index: ");
//                        int indName = scan.nextInt();
//
//                        System.out.println("Update activity name: ");
//                        String updateName = scan.nextLine();
//                        updateName = scan.nextLine();
//                        System.out.println("format time input yyyy-MM-dd HH:mm");
//                        System.out.println("Update appointment time: ");
//                        String updateTime = scan.nextLine();
//
////                        account.editActivity(indName, updateName, updateTime);
//                    }
//                    break;
//                case 4:
//                    account.showProfile();
//                    break;
//                case 5:
//                    account.friends();
//                    break;
//                case 6:
//                    //keluar
//                    //exit();
//                    scan.close();
//                    conndb.close();
//                    break;
//            }
//
//            //running in background device
//            //  for (int index = 0 ; account.activityList.size() > index ; index++){
//            //      Approval.checkPuncutal(LocalDateTime.now(), account.getActivity(index).getTime());
//            // }
//
//    //        // Deklarasi aktivitas dari user:  nama aktivitas & jadwalnya
//    //        Activity obj = new Activity("Rapat Onlen", LocalTime.parse("08:46"));
//        }
//
//
//
//            //String sql = "INSERT INTO profile (username, email, password, rating)" + " VALUES ('alik', 'alik.cool@gmail.com', 'h3llo', 6.5)";
//            //String sql = "INSERT INTO profile (username, email, password, rating) VALUES (?, ?, ?, ?)";
//            // PreparedStatement statement = connection.prepareStatement(sql);
//
//            // statement.setString(1, "Thoriq");
//            // statement.setString(2, "fadlurrahman.thoriq@gmail.com");
//            // statement.setString(3, "hai");
//            // statement.setDouble(4, 5.0);
//
//            // int rows = statement.executeUpdate();
//            // if (rows>0){
//            //     System.out.println("You just add a new profile");
//            // }
//
//
//
//
//
//
//
//
//
//        //===========================================================================
//
//        // Uji Coba buat account
////        Account user1 = new Account("abdulmalik", "sukapantai");
////        user1.showRating();
////        user1.addActivity("Cari makan", LocalTime.parse("07:00"));
////        user1.addActivity("Kelas OOP", LocalTime.parse("08:00"));
//
////        System.out.println(user1.getActivity(0).getName());
//    }
//
//    // public static void Login(){
//    //     System.out.println("===>Welcome to OnPoint<===\n");
//    //     System.out.println("press R to register");
//    //     System.out.println("Register\n");
//
//    //     System.out.println("username: ");
//    //     String user = scan.nextLine();
//
//    //     System.out.println("password: ");
//    //     String pass = scan.nextLine();
//    //     if(user.equals("r") || user.equals("R") || pass.equals("R") || pass.equals("r")){
//    //         register();
//    //     }
//    //     else if (!account.authPass(pass) && !account.authUsername(user)) {
//    //         System.out.println("username or password is wrong");
//    //         Login();
//    //     }
//    // }
//    // public static void register() {
//    //     System.out.println("===>Register<===");
//    //     System.out.println("username: ");
//    //     String user = scan.nextLine();
//
//    //     System.out.println("email: ");
//    //     String email = scan.nextLine();
//
//    //     System.out.println("password: ");
//    //     String pass = scan.nextLine();
//    //     //account.profile(user, email, pass);
//    //     Login();
//    // }
//
//    //DATABSE SEGMENT
//    //================================================================
//}