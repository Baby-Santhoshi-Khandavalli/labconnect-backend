package com.labconnect.services.testCatalog;
import com.labconnect.DTORequest.testCatalog.TestParameterRequest;
import com.labconnect.DTOResponse.testCatalog.TestParameterResponse;
import com.labconnect.mapper.testCatalog.TestParameterMapper;
import com.labconnect.models.testCatalog.Test;
import com.labconnect.models.testCatalog.TestParameter;
import com.labconnect.repository.testCatalog.TestParameterRepository;
import com.labconnect.repository.testCatalog.TestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class TestParameterService {

    private static final Logger log = LoggerFactory.getLogger(com.labconnect.services.testCatalog.TestParameterService.class);

    private final TestParameterRepository testParameterRepository;
    private final TestRepository testRepository;
    private final TestParameterMapper mapper;

    public TestParameterService(TestParameterRepository testParameterRepository,
                                TestRepository testRepository,
                                TestParameterMapper mapper) {
        this.testParameterRepository = testParameterRepository;
        this.testRepository = testRepository;
        this.mapper = mapper;
    }

    @Transactional
    public TestParameterResponse addParameterToTest(TestParameterRequest request) {
        if (request.getTestId() == null) {
            throw new IllegalArgumentException("testId is required");
        }

        Test test = testRepository.findById(request.getTestId())
                .orElseThrow(() -> new RuntimeException("Test not found"));

        TestParameter saved = testParameterRepository.save(mapper.toEntity(request, test));
        return mapper.toResponse(saved);
    }

    @Transactional
    public TestParameterResponse updateParameter(Long parameterId, TestParameterRequest request) {
        TestParameter entity = testParameterRepository.findById(parameterId)
                .orElseThrow(() -> new RuntimeException("Parameter not found"));

        // If caller wants to re-link parameter to a different Test
        if (request.getTestId() != null) {
            Test newTest = testRepository.findById(request.getTestId())
                    .orElseThrow(() -> new RuntimeException("Test not found for re-link"));
            entity.setTest(newTest);
        }

        mapper.updateEntity(request, entity);
        TestParameter saved = testParameterRepository.save(entity);
        return mapper.toResponse(saved);
    }

//    @Transactional(readOnly = true)
//    public List<TestParameterResponse> getParametersByTestId(Long testId) {
//        return testParameterRepository.findByTest_TestId(testId)
//                .stream()
//                .map(mapper::toResponse)
//                .toList();
//    }

    @Transactional(readOnly = true)
    public List<TestParameterResponse> getParametersByTestId(Long testId) {
        // checks whether test id is contains parameters
        if (!testRepository.existsById(testId)) {
            throw new RuntimeException("Cannot fetch parameters: Test ID " + testId + " not found.");
        }


        return testParameterRepository.findByTest_TestId(testId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Transactional
    public void deleteParameter(Long parameterId) {
        log.info("Deleting parameter with ID: {}", parameterId);
        testParameterRepository.deleteById(parameterId);
        log.info("Parameter {} deleted successfully", parameterId);
    }

}

