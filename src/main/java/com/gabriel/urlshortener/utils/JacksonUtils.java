package com.gabriel.urlshortener.utils;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

public class JacksonUtils {
    public static String handleJacksonMappingException(JsonMappingException e) {
        return switch (e) {
            case UnrecognizedPropertyException up -> "Unrecognized Field";
            case InvalidFormatException ife -> "Invalid value format";
            case MismatchedInputException mi -> "Invalid data type";
            default -> "Incorrect JSON";
        };
    }

}
