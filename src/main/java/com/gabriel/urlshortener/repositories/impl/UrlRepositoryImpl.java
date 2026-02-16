package com.gabriel.urlshortener.repositories.impl;

import com.gabriel.urlshortener.config.AppConfig;
import com.gabriel.urlshortener.config.DatabaseConfig;
import com.gabriel.urlshortener.entities.Url;
import com.gabriel.urlshortener.repositories.UrlRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UrlRepositoryImpl implements UrlRepository {

    public UrlRepositoryImpl() {}

    @Override
    public void insert(Url entity) {
        var query = "INSERT INTO urls (original_url, short_code) VALUES (?, ?)";
        try (
                var connection = DatabaseConfig.connect();
                var pstmt = connection.prepareStatement(query)
        ) {
            pstmt.setString(1, entity.getOriginalUrl());
            pstmt.setString(2, entity.getShortCode());

            pstmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Url getByShortcode(String shortCode) {
        return null;
    }
}
