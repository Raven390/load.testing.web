package ru.develonica.load.testing.web.test.response;

import org.springframework.http.HttpStatus;

public enum ApiErrorCode {
    BAD_REQUEST("4001","Тело запроса не корректно", HttpStatus.BAD_REQUEST),
    MISSING_REQUEST_PARAMETER("4002","Ошибка в запросе: отсутвует параметр %s", HttpStatus.BAD_REQUEST),

    UNKNOWN_CONNECTION_ERROR("5500", "Ошибка соединения", HttpStatus.INTERNAL_SERVER_ERROR),
    CANNOT_OPEN_CONNECTION_WITH_DB("5001","Невозможно установить соединение с базой данных", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_RESOURCE_ACCESS("5002", Constants.DB_ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR),
    UNKNOWN_DB_ERROR("5100", Constants.DB_ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR),
    UNEXPECTED_ERROR("5200", "Произошла непредвиденная ошибка", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    ApiErrorCode(String code, String massage, HttpStatus httpStatus) {
        this.code = code;
        this.message = massage;
        this.httpStatus = httpStatus;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    private static class Constants {
        public static final String DB_ERROR_MESSAGE = "Произошла ошибка при обращении к базе данных";
    }
}
