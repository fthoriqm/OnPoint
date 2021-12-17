package com.OnPoint.DatabaseRelation;

import java.sql.*;

public class Appsql {
    private final String url = "jdbc:postgresql://localhost:5432/OnPoint";
    private final String user = "postgres";
    private final String password = "";

    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}