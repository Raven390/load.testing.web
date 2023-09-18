package ru.develonica.load.testing.web.test.service;

import org.springframework.stereotype.Service;
import ru.develonica.load.testing.web.test.model.exception.EntityNotFoundException;
import ru.develonica.load.testing.web.test.model.object.TestCase;
import ru.develonica.load.testing.web.test.repository.TestCaseRepository;

import java.util.List;
import java.util.UUID;

@Service
public class TestService {

    private final TestCaseRepository testCaseRepository;

    public TestService(TestCaseRepository testCaseRepository) {
        this.testCaseRepository = testCaseRepository;
    }

    public TestCase save(TestCase testCase) {
        return testCaseRepository.save(testCase);
    }

    public TestCase get(UUID id) {
        return testCaseRepository.getById(id);
    }

    public List<TestCase> getAllByUserId(UUID userId) {
        return testCaseRepository.getAllWhereUserId(userId);
    }

    public void delete(UUID id) throws EntityNotFoundException {
        testCaseRepository.delete(id);
    }
}
