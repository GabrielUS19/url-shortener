package com.gabriel.urlshortener;

import com.gabriel.urlshortener.config.AppConfig;
import com.gabriel.urlshortener.config.DatabaseConfig;
import com.gabriel.urlshortener.network.Server;

import java.sql.SQLException;

public class Main {
    private static final int PORT = AppConfig.getInt("server.port", 8080);

    public static void main(String[] args) {
        try (var connection = DatabaseConfig.connect()) {
            var statement = connection.prepareStatement("SELECT * FROM urls");
            System.out.println(statement.executeQuery().toString());

        } catch (SQLException e) {
            System.err.println(e);
        }



//        var serverSocket = new Server(PORT);
//        serverSocket.start();
    }
}
