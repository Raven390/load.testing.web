package ru.develonica.load.testing.web.test.model.request.validator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import ru.develonica.load.testing.web.test.model.object.TestCase;
import ru.develonica.load.testing.web.test.model.request.TestCaseRequest;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


class TestTestCaseValidator {
    public static final String CORRECT_URL = "https://music.yandex.ru/users/voronvorontsov/playlists";
    static final String MALFORMED_URL_INVALID_PROTOCOL = "htttps://music.yandex.ru/users/voronvorontsov/playlists";
    static final String MALFORMED_URL_WRONG_DOMAIN = "https://music/users/voronvorontsov/playlists";
    TestCase request = new TestCase();
    ObjectMapper objectMapper = new ObjectMapper();
    @BeforeEach
    void setup() {
        request.setTestCaseRequest(new TestCaseRequest());
        TestCaseRequest testCaseRequest = request.getTestCaseRequest();
        testCaseRequest.setUrl(CORRECT_URL);
        testCaseRequest.setBody("""
                {
                    "dateStart": "2023-02-01T14:46:54.824+03:00",
                    "dateEnd": "2023-07-28T14:46:54.824+03:00"
                }
                """);
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("X-INN-Subject", "0101006425");
        testCaseRequest.setHeader(headerMap);
    }

    @Order(0)
    @Test
    void test01_AssertThatCorrectRequestReturnsTrue() throws JsonProcessingException {
        request.setName("Name");
        boolean result = TestCaseValidator.validate(request);
        assertThat(result).isTrue();
    }

    @Order(1)
    @Test
    void test02_AssertThatMalformedUrlProtocolCauseValidationError() {
        request.getTestCaseRequest().setUrl(MALFORMED_URL_INVALID_PROTOCOL);
        assertThat(TestCaseValidator.validate(request)).isFalse();
    }

    @Order(2)
    @Test
    void test03_AssertThatMalformedUrlDomainCauseValidationError() {
        request.getTestCaseRequest().setUrl(MALFORMED_URL_WRONG_DOMAIN);
        assertThat(TestCaseValidator.validate(request)).isFalse();
    }

    @Order(3)
    @Test
    void test_AssertThatMalformedJsonBodyCauseValidationError() {
        request.getTestCaseRequest().setBody("Invalid body");
        assertThat(TestCaseValidator.validate(request)).isFalse();
    }
}
