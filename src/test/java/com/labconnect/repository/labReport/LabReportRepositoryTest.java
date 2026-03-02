package com.labconnect.repository.labReport;

import com.labconnect.models.LabReport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

@DataJpaTest
public class LabReportRepositoryTest {

    @Autowired
    private LabReportRepository labReportRepository;

    @Test
    public void testSaveAndFindReport() {
        // Arrange
        LabReport report = new LabReport();
        report.setScope("Hematology");
        report.setMetrics("{\"white_cell_count\": \"Normal\"}");
        report.setGeneratedDate(LocalDateTime.now());

        // Act
        LabReport savedReport = labReportRepository.save(report);
        LabReport found = labReportRepository.findById(savedReport.getReportId()).orElse(null);

        // Assert
        Assertions.assertNotNull(found);
        Assertions.assertEquals("Hematology", found.getScope());
    }
}