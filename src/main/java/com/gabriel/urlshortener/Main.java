package com.gabriel.urlshortener;

import com.gabriel.urlshortener.config.AppConfig;
import com.gabriel.urlshortener.config.DatabaseConfig;
import com.gabriel.urlshortener.config.DatabaseGenerator;
import com.gabriel.urlshortener.controllers.UrlController;
import com.gabriel.urlshortener.network.Server;
import com.gabriel.urlshortener.repositories.impl.UrlRepositoryImpl;
import com.gabriel.urlshortener.services.impl.UrlServiceImpl;

import java.sql.SQLException;


public class Main {
    private static final int PORT = AppConfig.getInt("server.port", 8080);
    private static final String BASE_URL = AppConfig.getEnvOrDefault("server.base.url", "http://localhost:%s".formatted(PORT));

    public static void main(String[] args) {
        try (var conn = DatabaseConfig.connect()) {
            // Generate the required table "urls"
            var databaseGenerator = new DatabaseGenerator(conn);
            databaseGenerator.generate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        var urlRepository = new UrlRepositoryImpl();
        var urlService = new UrlServiceImpl(urlRepository, BASE_URL);
        var urlController = new UrlController(urlService);

        var serverSocket = new Server(PORT, urlController);
        serverSocket.start();
    }
}
