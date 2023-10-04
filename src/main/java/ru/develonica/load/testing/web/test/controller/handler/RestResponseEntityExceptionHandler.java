package ru.develonica.load.testing.web.test.controller.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.develonica.load.testing.web.test.response.ApiErrorCode;
import ru.develonica.load.testing.web.test.response.ApiErrorResponse;

import java.io.IOException;
import java.sql.SQLTransientConnectionException;
import java.time.OffsetDateTime;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    @ExceptionHandler(value = DataAccessException.class)
    protected ResponseEntity<Object> handleDataAccessDenied(DataAccessException ex, HttpServletRequest httpServletRequest){
        OffsetDateTime timestamp = OffsetDateTime.now();
        if (ex.getCause().getClass().equals(SQLTransientConnectionException.class)) {
            return ResponseEntity.internalServerError().body(new ApiErrorResponse(timestamp, ApiErrorCode.CANNOT_OPEN_CONNECTION_WITH_DB));
        }
        if (ex instanceof InvalidDataAccessResourceUsageException idarue) {
            LOG.error("Ошибка при выполнении запроса (Метод: {}) к базе данных: {}", httpServletRequest.getRequestURL(), idarue.getCause().getMessage(), idarue);
            return ResponseEntity.internalServerError().body(new ApiErrorResponse(timestamp, ApiErrorCode.INVALID_RESOURCE_ACCESS));
        }
        LOG.error("Произошла ошибка {}", ex.getMessage(), ex);
        return ResponseEntity.internalServerError().body(new ApiErrorResponse(timestamp, ApiErrorCode.UNKNOWN_DB_ERROR));
    }

    @ExceptionHandler(value = IOException.class)
    protected ResponseEntity<Object> handleIOException(IOException ex) {
        OffsetDateTime timestamp = OffsetDateTime.now();
        LOG.error("Ошибка соединения: {}", ex.getMessage());
        return ResponseEntity.internalServerError().body(new ApiErrorResponse(timestamp, ApiErrorCode.UNKNOWN_CONNECTION_ERROR));
    }

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<Object> handleException(Exception ex) {
        OffsetDateTime timestamp = OffsetDateTime.now();
        LOG.error("Произошла непредвиденная ошибка: {}", ex.getMessage());
        return ResponseEntity.internalServerError().body(new ApiErrorResponse(timestamp, ApiErrorCode.UNEXPECTED_ERROR));
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ApiErrorResponse response = new ApiErrorResponse(OffsetDateTime.now(), ApiErrorCode.MISSING_REQUEST_PARAMETER, ex.getParameterName());
        return ResponseEntity.badRequest().body(response);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ApiErrorResponse response = new ApiErrorResponse(OffsetDateTime.now(), ApiErrorCode.BAD_REQUEST);
        return ResponseEntity.badRequest().body(response);
    }
}
