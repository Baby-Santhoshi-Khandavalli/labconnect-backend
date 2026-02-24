
package labconnect.services;

import com.labconnect.models.LabReport;
import com.labconnect.repository.LabReportRepository;
import com.labconnect.Exception.ReportNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class DashboardServiceTest {

    @Autowired
    private DashboardService dashboardService;

    @MockitoBean // Modern Spring 3.4+ / 4.0 approach
    private LabReportRepository labReportRepository;

    @Test
    public void testCreateReport_Success() {
        // Arrange
        LabReport report = new LabReport();
        report.setScope("Biochemistry");
        Mockito.when(labReportRepository.save(any(LabReport.class))).thenReturn(report);

        // Act
        LabReport result = dashboardService.createReport(report);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Biochemistry", result.getScope());
        Mockito.verify(labReportRepository, Mockito.times(1)).save(any());
    }

    @Test
    public void testUpdateReport_ThrowsException() {
        // Arrange
        Mockito.when(labReportRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(RuntimeException.class, () -> {
            dashboardService.updateReport(99L, new LabReport());
        });
    }
}