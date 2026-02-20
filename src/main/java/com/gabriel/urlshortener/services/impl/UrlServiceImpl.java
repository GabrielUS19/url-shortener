package com.gabriel.urlshortener.services.impl;

import com.gabriel.urlshortener.config.AppConfig;
import com.gabriel.urlshortener.entities.Url;
import com.gabriel.urlshortener.exceptions.GenerateUniqueUrlException;
import com.gabriel.urlshortener.exceptions.InvalidUrlException;
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
    public String shorten(String originalUrl) {
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

            return shortenedUrl;
        }

        throw new GenerateUniqueUrlException("It was not possible to generate a unique URL right now");
    }
}
