package com.quiz;

import java.sql.Connection;
import java.sql.DriverManager;   

public class TestDB {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Ensure the driver is found
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizdb", "root", "root");
            if (conn != null) {
                System.out.println("✅ Database connected successfully!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
