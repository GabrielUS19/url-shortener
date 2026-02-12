package com.gabriel.urlshortener.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public final class RequestParser {

    private RequestParser() {}

    static HttpRequest parse(InputStream inputStream) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(inputStream));

        String firstLine = reader.readLine();
        if (firstLine == null || firstLine.isBlank()) {
            throw new IOException("Invalid request");
        }

        var parts = firstLine.split(" ");
        var method = HttpMethod.valueOf(parts[0]);
        var path = parts[1];

        Map<String, String> headers = new HashMap<>();
        String headerLine;

        while (!(headerLine = reader.readLine()).isEmpty()) {
            var headerParts = headerLine.split(": ", 2);
            headers.put(parts[0], parts[1]);
        }

        var body = new StringBuilder();
        if (headers.containsKey("Content-Length")) {
            int length = Integer.parseInt(headers.get("Content-Length"));
            for (int i = 0; i < length; i++) {
                body.append((char) reader.read());
            }
        }

        return new HttpRequest(method, path, headers, body.toString());
    }
}
