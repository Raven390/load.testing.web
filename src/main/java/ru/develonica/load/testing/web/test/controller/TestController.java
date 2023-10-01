package ru.develonica.load.testing.web.test.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.Metric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.develonica.load.testing.common.model.generated.Metrics;
import ru.develonica.load.testing.common.model.generated.TimeBasedMetric;
import ru.develonica.load.testing.web.test.model.dto.MetricsDto;
import ru.develonica.load.testing.web.test.model.dto.TestCaseDto;
import ru.develonica.load.testing.web.test.model.exception.EntityNotFoundException;
import ru.develonica.load.testing.web.test.model.exception.LoadTestingStartException;
import ru.develonica.load.testing.web.test.model.mapper.TestCaseMapper;
import ru.develonica.load.testing.web.test.model.object.TestCase;
import ru.develonica.load.testing.web.test.model.request.LoadTestingStartParams;
import ru.develonica.load.testing.web.test.model.request.validator.TestCaseValidator;
import ru.develonica.load.testing.web.test.model.response.ApiResponse;
import ru.develonica.load.testing.web.test.service.TestService;

import java.security.Principal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * Контроллер для работы с TestCase
 */
@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/test")
public class TestController {

    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);
    private final TestService testService;
    private final TestCaseMapper testCaseMapper;
    private final ObjectMapper objectMapper;

    public TestController(TestService testService, TestCaseMapper testCaseMapper, ObjectMapper objectMapper) {
        this.testService = testService;
        this.testCaseMapper = testCaseMapper;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAllByUserId(Principal principal) {
        LOG.info("Request to /");
        List<TestCase> testCaseList = testService.getAllByUserId(UUID.fromString(principal.getName()));
        List<TestCaseDto> testCaseDtoList = testCaseMapper.testCaseListToDtoList(testCaseList);
        ApiResponse apiResponse = new ApiResponse(testCaseDtoList);
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<TestCaseDto> get(@PathVariable UUID id) {
        try {
            TestCase testCase = testService.get(id);
            TestCaseDto testCaseDto = testCaseMapper.testCaseToDto(testCase);
            return ResponseEntity.ok().body(testCaseDto);
        } catch (EntityNotFoundException enfe) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/save")
    public ResponseEntity<TestCaseDto> save(@RequestBody TestCase testCase, Principal principal) throws JsonProcessingException {
        LOG.info("Request to /save with body {}", objectMapper.writeValueAsString(testCase));
        UUID userId = UUID.fromString(principal.getName());
        if (TestCaseValidator.validate(testCase)) {
            TestCase savedTestCase = testService.save(testCase, userId);
            TestCaseDto result = testCaseMapper.testCaseToDto(savedTestCase);
            return ResponseEntity.ok().body(result);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable UUID id) {
        LOG.info("Request to /delete/{}", id);
        try {
            testService.delete(id);
            return ResponseEntity.ok().body(new ApiResponse(id));
        } catch (EntityNotFoundException enfe) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/start/{id}")
    public ResponseEntity<ApiResponse> start(@PathVariable UUID id, @RequestBody LoadTestingStartParams params) throws LoadTestingStartException, EntityNotFoundException {
//        testService.start(id, params);
        testService.start(id, params);
        return ResponseEntity.ok().body(new ApiResponse(id));
    }

    @GetMapping("{id}/metrics")
    public ResponseEntity<ApiResponse> getMetrics(@PathVariable UUID id) throws LoadTestingStartException {
        Metrics metrics = testService.getMetrics(id);
        return ResponseEntity.ok().body(new ApiResponse(new MetricsDto(metrics)));
    }
}
