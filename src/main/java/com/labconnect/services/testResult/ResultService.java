package com.labconnect.services.testResult;

import com.labconnect.DTORequest.testResult.ResultAuthorizationRequestDTO;
import com.labconnect.DTORequest.testResult.TestResultRequestDTO;
import com.labconnect.DTOResponse.testResult.ResultAuthorizationResponseDTO;
import com.labconnect.DTOResponse.testResult.TestResultResponseDTO;
import com.labconnect.Enum.Flag;

import com.labconnect.models.Identity.User;
import com.labconnect.models.orderSpecimen.LabOrder;
import com.labconnect.models.testCatalog.TestParameter;
import com.labconnect.models.testResult.ResultAuthorization;
import com.labconnect.models.testResult.TestResult;
import com.labconnect.models.workFlow.TestWorkFlow;
//import com.labconnect.repository.*;
import com.labconnect.repository.Identity.UserRepository;
import com.labconnect.repository.orderSpecimen.LabOrderRepository;
import com.labconnect.repository.testCatalog.TestParameterRepository;
import com.labconnect.repository.testResult.ResultAuthorizationRepository;
import com.labconnect.repository.testResult.TestResultRepository;
import com.labconnect.repository.workFlow.TestWorkFlowRepository;
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
    private final TestWorkFlowRepository workflowRepo;
    private final TestParameterRepository parameterRepo;
    private final UserRepository userRepo;
    private final LabOrderRepository orderRepo;

    public ResultService(TestResultRepository testResultRepo,
                         ResultAuthorizationRepository authorizationRepo,
                         TestWorkFlowRepository workflowRepo,
                         TestParameterRepository parameterRepo,
                         UserRepository userRepo,
                         LabOrderRepository orderRepo) {
        this.testResultRepo = testResultRepo;
        this.authorizationRepo = authorizationRepo;
        this.workflowRepo = workflowRepo;
        this.parameterRepo = parameterRepo;
        this.userRepo = userRepo;
        this.orderRepo = orderRepo;
    }

    // --- TestResult methods ---
    public List<TestResultResponseDTO> getAllTestResults() {
        return testResultRepo.findAll().stream().map(this::toResponseDTO).toList();
    }

    public TestResultResponseDTO getTestResult(Long id) {
        return testResultRepo.findById(id).map(this::toResponseDTO).orElse(null);
    }

    public TestResultResponseDTO saveTestResult(TestResultRequestDTO dto) {
        TestWorkFlow workflow = workflowRepo.findById(dto.getWorkflowId())
                .orElseThrow(() -> new RuntimeException("Workflow not found"));
        TestParameter parameter = parameterRepo.findById(dto.getParameterId())
                .orElseThrow(() -> new RuntimeException("Parameter not found"));
        User user = userRepo.findById(dto.getEnteredBy())
                .orElseThrow(() -> new RuntimeException("User not found"));

        TestResult result = new TestResult();
        result.setWorkflow(workflow);
        result.setParameter(parameter);
        result.setValue(dto.getValue());
        result.setFlag(Flag.valueOf(dto.getFlag()));
        result.setEnteredBy(user);
        result.setEnteredDate(dto.getEnteredDate());

        TestResult saved = testResultRepo.save(result);
        return toResponseDTO(saved);
    }

    public TestResultResponseDTO updateInterpretation(Long resultId, String interpretation) {
        TestResult result = testResultRepo.findById(resultId)
                .orElseThrow(() -> new RuntimeException("Result not found"));

        result.setInterpretation(interpretation);

        TestResult saved = testResultRepo.save(result);
        return toResponseDTO(saved);
    }

    public void deleteTestResult(Long id) {
        testResultRepo.deleteById(id);
    }

    // --- Authorization methods ---
    public ResultAuthorizationResponseDTO authorizeResult(ResultAuthorizationRequestDTO dto) {
        TestResult result = testResultRepo.findById(dto.getResultId())
                .orElseThrow(() -> new RuntimeException("Result not found"));
        LabOrder order = orderRepo.findById(dto.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));
        User pathologist = userRepo.findById(dto.getPathologistId())
                .orElseThrow(() -> new RuntimeException("Pathologist not found"));

        ResultAuthorization authorization = new ResultAuthorization();
        authorization.setTestResult(result);
        authorization.setOrder(order);
        authorization.setPathologist(pathologist);
        authorization.setAuthorizedDate(dto.getAuthorizedDate());
        authorization.setRemarks(dto.getRemarks());

        ResultAuthorization saved = authorizationRepo.save(authorization);
        return toAuthorizationResponseDTO(saved);
    }

    public List<ResultAuthorizationResponseDTO> getAllAuthorizations() {
        return authorizationRepo.findAll().stream()
                .map(this::toAuthorizationResponseDTO)
                .toList();
    }

    // --- Helper mapping ---
    private TestResultResponseDTO toResponseDTO(TestResult result) {
        TestResultResponseDTO dto = new TestResultResponseDTO();
        dto.setResultId(result.getResultId());

        // Using the workflow to reach the LabOrder
        if (result.getWorkflow() != null && result.getWorkflow().getOrder() != null) {
            LabOrder order = result.getWorkflow().getOrder();

            dto.setOrderId(order.getOrderId());
            // Use getPatientId() because your LabOrder uses 'private Long patientId'
            dto.setPatientId(order.getPatientId());
        }

        dto.setWorkflowId(result.getWorkflow().getWorkflowID());
        dto.setParameterId(result.getParameter().getParameterId());
        dto.setValue(result.getValue());
        dto.setFlag(result.getFlag().name());
        dto.setEnteredBy(result.getEnteredBy().getUserId());
        dto.setEnteredDate(result.getEnteredDate());
        dto.setInterpretation(result.getInterpretation()); // Ensure this is set!

        return dto;
    }

    private ResultAuthorizationResponseDTO toAuthorizationResponseDTO(ResultAuthorization auth) {
        ResultAuthorizationResponseDTO dto = new ResultAuthorizationResponseDTO();
        dto.setAuthorizationId(auth.getAuthorizationId());
        dto.setOrderId(auth.getOrder().getOrderId());
        dto.setResultId(auth.getTestResult().getResultId());
        dto.setPathologistId(auth.getPathologist() != null ? auth.getPathologist().getUserId() : null);
        dto.setAuthorizedDate(auth.getAuthorizedDate());
        dto.setRemarks(auth.getRemarks());
        return dto;
    }
}
