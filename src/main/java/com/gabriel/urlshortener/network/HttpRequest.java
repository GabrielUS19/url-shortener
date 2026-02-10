package com.gabriel.urlshortener.network;

import java.util.Map;

public record HttpRequest(
        HttpMethod method,
        String path,
        Map<String, String> headers,
        String body
) {}
