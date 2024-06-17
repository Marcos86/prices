package com.mdelamo.prices.api.rest.advice;


import com.fasterxml.jackson.core.JacksonException;
import com.mdelamo.prices.api.rest.controller.request.ErrorDto;
import com.mdelamo.prices.domain.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ServerErrorException;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {JacksonException.class, NullPointerException.class,
            BindException.class, ServletRequestBindingException.class,
            HttpMessageConversionException.class, NumberFormatException.class,
            MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDto> handleInvalidRequestException(final Exception e) {
        writeLog(e);
        return ResponseEntity.badRequest().body(buildError(e));
    }

    @ExceptionHandler(value = {BusinessException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<ErrorDto> handleBusinessException(final Exception e) {
        writeLog(e);
        return ResponseEntity.unprocessableEntity().body(buildError(e));
    }

    @ExceptionHandler({ServerErrorException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorDto> handleException(final Exception e) {
        writeLog(e);
        return ResponseEntity.internalServerError().body(buildError(e));
    }

    private ErrorDto buildError(Exception e) {
        final var error = new ErrorDto();
        error.setMessage(e.getMessage());
        return error;
    }

    private void writeLog(final Exception e) {
        log.error("Exception message: {}", e.getMessage(), e);
    }
}
