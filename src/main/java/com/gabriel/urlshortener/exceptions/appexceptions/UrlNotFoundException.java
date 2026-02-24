package com.gabriel.urlshortener.exceptions.appexceptions;

import com.gabriel.urlshortener.enums.HttpStatus;
import com.gabriel.urlshortener.exceptions.AppException;

public class UrlNotFoundException extends AppException {
    public UrlNotFoundException(String message, HttpStatus status) {
        super(message, status);
    }
}
