package com.gabriel.urlshortener.repositories;

import com.gabriel.urlshortener.entities.Url;

public interface UrlRepository {
    void insert(Url entity);
    Url getByShortcode(String shortCode);
}
