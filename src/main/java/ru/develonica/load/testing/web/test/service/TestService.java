package ru.develonica.load.testing.web.test.service;

import org.springframework.stereotype.Service;
import ru.develonica.load.testing.web.test.model.exception.TestCreateException;
import ru.develonica.load.testing.web.test.model.request.TestCreateRequest;
import ru.develonica.load.testing.web.test.model.request.validator.TestRequestValidator;

import java.util.UUID;

@Service
public class TestService {

    public UUID createTestTask(TestCreateRequest testCreateRequest) throws TestCreateException {
        if (TestRequestValidator.validate(testCreateRequest)) {
            return UUID.randomUUID();
        } else {
            throw new TestCreateException(new TestCreateException("URL is malformed"));
        }

    }
}
