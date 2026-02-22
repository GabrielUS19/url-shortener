package com.gabriel.urlshortener.dto;

import com.gabriel.urlshortener.enums.HttpStatus;

public record ResponseDTO<T>(
        HttpStatus status,
        T body
) {}
