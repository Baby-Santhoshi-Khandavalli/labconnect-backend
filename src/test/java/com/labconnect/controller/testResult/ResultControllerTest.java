package com.labconnect.controller.testResult;

import com.labconnect.DTORequest.testResult.ResultAuthorizationRequestDTO;
import com.labconnect.DTORequest.testResult.TestResultRequestDTO;
import com.labconnect.DTOResponse.testResult.ResultAuthorizationResponseDTO;
import com.labconnect.DTOResponse.testResult.TestResultResponseDTO;
import com.labconnect.services.testResult.ResultService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class ResultControllerTest {

    @Mock
    private ResultService resultService;

    @InjectMocks
    private ResultController resultController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // --- TestResult endpoints ---

    @Test
    public void testGetAllResults_Success() {
        // Arrange: service returns a list of DTOs
        List<TestResultResponseDTO> results = new ArrayList<>();
        TestResultResponseDTO dto = new TestResultResponseDTO();
        dto.setResultId(1L);
        dto.setWorkflowId(10L);
        dto.setParameterId(20L);
        dto.setValue("12.5");
        dto.setFlag("NORMAL");
        dto.setEnteredBy(30L);
        dto.setEnteredDate(LocalDateTime.of(2026, 3, 1, 9, 0));
        results.add(dto);

        when(resultService.getAllTestResults()).thenReturn(results);

        // Act: call controller
        List<TestResultResponseDTO> response = resultController.getAllResults();

        // Assert: list is returned as-is
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals(1L, response.get(0).getResultId());
        Assertions.assertEquals("12.5", response.get(0).getValue());
        verify(resultService, times(1)).getAllTestResults();
    }

    @Test
    public void testCreateResult_Success() {
        // Arrange: input request DTO
        TestResultRequestDTO req = new TestResultRequestDTO();
        req.setWorkflowId(10L);
        req.setParameterId(20L);
        req.setValue("Negative");
        req.setFlag("NORMAL");
        req.setEnteredBy(30L);
        req.setEnteredDate(LocalDateTime.of(2026, 3, 1, 10, 30));

        // Service returns response DTO
        TestResultResponseDTO saved = new TestResultResponseDTO();
        saved.setResultId(100L);
        saved.setWorkflowId(10L);
        saved.setParameterId(20L);
        saved.setValue("Negative");
        saved.setFlag("NORMAL");
        saved.setEnteredBy(30L);
        saved.setEnteredDate(req.getEnteredDate());

        when(resultService.saveTestResult(any(TestResultRequestDTO.class))).thenReturn(saved);

        // Act
        ResponseEntity<TestResultResponseDTO> response = resultController.createResult(req);

        // Assert
        Assertions.assertEquals(200, response.getStatusCodeValue());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(100L, response.getBody().getResultId());
        Assertions.assertEquals("Negative", response.getBody().getValue());
        verify(resultService, times(1)).saveTestResult(any(TestResultRequestDTO.class));
    }

    @Test
    public void testGetResult_Found() {
        // Arrange
        Long id = 1L;
        TestResultResponseDTO dto = new TestResultResponseDTO();
        dto.setResultId(id);
        dto.setValue("98.6");

        when(resultService.getTestResult(id)).thenReturn(dto);

        // Act
        ResponseEntity<TestResultResponseDTO> response = resultController.getResult(id);

        // Assert
        Assertions.assertEquals(200, response.getStatusCodeValue());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(id, response.getBody().getResultId());
        Assertions.assertEquals("98.6", response.getBody().getValue());
        verify(resultService, times(1)).getTestResult(id);
    }

    @Test
    public void testGetResult_NotFound() {
        // Arrange
        Long id = 404L;
        when(resultService.getTestResult(id)).thenReturn(null);

        // Act
        ResponseEntity<TestResultResponseDTO> response = resultController.getResult(id);

        // Assert
        Assertions.assertEquals(404, response.getStatusCodeValue());
        Assertions.assertNull(response.getBody());
        verify(resultService, times(1)).getTestResult(id);
    }

    @Test
    public void testDeleteResult_Success() {
        // Arrange
        Long id = 10L;
        doNothing().when(resultService).deleteTestResult(id);

        // Act
        ResponseEntity<Void> response = resultController.deleteResult(id);

        // Assert
        Assertions.assertEquals(204, response.getStatusCodeValue());
        verify(resultService, times(1)).deleteTestResult(id);
    }

    // --- Authorization endpoints ---

    @Test
    public void testAuthorizeResult_Success() {
        // Arrange
        Long pathIdIdOverrideByController = 5L; // Path variable {id} used as resultId
        ResultAuthorizationRequestDTO req = new ResultAuthorizationRequestDTO();
        req.setResultId(999L); // should be overridden by controller to 5L
        req.setOrderId(200L);
        req.setPathologistId(300L);
        req.setAuthorizedDate(LocalDateTime.of(2026, 3, 1, 11, 0));
        req.setRemarks("Authorize via controller");

        ResultAuthorizationResponseDTO resp = new ResultAuthorizationResponseDTO();
        resp.setAuthorizationId(777L);
        resp.setOrderId(200L);
        resp.setResultId(pathIdIdOverrideByController); // expected after controller sets it
        resp.setPathologistId(300L);
        resp.setAuthorizedDate(req.getAuthorizedDate());
        resp.setRemarks("Authorize via controller");

        ArgumentCaptor<ResultAuthorizationRequestDTO> captor = ArgumentCaptor.forClass(ResultAuthorizationRequestDTO.class);

        when(resultService.authorizeResult(any(ResultAuthorizationRequestDTO.class))).thenReturn(resp);

        // Act
        ResponseEntity<ResultAuthorizationResponseDTO> response =
                resultController.authorizeResult(pathIdIdOverrideByController, req);

        // Assert
        Assertions.assertEquals(200, response.getStatusCodeValue());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(777L, response.getBody().getAuthorizationId());
        Assertions.assertEquals(pathIdIdOverrideByController, response.getBody().getResultId());
        Assertions.assertEquals("Authorize via controller", response.getBody().getRemarks());

        // Verify controller set dto.setResultId(id) before delegating
        verify(resultService, times(1)).authorizeResult(captor.capture());
        ResultAuthorizationRequestDTO passed = captor.getValue();
        Assertions.assertEquals(pathIdIdOverrideByController, passed.getResultId());
        Assertions.assertEquals(200L, passed.getOrderId());
        Assertions.assertEquals(300L, passed.getPathologistId());
    }

    @Test
    public void testGetAllAuthorizations_Success() {
        // Arrange
        List<ResultAuthorizationResponseDTO> list = new ArrayList<>();
        ResultAuthorizationResponseDTO a = new ResultAuthorizationResponseDTO();
        a.setAuthorizationId(1L);
        a.setOrderId(200L);
        a.setResultId(100L);
        a.setPathologistId(300L);
        a.setAuthorizedDate(LocalDateTime.of(2026, 3, 1, 12, 0));
        a.setRemarks("Verified");
        list.add(a);

        when(resultService.getAllAuthorizations()).thenReturn(list);

        // Act
        List<ResultAuthorizationResponseDTO> response = resultController.getAllAuthorizations();

        // Assert
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals("Verified", response.get(0).getRemarks());
        verify(resultService, times(1)).getAllAuthorizations();
    }
}
