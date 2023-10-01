package ru.develonica.load.testing.web.test.model.response;

import java.time.OffsetDateTime;

public class ApiResponse {
    private OffsetDateTime timestamp;
    private Object data;

    public ApiResponse(Object data) {
        this.timestamp = OffsetDateTime.now();
        this.data = data;
    }

    public ApiResponse() {
        this.timestamp = OffsetDateTime.now();
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
