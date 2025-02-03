package com.quiz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
	private static final String URL = "jdbc:mysql://localhost:3306/quizdb";
    private static final String USER = "quizapp";
    private static final String PASSWORD = "quizapp_password"; 
    private static final Logger logger = Logger.getLogger(DBConnection.class.getName());

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            logger.info("Database connection established successfully");
            return conn;
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "JDBC Driver not found", e);
            throw new SQLException("JDBC Driver not found", e);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to establish database connection", e);
            throw e;
        }
    }
}

