package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/shoestoredb";
    private static final String USER = "root";
    private static final String PASS = "";
    
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC driver not found.");
            e.printStackTrace();
            throw new RuntimeException("MySQL JDBC driver not found.", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            System.out.println("Failed to connect to database.");
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to database.", e);
        }
    }

    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            System.out.println("Connection successful!");
        } catch (Exception e) {
            System.out.println("Connection failed.");
            e.printStackTrace();
        }
    }
}
