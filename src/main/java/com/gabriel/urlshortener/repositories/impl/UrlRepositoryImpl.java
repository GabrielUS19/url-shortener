package com.gabriel.urlshortener.repositories.impl;

import com.gabriel.urlshortener.config.DatabaseConfig;
import com.gabriel.urlshortener.entities.Url;
import com.gabriel.urlshortener.repositories.UrlRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.UUID;

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
        var query = "SELECT id, original_url, short_code, created_at FROM urls WHERE short_code = ?";
        try (
                var connection = DatabaseConfig.connect();
                var pstmt = connection.prepareStatement(query)
        ) {
            pstmt.setString(1, shortCode);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    var url = new Url(rs.getString("original_url"), rs.getString("short_code"));

                    url.setId(rs.getObject("id", UUID.class));

                    url.setCreatedAt(rs.getObject("created_at", OffsetDateTime.class));

                    return url;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
