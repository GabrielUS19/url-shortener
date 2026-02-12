package com.gabriel.urlshortener.network;

import java.util.Map;

public class HttpDispatcher {
    public HttpDispatcher() {}

    public HttpResponse dispatch(HttpRequest request) {
        var method = request.method().toString();
        var path = request.path();

        if (method.equals("POST") && path.equals("/shorten")) {
            var headers = Map.of("Content-Type", "application/json", "Content-Length", "13");
            return new HttpResponse(HttpStatus.OK, headers, "shortened url");
        }

        if (method.equals("GET") && isValidShortCode(path.replace("/", ""))) {
            var headers = Map.of("Location", "https://www.youtube.com");
            return new HttpResponse(HttpStatus.FOUND, headers, null);
        }

        return new HttpResponse(HttpStatus.NOT_FOUND, Map.of(), "Error 404: Page Not Found");
    }

    private boolean isValidShortCode(String code) {
        return code.matches("^[a-zA-Z0-9]{4,10}$");
    }
}
