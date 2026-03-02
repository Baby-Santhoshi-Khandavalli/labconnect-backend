package com.labconnect.services.testResult;

import com.labconnect.DTORequest.TestResultRequestDTO;
import com.labconnect.DTOResponse.TestResultResponseDTO;
import com.labconnect.Enum.Flag;
import com.labconnect.models.ResultAuthorization;
import com.labconnect.models.TestResult;
import com.labconnect.repository.ResultAuthorizationRepository;
import com.labconnect.repository.TestResultRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
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