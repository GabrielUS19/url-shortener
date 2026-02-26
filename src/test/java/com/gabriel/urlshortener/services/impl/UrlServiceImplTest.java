package com.gabriel.urlshortener.services.impl;

import com.gabriel.urlshortener.entities.Url;
import com.gabriel.urlshortener.exceptions.appexceptions.GenerateUniqueUrlException;
import com.gabriel.urlshortener.exceptions.appexceptions.InvalidShortCodeException;
import com.gabriel.urlshortener.exceptions.appexceptions.InvalidUrlException;
import com.gabriel.urlshortener.exceptions.appexceptions.UrlNotFoundException;
import com.gabriel.urlshortener.repositories.UrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UrlServiceImplTest {

    @Mock
    private UrlRepository urlRepository;

    private UrlServiceImpl urlService;

    private static final String TEST_BASE_URL = "https://test";

    @BeforeEach
    void setUp() {
        urlService = new UrlServiceImpl(urlRepository, TEST_BASE_URL);
    }

    @Test
    @DisplayName("Should return the shortened URL when a valid URL is provided")
    void shouldReturnValidShortenedUrlWhenValidUrl() {
        String originalUrl = "https://google.com";

        when(urlRepository.getByShortcode(anyString())).thenReturn(null);

        var result = urlService.shorten(originalUrl);

        assertTrue(result.shortenedUrl().startsWith("https://test"));
        verify(urlRepository, times(1)).insert(any(Url.class));
    }

    @ParameterizedTest
    @ValueSource(strings = {"gab://www.youtube.com", "http://", "https:/youtube.com", "<script>alert(1)</script>"})
    @DisplayName("Should throw an InvalidUrlException for different invalid URLs")
    void shouldThrowExceptionWhenInvalidUrl(String invalidUrl) {
        InvalidUrlException exception = assertThrows(
                InvalidUrlException.class,
                () -> urlService.shorten(invalidUrl)
        );

        assertEquals("Invalid URL", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    @DisplayName("Should throw an InvalidUrlException when OriginalUrl is Null, Empty or Blank")
    void shouldThrowExceptionWhenEmptyUrl(String originalUrl) {
        InvalidUrlException exception = assertThrows(
                InvalidUrlException.class,
                () -> urlService.shorten(originalUrl)
        );

        assertEquals("Original URL required", exception.getMessage());
    }

    @Test
    @DisplayName("Should try again when a short code that is already in use is generated")
    void shouldTryAgainWhenAlreadyInUseShortCodeGenerated() {
        String originalUrl = "https://youtube.com";
        var existingUrl = new Url("test", "test");

        when(urlRepository.getByShortcode(any()))
                .thenReturn(existingUrl)
                .thenReturn(existingUrl)
                .thenReturn(null);

        urlService.shorten(originalUrl);

        verify(urlRepository, times(3)).getByShortcode(anyString());
        verify(urlRepository, times(1)).insert(any(Url.class));
    }

    @Test
    @DisplayName("Should throw a GenerateUniqueUrlException when exceds three attempts")
    void shouldThrowExceptionWhenExceds3Attempts() {
        String originalUrl = "https://youtube.com";
        var existingUrl = new Url("test", "test");

        when(urlRepository.getByShortcode(any())).thenReturn(existingUrl);

        var exception = assertThrows(
                GenerateUniqueUrlException.class,
                () -> urlService.shorten(originalUrl)
        );

        assertEquals(
                "It was not possible to generate a unique URL right now",
                exception.getMessage()
        );
        verify(urlRepository, times(3)).getByShortcode(anyString());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n", "-92jas", "ak kls", "asduwjk", "ywh67"})
    @DisplayName("Should throw a InvalidShortcodeException when an invalid shortcode is provided")
    void shouldThrowInvalidShortcodeExceptionWhenInvalidShortcode(String shortcode) {
        var exception = assertThrows(
                InvalidShortCodeException.class,
                () -> urlService.redirect(shortcode)
        );

        assertEquals("Invalid Shortcode", exception.getMessage());
    }

    @Test
    @DisplayName("Should Throw UrlNotFoundException when the url is not found")
    void shouldThrowUrlNotFoundExceptionWhenUrlNotFound() {
        var shortcode = "JhsSki";

        when(urlRepository.getByShortcode(anyString())).thenReturn(null);

        var exception = assertThrows(
                UrlNotFoundException.class,
                () -> urlService.redirect(shortcode)
        );

        assertEquals("Error 404: Page Not Found", exception.getMessage());
    }

}