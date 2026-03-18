package com.labconnect.repository.labReport;

import com.labconnect.models.labReport.LabReport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@EntityScan(basePackageClasses = LabReport.class)                    // isolate to LabReport entity only
@EnableJpaRepositories(basePackageClasses = LabReportRepository.class) // isolate to LabReportRepository only
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) // force embedded DB
@ActiveProfiles("test")                                             // avoid loading 'dev' profile in tests
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop"                 // create schema for the slice test
})
public class LabReportRepositoryTest {

    @Autowired
    private LabReportRepository labReportRepository;

    @Test
    void saveAndFindById_shouldPersistAndRetrieve() {
        // Arrange
        LabReport report = LabReport.builder()
                .scope("Hematology")
                .metrics("{\"white_cell_count\":\"Normal\"}")
                .generatedDate(LocalDateTime.now().withNano(0))     // trim nanos to avoid precision issues
                .build();

        // Act
        LabReport saved = labReportRepository.saveAndFlush(report); // flush to DB so we can read it back
        Optional<LabReport> foundOpt = labReportRepository.findById(saved.getReportId());

        // Assert
        assertTrue(foundOpt.isPresent(), "Saved report should be found by ID");
        LabReport found = foundOpt.get();

        assertNotNull(found.getReportId(), "Report ID should be generated");
        assertEquals("Hematology", found.getScope(), "Scope should match");
        assertEquals("{\"white_cell_count\":\"Normal\"}", found.getMetrics(), "Metrics should match");
        assertNotNull(found.getGeneratedDate(), "Generated date should be persisted");
    }
}