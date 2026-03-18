package com.labconnect.repository.qualityControl;

import com.labconnect.Enum.QCStatus;
import com.labconnect.Enum.Status;
import com.labconnect.models.qualityControl.QualityControl;
import com.labconnect.repository.testCatalog.TestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test; // JUnit annotation
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@EnableJpaRepositories(basePackages = {
        "com.labconnect.repository.qualityControl",
        "com.labconnect.repository.testCatalog"
})
@EntityScan(basePackages = {
        "com.labconnect.models.qualityControl",
        "com.labconnect.models.testCatalog"
})
class QualityControlRepositoryTest {

    @Autowired
    private QualityControlRepository repository;

    @Autowired
    private TestRepository testRepository;

    private com.labconnect.models.testCatalog.Test testEntity;

    @BeforeEach
    void setUp() {
        // Create and save a Test entity
        testEntity = new com.labconnect.models.testCatalog.Test();
        testEntity.setName("QC Blood Test");
        testEntity.setStatus(Status.Active);
        testRepository.save(testEntity);

        // Create and save QualityControl records
        QualityControl qc1 = new QualityControl();
        qc1.setTest(testEntity);
        qc1.setStatus(QCStatus.PASS);
        qc1.setRunDate(new Date());
        repository.save(qc1);

        QualityControl qc2 = new QualityControl();
        qc2.setTest(testEntity);
        qc2.setStatus(QCStatus.FAIL);
        qc2.setRunDate(new Date());
        repository.save(qc2);
    }

    @Test
    void testFindByStatus() {
        List<QualityControl> results = repository.findByStatus(QCStatus.PASS);
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getStatus()).isEqualTo(QCStatus.PASS);
    }

    @Test
    void testFindByTestId() {
        List<QualityControl> results = repository.findByTest_TestId(testEntity.getTestId());
        assertThat(results).hasSize(2);
    }

    @Test
    void testFindByRunDateBetween() {
        Date start = new Date(System.currentTimeMillis() - 1000 * 60 * 60);
        Date end = new Date(System.currentTimeMillis() + 1000 * 60 * 60);
        List<QualityControl> results = repository.findByRunDateBetween(start, end);
        assertThat(results).hasSize(2);
    }
}

