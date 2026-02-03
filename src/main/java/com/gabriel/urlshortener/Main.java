package com.gabriel.urlshortener;

import com.gabriel.urlshortener.config.AppConfig;
import com.gabriel.urlshortener.network.Server;

public class Main {
    private static final int PORT = AppConfig.getInt("server.port", 8080);

    public static void main(String[] args) {
        var serverSocket = new Server(PORT);
        serverSocket.start();
    }
}
