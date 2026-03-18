package com.labconnect.repository.qualityControl;

import com.labconnect.models.qualityControl.ComplianceRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@EnableJpaRepositories(basePackages = "com.labconnect.repository.qualityControl")
@EntityScan(basePackages = "com.labconnect.models.qualityControl")
class ComplianceRecordRepositoryTest {

    @Autowired
    private ComplianceRecordRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testSaveAndFindByTestId() {
        ComplianceRecord record = new ComplianceRecord();
        record.setTestId(123L);
        record.setAuditType("Calibration");
        record.setNotes("Instrument recalibrated");
        record.setLoggedDate(LocalDateTime.now());

        entityManager.persist(record);
        entityManager.flush();

        List<ComplianceRecord> results = repository.findByTestId(123L);
        assertEquals(1, results.size());
        assertEquals("Calibration", results.get(0).getAuditType());
    }

    @Test
    void testFindByAuditType() {
        ComplianceRecord record1 = new ComplianceRecord();
        record1.setTestId(456L);
        record1.setAuditType("Internal");
        record1.setNotes("Internal audit completed");
        record1.setLoggedDate(LocalDateTime.now());

        ComplianceRecord record2 = new ComplianceRecord();
        record2.setTestId(789L);
        record2.setAuditType("External");
        record2.setNotes("External audit completed");
        record2.setLoggedDate(LocalDateTime.now());

        entityManager.persist(record1);
        entityManager.persist(record2);
        entityManager.flush();

        List<ComplianceRecord> results = repository.findByAuditType("Internal");
        assertEquals(1, results.size());
        assertEquals(456L, results.get(0).getTestId());
    }
}



