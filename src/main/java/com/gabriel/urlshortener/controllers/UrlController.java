package com.gabriel.urlshortener.controllers;

import com.gabriel.urlshortener.dto.ExceptionDTO;
import com.gabriel.urlshortener.dto.ResponseDTO;
import com.gabriel.urlshortener.dto.UrlShortenRequest;
import com.gabriel.urlshortener.dto.UrlShortenResponse;
import com.gabriel.urlshortener.network.HttpStatus;
import com.gabriel.urlshortener.services.UrlService;
import com.gabriel.urlshortener.utils.UrlValidator;

public class UrlController {
    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    public ResponseDTO<?> shorten(UrlShortenRequest urlShortenRequest) {
        var originalUrl = urlShortenRequest.originalUrl();

        if (!UrlValidator.isWebURL(originalUrl)) {
            return new ResponseDTO<>(HttpStatus.BAD_REQUEST, new ExceptionDTO("Invalid URL", "This URL is not a Web URL."));
        }

        String shortenedUrl = urlService.shorten(originalUrl);

        return new ResponseDTO<>(HttpStatus.CREATED, new UrlShortenResponse(shortenedUrl, originalUrl));
    }
}
