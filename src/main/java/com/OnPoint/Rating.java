package com.OnPoint;

public class Rating {
    double rate;
    int totalMeet;

    public Rating(){
        this.rate = 5.00;
        this.totalMeet = 1;
    }

    void lateRate(){
        this.totalMeet = this.totalMeet++;
        this.rate = (this.rate / this.totalMeet);
    }
    void notLateRate(){
        this.totalMeet = this.totalMeet++;
        this.rate += 5.00;
        this.rate = (this.rate / this.totalMeet);
    }
    public double getRate(){
        return this.rate;
    }
    public double getTotalMeet(){
        return this.totalMeet;
    }
}