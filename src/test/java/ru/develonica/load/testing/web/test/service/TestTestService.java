package ru.develonica.load.testing.web.test.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.develonica.load.testing.web.test.model.exception.TestCreateException;
import ru.develonica.load.testing.web.test.model.request.TestCreateRequest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class TestTestService {

    static TestService testService;
    static TestCreateRequest testCreateRequest = new TestCreateRequest();

    @BeforeAll
    static void setup() {
        testService = new TestService();
    }

    @Test
    void test01_AssertThatReturningUUIDisNotNull() throws TestCreateException {
            UUID testTaskId = testService.createTestTask(testCreateRequest);
            assertThat(testTaskId).isNotNull();
    }
}
