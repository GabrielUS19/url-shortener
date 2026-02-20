package com.gabriel.urlshortener.exceptions;

public class GenerateUniqueUrlException extends RuntimeException {
    public GenerateUniqueUrlException(String message) {
        super(message);
    }
}
