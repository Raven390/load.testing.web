package ru.develonica.load.testing.web.test.model.object;

import ru.develonica.load.testing.web.test.model.request.TestCaseRequest;

import java.util.UUID;

/**
 * Сущность "Тестовый кейс". В разработке, атрибуты будут меняться.
 */
public class TestCase {

    private UUID id;

    private String name;


    private TestCaseRequest testCaseRequest;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TestCaseRequest getTestCaseRequest() {
        return testCaseRequest;
    }

    public void setTestCaseRequest(TestCaseRequest testCaseRequest) {
        this.testCaseRequest = testCaseRequest;
    }
}
