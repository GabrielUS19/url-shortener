package com.gabriel.urlshortener.network;

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

        resString.append("\r\n");

        if (response.body() != null) {
            resString.append(response.body());
        }

        return resString.toString();
    }
}
