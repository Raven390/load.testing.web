package ru.develonica.load.testing.web.test.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.develonica.load.testing.web.test.model.exception.TestCreateException;
import ru.develonica.load.testing.web.test.model.request.TestCreateRequest;
import ru.develonica.load.testing.web.test.model.request.validator.TestRequestValidator;
import ru.develonica.load.testing.web.test.service.TestService;

import java.util.UUID;

@Controller
@RequestMapping("/test")
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createRequest(@RequestBody TestCreateRequest testCreateRequest) {
        if (TestRequestValidator.validate(testCreateRequest)) {
            try {
                UUID createdTestTaskId = testService.createTestTask(testCreateRequest);
                return ResponseEntity.ok().body(createdTestTaskId.toString());
            } catch (TestCreateException e) {
                return ResponseEntity.internalServerError().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
}
