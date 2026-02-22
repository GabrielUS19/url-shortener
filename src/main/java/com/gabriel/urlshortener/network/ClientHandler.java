package com.gabriel.urlshortener.network;

import com.gabriel.urlshortener.controllers.UrlController;

import java.io.IOException;
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
            var responser = new Responser();

            String response = dispatcher.dispatch(request, responser);

            byte[] responseByte = response.getBytes(StandardCharsets.UTF_8);

            output.write(responseByte);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }
}
