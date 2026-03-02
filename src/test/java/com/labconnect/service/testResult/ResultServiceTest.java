package com.labconnect.service.testResult;

import com.labconnect.models.testResult.TestResult;
import com.labconnect.repository.testResult.ResultAuthorizationRepository;
import com.labconnect.repository.testResult.TestResultRepository;
import com.labconnect.services.testResult.ResultService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

public class ResultServiceTest {

    @Mock
    private TestResultRepository testResultRepo;

    @Mock
    private ResultAuthorizationRepository authorizationRepo;

    @InjectMocks
    private ResultService resultService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTestResult_Success() {
        TestResult result = new TestResult();
        result.setResultId(1L);
        result.setValue("98.6");

        when(testResultRepo.findById(1L)).thenReturn(Optional.of(result));

        TestResult found = resultService.getTestResult(1L);

        assertNotNull(found);
        assertEquals("98.6", found.getValue());
        verify(testResultRepo, times(1)).findById(1L);
    }

    @Test
    void testAuthorizeResult_FailureWhenResultNotFound() {
        when(testResultRepo.existsById(99L)).thenReturn(false);

        var auth = resultService.authorizeResult(99L, null);

        assertNull(auth);
        verify(authorizationRepo, never()).save(any());
    }
}