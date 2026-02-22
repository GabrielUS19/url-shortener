package com.gabriel.urlshortener.controllers;

import com.gabriel.urlshortener.dto.ResponseDTO;
import com.gabriel.urlshortener.dto.UrlShortenRequest;
import com.gabriel.urlshortener.enums.HttpStatus;
import com.gabriel.urlshortener.exceptions.appexceptions.InvalidUrlException;
import com.gabriel.urlshortener.network.HttpResponse;
import com.gabriel.urlshortener.services.UrlService;
import com.gabriel.urlshortener.utils.UrlValidator;

import java.util.Map;

public class UrlController {
    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    public ResponseDTO<Map<String, Object>> shorten(UrlShortenRequest urlShortenRequest) {
        var originalUrl = urlShortenRequest.originalUrl();

        if (!UrlValidator.isWebURL(originalUrl)) {
            throw new InvalidUrlException("Invalid URL");
        }

        var urlShortenResponse = urlService.shorten(originalUrl);

        return new ResponseDTO<>(HttpStatus.CREATED, Map.of(
                "success", true,
                "message", "URL shortened",
                "data", urlShortenResponse
        ));
    }
}
