package com.gabriel.urlshortener.network;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabriel.urlshortener.enums.HttpStatus;

import java.util.Map;

public class Responser {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    String sendText(HttpStatus status, String body) {
        var headers = Map.of(
                "Content-Type", "text/plain",
                "Connection", "close");

        return ResponseParser.parse(new HttpResponse(status, headers, body));
    }

    String sendJson(HttpStatus status, Map<String, Object> bodyMap) {
        var headers = Map.of(
                "Content-Type", "application/json",
                "Connection", "close");

        String body;

        try {
            body = objectMapper.writeValueAsString(bodyMap);

        } catch (Exception e) {
            body = """
                    {
                        "success": false,
                        "message": "Server Internal Error"
                    }
                    """;
        }

        return ResponseParser.parse(new HttpResponse(status, headers, body));
    }

    String redirect(HttpStatus status, String location) {
        var headers = Map.of(
                "Location", location,
                "Connection", "close");

        return ResponseParser.parse(new HttpResponse(status, headers, null));
    }
}

