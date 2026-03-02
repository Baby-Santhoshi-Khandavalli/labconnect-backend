package com.labconnect.repository.workFlow;

import com.labconnect.Enum.RunStatus;
import com.labconnect.models.workFlow.InstrumentRun;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
class InstrumentRunRepositoryTest {

    @Autowired
    private InstrumentRunRepository repository;

    @Test
    void testFindByStatus() {
        // Arrange
        InstrumentRun run = new InstrumentRun();
        run.setStatus(RunStatus.PASSED);
        repository.saveAndFlush(run);

        // Act
        List<InstrumentRun> result = repository.findByStatus(RunStatus.PASSED);

        // Assert
        assertFalse(result.isEmpty(), "Expected at least one InstrumentRun");
        assertEquals(RunStatus.PASSED, result.get(0).getStatus(), "Status should be PASSED");
    }
}
