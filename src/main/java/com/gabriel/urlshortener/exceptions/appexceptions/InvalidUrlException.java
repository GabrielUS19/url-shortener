package com.gabriel.urlshortener.exceptions.appexceptions;

import com.gabriel.urlshortener.enums.HttpStatus;
import com.gabriel.urlshortener.exceptions.AppException;

public class InvalidUrlException extends AppException {
    public InvalidUrlException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
