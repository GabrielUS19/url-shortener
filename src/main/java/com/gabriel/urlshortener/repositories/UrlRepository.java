package com.gabriel.urlshortener.repositories;

import com.gabriel.urlshortener.entities.Url;

import java.util.Optional;

public interface UrlRepository {
    void insert(Url entity);
    Url getByShortcode(String shortCode);
}
