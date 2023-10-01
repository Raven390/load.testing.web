package ru.develonica.load.testing.web.test.model.request.validator;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.develonica.load.testing.web.test.model.object.TestCase;
import ru.develonica.load.testing.web.test.model.request.TestCaseRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestCaseValidator {
//    private static final String URL_PATTERN_REGEX = "(?<scheme>https?:\\/\\/)(?<subdomain>\\S*?)(?<domainword>[^.\\s]+)(?<tld>\\.[a-z]+|\\.[a-z]{2,3}\\.[a-z]{2,3})(?=\\/|$)";
//    private static final Pattern URL_PATTERN = Pattern.compile(URL_PATTERN_REGEX);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private TestCaseValidator() {
    }

    public static boolean validate(TestCase testCase) {
        if (testCase.getName() == null) {
            return false;
        }
        if (testCase.getTestCaseRequest() == null) {
            return false;
        }

        TestCaseRequest testCaseRequest = testCase.getTestCaseRequest();
        /*
        TODO: доработать валидацию URL
        Matcher matcher = URL_PATTERN.matcher(testCaseRequest.getUrl());
        if (!matcher.find()) {
            return false;
        }*/
        return testCaseRequest.getBody() == null || validateJsonBody(testCaseRequest.getBody());
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
