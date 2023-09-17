package ru.develonica.load.testing.web.test.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.develonica.load.testing.web.test.model.exception.TestCreateException;
import ru.develonica.load.testing.web.test.model.object.TestCase;
import ru.develonica.load.testing.web.test.model.request.TestCaseRequest;
import ru.develonica.load.testing.web.test.repository.TestCaseRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


class TestTestService {
    public static final String CORRECT_URL = "https://music.yandex.ru/users/voronvorontsov/playlists";
    static TestService testService = new TestService(new TestCaseRepository());
    static TestCaseRequest request = new TestCaseRequest();

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

//    @Test
//    void test01_AssertThatReturningValueNotNullWhenRequestIsCorrect() throws TestCreateException {
//        TestCase testCase = new TestCase();
//        testCase.setId(UUID.randomUUID());
//        testCase.setName("Test");
//        testCase.setTestCaseRequest(request);
//        TestCase savedTestCase = testService.save(testCase);
//        assertThat(savedTestCase).isNotNull();
//    }
}
