package com.gabriel.urlshortener.network;

import com.gabriel.urlshortener.enums.HttpStatus;

import java.util.Map;

public record HttpResponse(
    HttpStatus status,
    Map<String, String> headers,
    String body
) {}
