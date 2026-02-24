package labconnect.controller;

import com.labconnect.models.ResultAuthorization;
import com.labconnect.models.TestResult;
import com.labconnect.services.ResultService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

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
        // Initializes @Mock and injects them into @InjectMocks
        MockitoAnnotations.openMocks(this);
    }

    // --- TestResult Method Tests ---

    @Test
    public void testGetAllTestResults_Success() {
        // Arrange
        List<TestResult> results = new ArrayList<>();
        TestResult tr = new TestResult();
        tr.setValue("12.5");
        results.add(tr);

        when(resultService.getAllTestResults()).thenReturn(results);

        // Act
        List<TestResult> response = resultController.getAllTestResults();

        // Assert
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals("12.5", response.get(0).getValue());
        verify(resultService, times(1)).getAllTestResults();
    }

    @Test
    public void testCreateTestResult_Success() {
        // Arrange
        TestResult input = new TestResult();
        input.setValue("Negative");

        when(resultService.saveTestResult(any(TestResult.class))).thenReturn(input);

        // Act
        TestResult created = resultController.createTestResult(input);

        // Assert
        Assertions.assertNotNull(created);
        Assertions.assertEquals("Negative", created.getValue());
        verify(resultService, times(1)).saveTestResult(input);
    }

    @Test
    public void testDeleteTestResult_Success() {
        // Arrange
        Long id = 10L;
        doNothing().when(resultService).deleteTestResult(id);

        // Act
        resultController.deleteTestResult(id);

        // Assert
        verify(resultService, times(1)).deleteTestResult(id);
    }

    // --- ResultAuthorization Method Tests ---

    @Test
    public void testGetAuthorization_Success() {
        // Arrange
        ResultAuthorization auth = new ResultAuthorization();
        auth.setRemarks("Verified");

        when(resultService.getAuthorization(1L)).thenReturn(auth);

        // Act
        ResultAuthorization response = resultController.getAuthorization(1L);

        // Assert
        Assertions.assertEquals("Verified", response.getRemarks());
        verify(resultService, times(1)).getAuthorization(1L);
    }

    // --- Combined Workflow Test ---

    @Test
    public void testAuthorizeResult_Workflow() {
        // Arrange
        Long resultId = 5L;
        ResultAuthorization auth = new ResultAuthorization();
        auth.setRemarks("Authorized through Controller");

        when(resultService.authorizeResult(eq(resultId), any(ResultAuthorization.class)))
                .thenReturn(auth);

        // Act
        ResultAuthorization result = resultController.authorizeResult(resultId, auth);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Authorized through Controller", result.getRemarks());
        verify(resultService, times(1)).authorizeResult(eq(resultId), any(ResultAuthorization.class));
    }
}
