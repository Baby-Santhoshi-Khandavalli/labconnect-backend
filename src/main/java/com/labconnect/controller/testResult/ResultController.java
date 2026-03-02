package com.labconnect.controller.testResult;

import com.labconnect.models.testResult.ResultAuthorization;
import com.labconnect.models.testResult.TestResult;
import com.labconnect.services.testResult.ResultService;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/results")
public class ResultController {

    private final ResultService service;

    public ResultController(ResultService service) {
        this.service = service;
    }

    // --- TestResult endpoints ---
    @GetMapping("/test-results")
    public List<TestResult> getAllTestResults() {
        return service.getAllTestResults();
    }

    @GetMapping("/test-results/{id}")
    public TestResult getTestResult(@PathVariable Long id) {
        return service.getTestResult(id);
    }

    @PostMapping("/test-results")
    public TestResult createTestResult(@RequestBody TestResult result) {
        return service.saveTestResult(result);
    }

    @DeleteMapping("/test-results/{id}")
    public void deleteTestResult(@PathVariable Long id) {
        service.deleteTestResult(id);
    }

    // --- ResultAuthorization endpoints ---
    @GetMapping("/authorizations")
    public List<ResultAuthorization> getAllAuthorizations() {
        return service.getAllAuthorizations();
    }

    @GetMapping("/authorizations/{id}")
    public ResultAuthorization getAuthorization(@PathVariable Long id) {
        return service.getAuthorization(id);
    }

    @PostMapping("/authorizations")
    public ResultAuthorization createAuthorization(@RequestBody ResultAuthorization authorization) {
        return service.saveAuthorization(authorization);
    }

    @DeleteMapping("/authorizations/{id}")
    public void deleteAuthorization(@PathVariable Long id) {
        service.deleteAuthorization(id);
    }

    // --- Combined workflow ---
    @PostMapping("/test-results/{id}/authorize")
    public ResultAuthorization authorizeResult(@PathVariable Long id,
                                               @RequestBody ResultAuthorization authorization) {
        return service.authorizeResult(id, authorization);
    }
}
