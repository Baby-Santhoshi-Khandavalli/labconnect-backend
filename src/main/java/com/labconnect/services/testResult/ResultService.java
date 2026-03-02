package com.labconnect.services.testResult;
import com.labconnect.models.testResult.ResultAuthorization;
import com.labconnect.models.testResult.TestResult;
import com.labconnect.repository.testResult.ResultAuthorizationRepository;
import com.labconnect.repository.testResult.TestResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ResultService {

    private final TestResultRepository testResultRepo;
    private final ResultAuthorizationRepository authorizationRepo;

    public ResultService(TestResultRepository testResultRepo,
                         ResultAuthorizationRepository authorizationRepo) {
        this.testResultRepo = testResultRepo;
        this.authorizationRepo = authorizationRepo;
    }
    // --- TestResult methods ---
    public List<TestResult> getAllTestResults() {
        return testResultRepo.findAll();
    }

    public TestResult getTestResult(Long id) {
        return testResultRepo.findById(id).orElse(null);
    }

    public TestResult saveTestResult(TestResult result) {
        return testResultRepo.save(result);
    }

    public void deleteTestResult(Long id) {
        testResultRepo.deleteById(id);
    }

    // --- ResultAuthorization methods ---
    public List<ResultAuthorization> getAllAuthorizations() {
        return authorizationRepo.findAll();
    }

    public ResultAuthorization getAuthorization(Long id) {
        return authorizationRepo.findById(id).orElse(null);
    }

    public ResultAuthorization saveAuthorization(ResultAuthorization authorization) {
        return authorizationRepo.save(authorization);
    }

    public void deleteAuthorization(Long id) {
        authorizationRepo.deleteById(id);
    }

    // --- Combined workflow example ---
    public ResultAuthorization authorizeResult(Long resultId, ResultAuthorization authorization) {
        if (testResultRepo.existsById(resultId)) {
            return authorizationRepo.save(authorization);
        }
        return null;
    }
}