package com.gabriel.urlshortener.services.impl;

import com.gabriel.urlshortener.dto.UrlRedirectResponse;
import com.gabriel.urlshortener.dto.UrlShortenResponse;
import com.gabriel.urlshortener.entities.Url;
import com.gabriel.urlshortener.enums.HttpStatus;
import com.gabriel.urlshortener.exceptions.appexceptions.GenerateUniqueUrlException;
import com.gabriel.urlshortener.exceptions.appexceptions.InvalidShortCodeException;
import com.gabriel.urlshortener.exceptions.appexceptions.InvalidUrlException;
import com.gabriel.urlshortener.exceptions.appexceptions.UrlNotFoundException;
import com.gabriel.urlshortener.repositories.UrlRepository;
import com.gabriel.urlshortener.services.UrlService;
import com.gabriel.urlshortener.utils.ShortcodeGenerator;
import com.gabriel.urlshortener.utils.UrlValidator;

public class UrlServiceImpl implements UrlService {
    private final UrlRepository urlRepository;
    private static final int MAX_ATTEMPTS = 3;
    private final String serverBaseUrl;

    public UrlServiceImpl(UrlRepository urlRepository, String serverBaseUrl) {
        this.urlRepository = urlRepository;
        this.serverBaseUrl = serverBaseUrl;
    }

    @Override
    public UrlShortenResponse shorten(String originalUrl) {
        if (originalUrl == null || originalUrl.isBlank()) {
            throw new InvalidUrlException("Original URL required");
        }

        if (!UrlValidator.isWebURL(originalUrl)) {
            throw new InvalidUrlException("Invalid URL");
        }

        var attempts = 0;

        while (attempts < MAX_ATTEMPTS) {
            var shortCode = ShortcodeGenerator.generate();
            var url = urlRepository.getByShortcode(shortCode);

            if (url != null) {
                attempts++;
                continue;
            }

            String shortenedUrl = serverBaseUrl + "/" + shortCode;

            var entityUrl = new Url(originalUrl, shortCode);
            urlRepository.insert(entityUrl);

            return new UrlShortenResponse(shortenedUrl, originalUrl);
        }

        throw new GenerateUniqueUrlException("It was not possible to generate a unique URL right now");
    }

    @Override
    public UrlRedirectResponse redirect(String shortCode) {
        if (!UrlValidator.isValidShortcode(shortCode)) {
            throw new InvalidShortCodeException("Invalid Shortcode", HttpStatus.BAD_REQUEST);
        }

        var url = urlRepository.getByShortcode(shortCode);

        if (url == null) {
            throw new UrlNotFoundException("Error 404: Page Not Found", HttpStatus.NOT_FOUND);
        }

        return new UrlRedirectResponse(url.getOriginalUrl());
    }
}
