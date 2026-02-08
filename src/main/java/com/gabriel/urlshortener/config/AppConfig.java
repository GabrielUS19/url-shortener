package com.gabriel.urlshortener.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = AppConfig.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input != null) {
                properties.load(input);
            } else {
                System.out.println("Warn: application.properties not found.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String get(String key) {
        String value = System.getenv(key.toUpperCase().replace(".", "_"));

        if (value != null) {
            return value;
        }

        return properties.getProperty(key);
    }

    public static int getInt(String key, int defaultValue) {
        String value = get(key);

        return value != null ? Integer.parseInt(value) : defaultValue;
    }

    public static String getEnvOrDefault(String key, String defaultValue) {
        String value = get(key);

        return value != null ? value : defaultValue;
    }
}
