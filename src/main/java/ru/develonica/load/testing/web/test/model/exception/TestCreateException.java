package ru.develonica.load.testing.web.test.model.exception;

public class TestCreateException extends Exception {
    public TestCreateException() {
    }

    public TestCreateException(String message) {
        super(message);
    }

    public TestCreateException(String message, Throwable cause) {
        super(message, cause);
    }

    public TestCreateException(Throwable cause) {
        super(cause);
    }

    public TestCreateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
