package com.OnPoint;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Approval {
    private static Rating rating = new Rating();
    public static Punctual punctuality;

    public Approval(Rating rating) {
        Approval.rating = rating;
        punctuality = Punctual.YET;
    }

    private static boolean confirmation() {
        //  if(fromIntegrate()){ //konfirmasi dari lokasi tujuannya atau konfirmasi yang ketergantungan dari temen meetingnya
        //    return true;
        //}
        return false;
    }

    //     fungsi pengecekan ketepatan waktu
//     variable come datang dari aplikasi integrasi e.g zoom, meets, dll)
    public static void checkPuncutal(LocalDateTime come, LocalDateTime scheduled) {
//         if (scheduled.isAfter(come)){
//             Approval.punctuality = Punctual.YET;
//             System.out.println("On The Way");
//         }
        // kalau doi datang sebelum/tepat jadwalnya dan konfirmasi
        if (scheduled.isAfter(come) ){//&& confirmation()) {
            Approval.punctuality = Punctual.YES;
            Approval.rating.notLateRate();
            System.out.println("Thank you for Your Punctuality ;)");
        }
        // kalau doi datang telat
        else {
            Approval.punctuality = Punctual.NO;
            Approval.rating.lateRate();
            System.out.println("You are Late!");
        }
    }
}
//fungsi persetujuan teman / voting
//fungsi konfirmasi dari google map
//fungsi konfirmasi