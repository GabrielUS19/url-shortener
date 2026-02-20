package com.gabriel.urlshortener.network;

import com.gabriel.urlshortener.controllers.UrlController;
import jdk.jshell.spi.ExecutionControl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientHandler implements Runnable{
    private final Socket clientSocket;
    private final UrlController urlController;

    ClientHandler(Socket clientSocket, UrlController urlController) {
        this.clientSocket = clientSocket;
        this.urlController = urlController;
    }

    @Override
    public void run() {
        try (
                var input = clientSocket.getInputStream();
                var output = clientSocket.getOutputStream()
                ) {
            HttpRequest request = RequestParser.parse(input);

            var dispatcher = new HttpDispatcher(urlController);
            HttpResponse response = dispatcher.dispatch(request);

            byte[] responseByte = ResponseParser.parse(response).getBytes(StandardCharsets.UTF_8);

            output.write(responseByte);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }
}
