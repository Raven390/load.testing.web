package ru.develonica.load.testing.web.test.model.dto;

import ru.develonica.load.testing.web.test.model.request.TestCaseRequest;

import java.util.UUID;

/**
 * DTO сущности TestCase
 */
public class TestCaseDto {
    private UUID id;
    private String name;
    private TestCaseRequest testCaseRequest;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public TestCaseRequest getTestCaseRequest() {
        return testCaseRequest;
    }

    public void setTestCaseRequest(TestCaseRequest testCaseRequest) {
        this.testCaseRequest = testCaseRequest;
    }
}
