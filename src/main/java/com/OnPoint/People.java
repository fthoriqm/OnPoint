package com.OnPoint;

public class People {
    private String username;
    private String email;
    private String password;
    private double rating;
    public Rating rate = new Rating();

    public People(String username, String email, String password, double rating) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.rating = rating;
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

    public double getRating() {
        return this.rating;
    }


}
