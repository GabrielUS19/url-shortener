package com.gabriel.urlshortener.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;

public class Server {
    private final int port;

    public Server(int port) {
        this.port = port;
    }

    public void start() {
        try (
                var serverSocket = new ServerSocket(port);
                var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            System.out.println("Server running in port: " + port);

            while (!Thread.currentThread().isInterrupted()) {
                var clientSocket = serverSocket.accept();

                executor.submit(new ClientHandler(clientSocket));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
