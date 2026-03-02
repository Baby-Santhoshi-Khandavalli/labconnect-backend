//package com.labconnect.repository;
//
//import com.labconnect.models.ComplianceRecord;
//import com.labconnect.repository.ComplianceRecordRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
//// CORRECT
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@DataJpaTest
//class ComplianceRecordRepositoryTest {
//
//    @Autowired
//    private ComplianceRecordRepository repository;
//
//    @Test
//    void testSaveAndFindByTestId() {
//        ComplianceRecord record = new ComplianceRecord();
//        record.setTestId(123L);
//        record.setAuditType("Calibration");
//        record.setNotes("Instrument recalibrated");
//        record.setLoggedDate(LocalDateTime.now());
//
//        repository.save(record);
//
//        List<ComplianceRecord> results = repository.findByTestId(123L);
//        assertEquals(1, results.size());
//        assertEquals("Calibration", results.get(0).getAuditType());
//    }
//}
