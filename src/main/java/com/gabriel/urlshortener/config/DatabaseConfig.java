package com.gabriel.urlshortener.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final String URL = AppConfig.get("PG_URL");
    private static final String USER = AppConfig.get("PG_USER");
    private static final String PASSWORD = AppConfig.get("PG_PASSWORD");

    private DatabaseConfig() {}

    public static Connection connect() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
