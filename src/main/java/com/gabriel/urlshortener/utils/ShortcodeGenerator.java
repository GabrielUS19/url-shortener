package com.gabriel.urlshortener.utils;

import java.security.SecureRandom;

public class ShortcodeGenerator {
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 6;

    private ShortcodeGenerator() {}

    public static String generate() {
        var code = new StringBuilder();

        for (int i = 0; i < LENGTH; i++) {
            var secureRandom = new SecureRandom();

            int randomIndex = secureRandom.nextInt(ALPHABET.length() - 1);
            code.append(ALPHABET.charAt(randomIndex));
        }

        return code.toString();
    }

}
