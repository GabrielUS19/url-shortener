package com.gabriel.urlshortener.config;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseGenerator {
    private final Connection connection;

    public DatabaseGenerator(Connection connection) {
        this.connection = connection;
    }

    public void generate() {
        var resourcePath = "/database/schema.sql";

        try (
                var stmt = connection.createStatement();
                var input = getClass().getResourceAsStream(resourcePath);
                var reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))
        )
        {
            if (input == null) {
                throw new RuntimeException("Could not find resource: " + resourcePath);
            }

            var query = new StringBuilder();
            String line;


            while ((line = reader.readLine()) != null && !line.isBlank()) {
                query.append(line);
            }

            stmt.execute(query.toString());

        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
