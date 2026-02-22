package com.gabriel.urlshortener.exceptions.appexceptions;

import com.gabriel.urlshortener.enums.HttpStatus;
import com.gabriel.urlshortener.exceptions.AppException;

public class GenerateUniqueUrlException extends AppException {
    public GenerateUniqueUrlException(String message) {
        super(message, HttpStatus.SERVER_INTERNAL_ERROR);
    }
}
