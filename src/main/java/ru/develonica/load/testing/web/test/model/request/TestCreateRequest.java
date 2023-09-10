package ru.develonica.load.testing.web.test.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TestCreateRequest {

    private String url;
    private String body;
    private Map<String, String> header;
    private Map<String, String> pathVariableMap;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public Map<String, String> getPathVariableMap() {
        return pathVariableMap;
    }

    public void setPathVariableMap(Map<String, String> pathVariableMap) {
        this.pathVariableMap = pathVariableMap;
    }
}
