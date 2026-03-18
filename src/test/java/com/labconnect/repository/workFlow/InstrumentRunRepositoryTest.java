package com.labconnect.repository.workFlow;

import com.labconnect.Enum.RunStatus;
import com.labconnect.Enum.Status;
import com.labconnect.models.workFlow.InstrumentRun;
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
        "com.labconnect.repository.workFlow",
        "com.labconnect.repository.testCatalog"
})
@EntityScan(basePackages = {
        "com.labconnect.models.workFlow",
        "com.labconnect.models.testCatalog"
})
class InstrumentRunRepositoryTest {

    @Autowired
    private InstrumentRunRepository instrumentRunRepository;

    @Autowired
    private TestRepository testRepository;

    // Use fully qualified name to avoid clash with JUnit's @Test annotation
    private com.labconnect.models.testCatalog.Test testEntity;

    @BeforeEach
    void setUp() {
        testEntity = new com.labconnect.models.testCatalog.Test();
        testEntity.setName("Blood Test");
        testEntity.setStatus(Status.Active);
        testRepository.save(testEntity);

        InstrumentRun run1 = new InstrumentRun();
        run1.setTest(testEntity);
        run1.setInstrumentName("Analyzer A");
        run1.setRunDate(new Date());
        run1.setStatus(RunStatus.PASSED);
        instrumentRunRepository.save(run1);

        InstrumentRun run2 = new InstrumentRun();
        run2.setTest(testEntity);
        run2.setInstrumentName("Analyzer B");
        run2.setRunDate(new Date());
        run2.setStatus(RunStatus.FAILED);
        instrumentRunRepository.save(run2);
    }

    @Test
    void testFindByInstrumentName() {
        List<InstrumentRun> runs = instrumentRunRepository.findByInstrumentName("Analyzer A");
        assertThat(runs).hasSize(1);
        assertThat(runs.get(0).getStatus()).isEqualTo(RunStatus.PASSED);
    }

    @Test
    void testFindByStatus() {
        List<InstrumentRun> runs = instrumentRunRepository.findByStatus(RunStatus.FAILED);
        assertThat(runs).hasSize(1);
        assertThat(runs.get(0).getInstrumentName()).isEqualTo("Analyzer B");
    }
}
