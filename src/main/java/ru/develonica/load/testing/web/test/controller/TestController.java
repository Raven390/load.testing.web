package ru.develonica.load.testing.web.test.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.develonica.load.testing.web.test.model.dto.TestCaseDto;
import ru.develonica.load.testing.web.test.model.exception.EntityNotFoundException;
import ru.develonica.load.testing.web.test.model.mapper.TestCaseMapper;
import ru.develonica.load.testing.web.test.model.object.TestCase;
import ru.develonica.load.testing.web.test.model.request.validator.TestRequestValidator;
import ru.develonica.load.testing.web.test.service.TestService;

import java.security.Principal;
import java.util.List;
import java.util.UUID;


/**
 * Контроллер для работы с TestCase
 */
@Controller
@RequestMapping("/test")
public class TestController {

    private final TestService testService;
    private final TestCaseMapper testCaseMapper;

    public TestController(TestService testService, TestCaseMapper testCaseMapper) {
        this.testService = testService;
        this.testCaseMapper = testCaseMapper;
    }

    @GetMapping("/")
    public ResponseEntity<List<TestCaseDto>> getAllByUserId(Principal principal) {
        List<TestCase> testCaseList = testService.getAllByUserId(UUID.fromString(principal.getName()));
        List<TestCaseDto> testCaseDtoList = testCaseMapper.testCaseListToDtoList(testCaseList);
        return ResponseEntity.ok().body(testCaseDtoList);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<TestCaseDto> get(@PathVariable UUID id){
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
        UUID userId = UUID.fromString(principal.getName());
        if (TestRequestValidator.validate(testCase.getTestCaseRequest())) {
            TestCase savedTestCase = testService.save(testCase, userId);
            TestCaseDto result = testCaseMapper.testCaseToDto(savedTestCase);
            return ResponseEntity.ok().body(result);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<UUID> delete(@PathVariable UUID id) {
        try {
            testService.delete(id);
            return ResponseEntity.ok().body(id);
        } catch (EntityNotFoundException enfe) {
            return ResponseEntity.notFound().build();
        }

    }
}
