package com.alumni.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseUtil {
    private static final Logger LOGGER = Logger.getLogger(DatabaseUtil.class.getName());
    private static final Properties properties = new Properties();
    
    static {
        try {
            // Load the MariaDB driver
            Class.forName("org.mariadb.jdbc.Driver");
            
            // Load database properties
            try (InputStream input = DatabaseUtil.class.getClassLoader().getResourceAsStream("database.properties")) {
                if (input == null) {
                    LOGGER.severe("Unable to find database.properties");
                    throw new RuntimeException("Unable to find database.properties");
                }
                properties.load(input);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Error loading database properties", e);
                throw new RuntimeException("Error loading database properties", e);
            }
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "MariaDB JDBC Driver not found", e);
            throw new RuntimeException("MariaDB JDBC Driver not found", e);
        }
    }
    
    public static Connection getConnection() throws SQLException {
        String url = properties.getProperty("db.url");
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");
        
        LOGGER.info("Connecting to database: " + url);
        return DriverManager.getConnection(url, username, password);
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error closing database connection", e);
            }
        }
    }
} // improve exception handling
// cleanup utility methods
// optimize connection reuse
