package com.labconnect.service.testResult;

import com.labconnect.DTORequest.testResult.ResultAuthorizationRequestDTO;
import com.labconnect.DTOResponse.testResult.TestResultResponseDTO;
import com.labconnect.Enum.Flag;
import com.labconnect.models.Identity.User;
import com.labconnect.models.testCatalog.TestParameter;
import com.labconnect.models.testResult.TestResult;
import com.labconnect.models.workFlow.TestWorkFlow;
import com.labconnect.repository.Identity.UserRepository;
import com.labconnect.repository.orderSpecimen.LabOrderRepository;
import com.labconnect.repository.testCatalog.TestParameterRepository;
import com.labconnect.repository.testResult.ResultAuthorizationRepository;
import com.labconnect.repository.testResult.TestResultRepository;
import com.labconnect.repository.workFlow.TestWorkFlowRepository;
import com.labconnect.services.testResult.ResultService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ResultServiceTest {

    // --- All dependencies the service constructor requires ---
    @Mock private TestResultRepository testResultRepo;
    @Mock private ResultAuthorizationRepository authorizationRepo;
    @Mock private TestWorkFlowRepository workflowRepo;
    @Mock private TestParameterRepository parameterRepo;
    @Mock private UserRepository userRepo;
    @Mock private LabOrderRepository orderRepo;

    @InjectMocks
    private ResultService resultService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTestResult_Success() {
        // Arrange: build a TestResult with minimal non-null associations used by toResponseDTO
        TestWorkFlow wf = new TestWorkFlow();
        wf.setWorkflowID(10L);

        TestParameter param = new TestParameter();
        param.setParameterId(20L);

        User enteredBy = new User();
        enteredBy.setUserId(30L);

        TestResult result = new TestResult();
        result.setResultId(1L);
        result.setWorkflow(wf);                // needed by dto.setWorkflowId(...)
        result.setParameter(param);            // needed by dto.setParameterId(...)
        result.setValue("98.6");               // asserted
        result.setFlag(Flag.NORMAL);           // mapped via .name()
        result.setEnteredBy(enteredBy);        // needed by dto.setEnteredBy(...)
        result.setEnteredDate(LocalDateTime.of(2026, 3, 1, 9, 0));

        when(testResultRepo.findById(1L)).thenReturn(Optional.of(result));

        // Act: call the service (returns DTO, not entity)
        TestResultResponseDTO dto = resultService.getTestResult(1L);

        // Assert: verify DTO content
        assertNotNull(dto);
        assertEquals(1L, dto.getResultId());
        assertEquals(10L, dto.getWorkflowId());
        assertEquals(20L, dto.getParameterId());
        assertEquals("98.6", dto.getValue());
        assertEquals("NORMAL", dto.getFlag());
        assertEquals(30L, dto.getEnteredBy());

        verify(testResultRepo, times(1)).findById(1L);
    }

    @Test
    void testAuthorizeResult_FailureWhenResultNotFound() {
        // Arrange: result lookup will fail first; other mocks are irrelevant for this test
        when(testResultRepo.findById(99L)).thenReturn(Optional.empty());

        ResultAuthorizationRequestDTO req = new ResultAuthorizationRequestDTO();
        req.setResultId(99L); // non-existent
        // orderId/pathologistId/authorizedDate/remarks not needed because it should fail before accessing them

        // Act + Assert: service throws RuntimeException("Result not found")
        RuntimeException ex = assertThrows(RuntimeException.class, () -> resultService.authorizeResult(req));
        assertEquals("Result not found", ex.getMessage());

        verify(testResultRepo, times(1)).findById(99L);
        verifyNoInteractions(authorizationRepo);
    }
}