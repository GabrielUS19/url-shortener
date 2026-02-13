package com.gabriel.urlshortener;

import com.gabriel.urlshortener.config.AppConfig;
import com.gabriel.urlshortener.config.DatabaseConfig;
import com.gabriel.urlshortener.config.DatabaseGenerator;
import com.gabriel.urlshortener.network.Server;

import java.sql.SQLException;


public class Main {
    private static final int PORT = AppConfig.getInt("server.port", 8080);

    public static void main(String[] args) {
        try (var conn = DatabaseConfig.connect()) {
            var databaseGenerator = new DatabaseGenerator(conn);
            databaseGenerator.generate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        var serverSocket = new Server(PORT);
        serverSocket.start();
    }
}
