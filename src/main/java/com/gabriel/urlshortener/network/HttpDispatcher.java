package com.gabriel.urlshortener.network;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.gabriel.urlshortener.controllers.UrlController;
import com.gabriel.urlshortener.dto.ExceptionDTO;
import com.gabriel.urlshortener.dto.ResponseDTO;
import com.gabriel.urlshortener.dto.UrlShortenRequest;
import com.gabriel.urlshortener.utils.UrlValidator;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpDispatcher {
    private final UrlController urlController;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public HttpDispatcher(UrlController urlController) {
        this.urlController = urlController;
    }

    public HttpResponse dispatch(HttpRequest request) throws JsonProcessingException {
        String requestMethod = request.method().toString();
        String requestPath = request.path();
        String requestBody = request.body();

        if (requestBody == null || requestBody.isBlank()) {
            var exception = new ExceptionDTO("Invalid request", "Required body");
            var body = objectMapper.writeValueAsString(exception);
            var bodyLength = String.valueOf(body.getBytes(StandardCharsets.UTF_8).length);

            var headers = Map.of(
                    "Content-Type", "application/json",
                    "Content-Length", bodyLength,
                    "Connection", "close"
            );

            return new HttpResponse(HttpStatus.BAD_REQUEST, headers, body);
        }

        if (requestMethod.equals("POST") && requestPath.equals("/shorten")) {
            try {
                var urlShortenRequest = objectMapper.readValue(requestBody, UrlShortenRequest.class);

                var responseController = urlController.shorten(urlShortenRequest);

                var status = responseController.status();

                String body = objectMapper.writeValueAsString(responseController.body());
                var bodyLength = String.valueOf(body.getBytes(StandardCharsets.UTF_8).length);

                var headers = Map.of(
                        "Content-Type", "application/json",
                        "Content-Length", bodyLength,
                        "Connection", "close"
                );

                return new HttpResponse(status, headers, body);

            } catch (UnrecognizedPropertyException e) {
                var exception = new ExceptionDTO("Unrecognized Field", "Unrecognized field '%s'".formatted(e.getPropertyName()));
                var body = objectMapper.writeValueAsString(exception);
                var bodyLength = String.valueOf(body.getBytes(StandardCharsets.UTF_8).length);

                var headers = Map.of(
                        "Content-Type", "application/json",
                        "Content-Length", bodyLength,
                        "Connection", "close"
                );

                return new HttpResponse(HttpStatus.BAD_REQUEST, headers, body);

            } catch (Exception e) {
                System.err.println(e);
                throw new RuntimeException(e);
            }

        }

        if (requestMethod.equals("GET") && UrlValidator.isValidShortcode(requestPath.replace("/", ""))) {
            var headers = Map.of(
                    "Location", "https://www.youtube.com",
                    "Connection", "close"
            );
            return new HttpResponse(HttpStatus.FOUND, headers, null);
        }

        String body404 = "Error 404: Page Not Found";
        var headers404 = Map.of("Content-Length", String.valueOf(body404.getBytes(StandardCharsets.UTF_8).length));

        return new HttpResponse(HttpStatus.NOT_FOUND, headers404, body404);
    }
}
