package com.gabriel.urlshortener.services;

import com.gabriel.urlshortener.dto.UrlShortenResponse;

public interface UrlService {
    UrlShortenResponse shorten(String originalUrl);
}
