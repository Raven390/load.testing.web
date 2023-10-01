package ru.develonica.load.testing.web.test.model.exception;

public class LoadTestingStartException extends Exception {
    public LoadTestingStartException() {
    }

    public LoadTestingStartException(String message) {
        super(message);
    }

    public LoadTestingStartException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoadTestingStartException(Throwable cause) {
        super(cause);
    }

    public LoadTestingStartException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}