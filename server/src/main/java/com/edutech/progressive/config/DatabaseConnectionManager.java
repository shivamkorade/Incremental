package com.edutech.progressive.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionManager {
    
private static final Properties properties = new Properties();

    // Loads properties from application.properties (Only once)
    private static void loadProperties() {
        if (!properties.isEmpty()) {
            return; // Already loaded
        }
        try (InputStream input = DatabaseConnectionManager.class
                .getClassLoader()
                .getResourceAsStream("application.properties")) {

            if (input == null) {
                throw new RuntimeException("Unable to find application.properties");
            }

            properties.load(input);

        } catch (IOException e) {
            throw new RuntimeException("Error loading database configuration", e);
        }
    }

    // Returns JDBC connection
    // public static Connection getConnection() throws SQLException {
    //     loadProperties(); // Load properties before creating connection

        // String url = properties.getProperty("db.url");
        // String username = properties.getProperty("db.username");
        // String password = properties.getProperty("db.password");
        // String driver = properties.getProperty("db.driver");

       public static String url="jdbc:mysql://localhost:3306/mydb";
       public static String username = "root";
       public static String password ="root";

    //     try {
    //         Class.forName(driver); // Load driver
    //     } catch (ClassNotFoundException e) {
    //         throw new SQLException("Database Driver not found: " + driver, e);
    //     }
    //     return DriverManager.getConnection(url, username, password);
    // }

        public static Connection getConnection() throws SQLException{
            return DriverManager.getConnection(url,username,password);
        }

    }
// }


