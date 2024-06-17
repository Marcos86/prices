package com.mdelamo.prices.api.rest.advice;

import com.fasterxml.jackson.core.JacksonException;
import com.mdelamo.prices.api.rest.controller.request.ErrorDto;
import com.mdelamo.prices.domain.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ServerErrorException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestExceptionHandlerTest {

    @InjectMocks
    private RestExceptionHandler handler;

    @ParameterizedTest
    @ValueSource(classes = { BusinessException.class })
    void handleBusinessException_shouldMapProperly(final Class<Exception> ex) {

        final var exception = mock(ex);

        when(exception.getMessage()).thenReturn("error");

        final var error = buildErrorDto(exception);

        final var expectedResponse = ResponseEntity.unprocessableEntity()
                .body(error);

        final var actualResponse = this.handler.handleBusinessException(exception);

        verify(exception, atLeast(1)).getMessage();

        assertEquals(expectedResponse, actualResponse);
    }

    @ParameterizedTest
    @ValueSource(classes = { JacksonException.class, NullPointerException.class,
            BindException.class, ServletRequestBindingException.class,
            HttpMessageConversionException.class, NumberFormatException.class,
            MethodArgumentTypeMismatchException.class})
    void handleInvalidRequestException_shouldMapProperly_businessException(final Class<Exception> ex) {

        final var exception = mock(ex);

        when(exception.getMessage()).thenReturn("error");

        final var error = buildErrorDto(exception);

        final var expectedResponse = ResponseEntity.badRequest()
                .body(error);

        final var actualResponse = handler.handleInvalidRequestException(exception);

        verify(exception, atLeast(1)).getMessage();

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void handleException_shouldMapProperly() {

        final var exception = mock(ServerErrorException.class);

        when(exception.getMessage()).thenReturn("error");

        final var error = buildErrorDto(exception);

        final var expectedResponse = ResponseEntity.internalServerError()
                .body(error);

        final var actualResponse = handler.handleException(exception);

        verify(exception, atLeast(1)).getMessage();

        assertEquals(expectedResponse, actualResponse);
    }

    private ErrorDto buildErrorDto(Exception exception) {
        final var error = new ErrorDto();
        error.setMessage(exception.getMessage());
        return error;
    }

}
