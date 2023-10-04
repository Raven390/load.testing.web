package ru.develonica.load.testing.web.test.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.OffsetDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse {

    private OffsetDateTime timestamp;

    @JsonIgnore
    private ApiErrorCode apiErrorCode;

    private String code;

    private String message;

    public ApiErrorResponse(OffsetDateTime timestamp, ApiErrorCode apiErrorCode) {
        this.timestamp = timestamp;
        this.apiErrorCode = apiErrorCode;
        this.code = apiErrorCode.getCode();
        this.message = apiErrorCode.getMessage();
    }

    public ApiErrorResponse(OffsetDateTime timestamp, ApiErrorCode apiErrorCode, String missingParameter) {
        this.timestamp = timestamp;
        this.apiErrorCode = apiErrorCode;
        this.code = apiErrorCode.getCode();
        this.message = apiErrorCode.getMessage().formatted(missingParameter);
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public ApiErrorCode getApiErrorCode() {
        return apiErrorCode;
    }

    public void setApiErrorCode(ApiErrorCode apiErrorCode) {
        this.apiErrorCode = apiErrorCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
