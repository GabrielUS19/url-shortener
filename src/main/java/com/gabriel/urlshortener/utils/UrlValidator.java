package com.gabriel.urlshortener.utils;

import java.net.URI;
import java.net.URISyntaxException;

public final class UrlValidator {
    private UrlValidator() {}

    public static boolean isValidShortcode(String shortCode) {
        return shortCode.matches("^[a-zA-Z0-9]{6}$");
    }

    public static boolean isWebURL(String urlString) {
        try {
            var uri = new URI(urlString);
            var scheme = uri.getScheme();

            return (scheme != null && (scheme.equalsIgnoreCase("http") || scheme.equalsIgnoreCase("https")) &&
                    uri.getHost() != null);

        } catch (URISyntaxException e) {
            return false;
        }
    }
}
