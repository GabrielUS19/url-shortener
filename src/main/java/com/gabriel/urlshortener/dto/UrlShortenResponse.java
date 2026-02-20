package com.gabriel.urlshortener.dto;

import com.gabriel.urlshortener.network.HttpMethod;

public record UrlShortenResponse(String shortUrl, String originalUrl) {
}
