package ru.develonica.load.testing.web.test.model.request.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import ru.develonica.load.testing.web.test.model.request.TestCaseRequest;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


class TestTestRequestValidator {
    public static final String CORRECT_URL = "https://music.yandex.ru/users/voronvorontsov/playlists";
    static final String MALFORMED_URL_INVALID_PROTOCOL = "htttps://music.yandex.ru/users/voronvorontsov/playlists";
    static final String MALFORMED_URL_WRONG_DOMAIN = "https://music/users/voronvorontsov/playlists";
    TestCaseRequest request = new TestCaseRequest();
    @BeforeEach
    void setup() {
        request.setUrl(CORRECT_URL);
        request.setBody("""
                {
                    "dateStart": "2023-02-01T14:46:54.824+03:00",
                    "dateEnd": "2023-07-28T14:46:54.824+03:00"
                }
                """);
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("X-INN-Subject", "0101006425");
        request.setHeader(headerMap);
        request.setPathVariableMap(null);
    }

    @Order(0)
    @Test
    void test01_AssertThatCorrectRequestReturnsTrue() {

        boolean result = TestRequestValidator.validate(request);
        assertThat(result).isTrue();
    }

    @Order(1)
    @Test
    void test02_AssertThatMalformedUrlProtocolCauseValidationError() {
        request.setUrl(MALFORMED_URL_INVALID_PROTOCOL);
        assertThat(TestRequestValidator.validate(request)).isFalse();
    }

    @Order(2)
    @Test
    void test03_AssertThatMalformedUrlDomainCauseValidationError() {
        request.setUrl(MALFORMED_URL_WRONG_DOMAIN);
        assertThat(TestRequestValidator.validate(request)).isFalse();
    }

    @Order(3)
    @Test
    void test_AssertThatMalformedJsonBodyCauseValidationError() {
        request.setBody("Invalid body");
        assertThat(TestRequestValidator.validate(request)).isFalse();
    }
}
