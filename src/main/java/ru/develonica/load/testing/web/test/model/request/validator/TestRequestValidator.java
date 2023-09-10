package ru.develonica.load.testing.web.test.model.request.validator;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.develonica.load.testing.web.test.model.request.TestCreateRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRequestValidator {
    private static final String URL_PATTERN_REGEX = "(?<scheme>https?:\\/\\/)(?<subdomain>\\S*?)(?<domainword>[^.\\s]+)(?<tld>\\.[a-z]+|\\.[a-z]{2,3}\\.[a-z]{2,3})(?=\\/|$)";
    private static final Pattern URL_PATTERN = Pattern.compile(URL_PATTERN_REGEX);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private TestRequestValidator() {
    }

    public static boolean validate(TestCreateRequest testCreateRequest) {
        Matcher matcher = URL_PATTERN.matcher(testCreateRequest.getUrl());
        return matcher.find() && validateJsonBody(testCreateRequest.getBody());
    }

    private static boolean validateJsonBody(String json) {
        try {
            objectMapper.readTree(json);
        } catch (JacksonException je) {
            return false;
        }
        return true;
    }
}
