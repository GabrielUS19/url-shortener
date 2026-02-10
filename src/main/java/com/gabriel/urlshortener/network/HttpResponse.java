package com.gabriel.urlshortener.network;

import java.util.Map;

public record HttpResponse(
    HttpStatus status,
    Map<String, String> headers,
    String Body
) {}
