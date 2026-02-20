package com.gabriel.urlshortener.network;

import com.gabriel.urlshortener.controllers.UrlController;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;

public class Server {
    private final int port;
    private final UrlController urlController;

    public Server(int port, UrlController urlController) {
        this.port = port;
        this.urlController = urlController;
    }

    public void start() {
        try (
                var serverSocket = new ServerSocket(port);
                var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            System.out.println("Server running in port: " + port);

            while (!Thread.currentThread().isInterrupted()) {
                var clientSocket = serverSocket.accept();

                executor.submit(new ClientHandler(clientSocket, urlController));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
