package com.gabriel.urlshortener.services.impl;

import com.gabriel.urlshortener.config.AppConfig;
import com.gabriel.urlshortener.entities.Url;
import com.gabriel.urlshortener.exceptions.GenerateUniqueUrlException;
import com.gabriel.urlshortener.repositories.UrlRepository;
import com.gabriel.urlshortener.services.UrlService;
import com.gabriel.urlshortener.utils.ShortcodeGenerator;

public class UrlServiceImpl implements UrlService {
    private final UrlRepository urlRepository;
    private static final int MAX_ATTEMPTS = 3;

    private static final int PORT = AppConfig.getInt("server.port", 8080);
    private static final String SERVER_BASE_URL = AppConfig.getEnvOrDefault("app.base.port", "http://localhost:%d".formatted(PORT));

    public UrlServiceImpl(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Override
    public String shorten(String originalUrl) {
        var attempts = 0;

        while (attempts < MAX_ATTEMPTS) {
            var shortCode = ShortcodeGenerator.generate();
            var url = urlRepository.getByShortcode(shortCode);

            if (url != null) {
                attempts++;
                continue;
            }

            String shortenedUrl = SERVER_BASE_URL + "/" + shortCode;

            var entityUrl = new Url(originalUrl, shortCode);
            urlRepository.insert(entityUrl);

            return shortenedUrl;
        }

        throw new GenerateUniqueUrlException("It was not possible to generate a unique URL right now");
    }
}
