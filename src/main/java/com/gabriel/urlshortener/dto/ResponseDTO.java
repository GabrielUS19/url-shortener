package com.gabriel.urlshortener.dto;

import com.gabriel.urlshortener.network.HttpStatus;

public record ResponseDTO<T>(
        HttpStatus status,
        T body
) {}
