package com.gabriel.urlshortener.network;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabriel.urlshortener.controllers.UrlController;
import com.gabriel.urlshortener.dto.UrlRedirectRequest;
import com.gabriel.urlshortener.dto.UrlShortenRequest;
import com.gabriel.urlshortener.enums.HttpStatus;
import com.gabriel.urlshortener.exceptions.AppException;
import com.gabriel.urlshortener.utils.JacksonUtils;
import com.gabriel.urlshortener.utils.UrlValidator;

import java.util.Map;

public class HttpDispatcher {
    private final UrlController urlController;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public HttpDispatcher(UrlController urlController) {
        this.urlController = urlController;
    }

    public String dispatch(HttpRequest request, Responser response) {
        String requestMethod = request.method().toString();
        String requestPath = request.path();
        String requestBody = request.body();

        try {
            if (requestMethod.equals("POST") && requestPath.equals("/shorten")) {
                var shortenRequest = objectMapper.readValue(requestBody, UrlShortenRequest.class);

                var res = urlController.shorten(shortenRequest);

                return response.sendJson(res.status(), res.body());
            }

            if (requestMethod.equals("GET") && UrlValidator.isValidShortcode(requestPath.replace("/", ""))) {
                var shortCode = requestPath.replace("/", "");

                var originalUrl = urlController.redirect(new UrlRedirectRequest(shortCode));

                return response.redirect(HttpStatus.FOUND, originalUrl);
            }

            return response.sendJson(HttpStatus.NOT_FOUND, Map.of(
                    "success", false,
                    "message", "Error 404: Page Not Found",
                    "data", ""
                    ));

        } catch (AppException e) {
            return response.sendJson(HttpStatus.BAD_REQUEST, Map.of(
                    "success", false,
                    "message", e.getMessage(),
                    "data", ""
            ));

        } catch (JsonParseException e) {
            String message = e.getOriginalMessage();

            Map<String, Object> body = Map.of(
                    "success", false,
                    "message", message,
                    "data", "");

            return response.sendJson(HttpStatus.BAD_REQUEST, body);

        } catch (JsonMappingException e) {
            Map<String, Object> body = Map.of(
                    "success", false,
                    "message", JacksonUtils.handleJacksonMappingException(e),
                    "data", ""
            );

            return response.sendJson(HttpStatus.BAD_REQUEST, body);

        } catch (Exception e) {
            Map<String, Object> body = Map.of(
                    "success", false,
                    "message", "Server Internal Error",
                    "data", "");

            e.printStackTrace();
            return response.sendJson(HttpStatus.SERVER_INTERNAL_ERROR, body);
        }
    }
}
