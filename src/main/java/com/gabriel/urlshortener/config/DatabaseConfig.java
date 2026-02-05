package com.gabriel.urlshortener.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final String URL = AppConfig.getEnvOrDefault("PG_URL", "jdbc:postgresql://localhost:5432/url_db");
    private static final String USER = AppConfig.getEnvOrDefault("PG_USER", "postgres");
    private static final String PASSWORD = AppConfig.getEnvOrDefault("PG_PASSWORD", "1234");

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private DatabaseConfig() {}

    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
