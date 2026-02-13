package com.gabriel.urlshortener.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final String URL = AppConfig.getEnvOrDefault("PG_URL", "jdbc:postgresql://localhost:5432/url_db");
    private static final String USER = AppConfig.getEnvOrDefault("PG_USER", "postgres");
    private static final String PASSWORD = AppConfig.getEnvOrDefault("PG_PASSWORD", "1234");

    private DatabaseConfig() {}

    public static Connection connect() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
