package ru.develonica.load.testing.web.test.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import ru.develonica.load.testing.common.model.generated.Metrics;
import ru.develonica.load.testing.web.test.model.exception.EntityNotFoundException;
import ru.develonica.load.testing.web.test.model.exception.LoadTestingStartException;
import ru.develonica.load.testing.web.test.model.object.TestCase;
import ru.develonica.load.testing.web.test.model.request.LoadTestingStartParams;
import ru.develonica.load.testing.web.test.model.request.TestCaseRequest;
import ru.develonica.load.testing.web.test.repository.TestCaseRepository;

import java.util.List;
import java.util.UUID;

@Service
public class TestService {

    private final TestCaseRepository testCaseRepository;
    private final LoadTestingStartService loadTestingStartService;

    public TestService(TestCaseRepository testCaseRepository, LoadTestingStartService loadTestingStartService) {
        this.testCaseRepository = testCaseRepository;
        this.loadTestingStartService = loadTestingStartService;
    }

    public TestCase save(TestCase testCase, UUID userId) throws JsonProcessingException {
        return testCaseRepository.save(testCase, userId);
    }

    public TestCase get(UUID id) throws EntityNotFoundException {
        return testCaseRepository.getById(id);
    }

    public List<TestCase> getAllByUserId(UUID userId) {
        return testCaseRepository.getAllWhereUserId(userId);
    }

    public void delete(UUID id) throws EntityNotFoundException {
        testCaseRepository.delete(id);
    }

    public String start(UUID id, LoadTestingStartParams params) throws LoadTestingStartException, EntityNotFoundException {
        TestCaseRequest testCaseRequest = testCaseRepository.getById(id).getTestCaseRequest();
        String status = loadTestingStartService.startLoadTestingCase(params, testCaseRequest);
//        TODO: установка статуса в БД testCaseRepository.setStatus(LoadTestingStatus.valueOf(status));
        return status;
    }

    public Metrics getMetrics(UUID id) throws LoadTestingStartException {
        Metrics metrics = loadTestingStartService.getMetrics();
        // TODO: реализовать механизм сохранения актуальных метрик в БД, архивных в ФС
        return metrics;
    }
}
