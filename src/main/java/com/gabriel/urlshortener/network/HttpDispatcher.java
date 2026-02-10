package com.gabriel.urlshortener.network;

import jdk.jshell.spi.ExecutionControl;

public class HttpDispatcher {
    HttpDispatcher() {}

    public HttpResponse dispatch(HttpRequest request) throws ExecutionControl.NotImplementedException {
        return switch (request.path()) {
            case "/short-url" -> throw new ExecutionControl.NotImplementedException("Em breve");
            case String s when isValidShortCode(s) -> throw new ExecutionControl.NotImplementedException("Em breve");
            default -> new HttpResponse(HttpStatus.NOT_FOUND, null, null);
        };
    }

    private boolean isValidShortCode(String code) {
        return code.matches("^[a-zA-Z0-9]{4,10}$");
    }
}
