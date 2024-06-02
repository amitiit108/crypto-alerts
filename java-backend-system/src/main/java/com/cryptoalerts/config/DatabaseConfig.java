package com.cryptoalerts.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConfig {
    private static final Logger logger = Logger.getLogger(DatabaseConfig.class.getName());
    private final String url;
    private final String username;
    private final String password;

    public DatabaseConfig(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Connection getConnection() throws SQLException {
        try {
            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Failed to load JDBC driver", e);
            throw new SQLException("Failed to load JDBC driver", e);
        }
        
        // Obtain a database connection
        Connection connection = DriverManager.getConnection(url, username, password);
        logger.info("Database connection established");
        return connection;
    }

    // Close the database connection
    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                logger.info("Database connection closed");
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Error closing database connection", e);
            }
        }
    }
}
