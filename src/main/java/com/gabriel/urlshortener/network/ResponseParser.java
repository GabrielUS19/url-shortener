package com.gabriel.urlshortener.network;

import java.nio.charset.StandardCharsets;

public final class ResponseParser {
    private ResponseParser() {}

    static String parse(HttpResponse response) {
        var resString = new StringBuilder();
        var statusCode = response.status().getCode();
        var statusMessage = response.status().getMessage();

        resString.append("HTTP/1.1 ").append(statusCode).append(" ").append(statusMessage).append("\r\n");
        for (var header : response.headers().entrySet()) {
            resString.append(header.getKey()).append(": ").append(header.getValue()).append("\r\n");
        }

        resString.append("Content-Length: ");

        if (response.body() != null) {
            resString.append(response.body().getBytes(StandardCharsets.UTF_8).length)
                    .append("\r\n")
                    .append("\r\n")
                    .append(response.body());
        } else {
            resString.append("0")
                    .append("\r\n")
                    .append("\r\n");
        }

        return resString.toString();
    }
}
