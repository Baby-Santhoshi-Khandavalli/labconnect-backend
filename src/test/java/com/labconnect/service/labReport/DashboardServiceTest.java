package com.labconnect.service.labReport;

import com.labconnect.models.labReport.LabReport;
import com.labconnect.repository.labReport.LabReportRepository;
import com.labconnect.services.labReport.DashboardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @Mock
    private LabReportRepository labReportRepository;

    @InjectMocks
    private DashboardService dashboardService;

    @Test
    void createReport_shouldPersistAndReturnSavedEntity() {
        // Arrange
        LabReport input = LabReport.builder()
                .scope("Hematology")
                .metrics("{\"white_cell_count\":\"Normal\"}")
                .generatedDate(LocalDateTime.now().withNano(0))
                .build();

        LabReport saved = LabReport.builder()
                .reportId(100L)
                .scope(input.getScope())
                .metrics(input.getMetrics())
                .generatedDate(input.getGeneratedDate())
                .build();

        when(labReportRepository.save(any(LabReport.class))).thenReturn(saved);

        // Act
        LabReport result = dashboardService.createReport(input);

        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals(100L, result.getReportId(), "Report ID should be from the saved entity");
        assertEquals("Hematology", result.getScope(), "Scope should match");
        assertEquals("{\"white_cell_count\":\"Normal\"}", result.getMetrics(), "Metrics should match");
        assertEquals(input.getGeneratedDate(), result.getGeneratedDate(), "Generated date should match");

        // Verify repository interaction and captured argument
        ArgumentCaptor<LabReport> captor = ArgumentCaptor.forClass(LabReport.class);
        verify(labReportRepository, times(1)).save(captor.capture());
        LabReport passedToRepo = captor.getValue();
        assertEquals(input.getScope(), passedToRepo.getScope(), "Service should pass the same scope to repo");
        assertEquals(input.getMetrics(), passedToRepo.getMetrics(), "Service should pass the same metrics to repo");
        assertEquals(input.getGeneratedDate(), passedToRepo.getGeneratedDate(), "Service should pass the same date to repo");

        verifyNoMoreInteractions(labReportRepository);
    }
}