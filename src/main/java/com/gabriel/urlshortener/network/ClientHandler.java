package com.gabriel.urlshortener.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private final Socket clientSocket;

    ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (
                var input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                var output = new PrintWriter(clientSocket.getOutputStream())
        ) {

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
