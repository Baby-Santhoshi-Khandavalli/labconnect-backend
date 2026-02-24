package com.labconnect.repository;

import com.labconnect.Enum.RunStatus;
import com.labconnect.models.InstrumentRun;
import com.labconnect.repository.InstrumentRunRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
class InstrumentRunRepositoryTest {

    @Autowired
    private InstrumentRunRepository repository;

    @Test
    void testFindByStatus() {
        InstrumentRun run = new InstrumentRun();
        run.setStatus(RunStatus.PASSED);
        repository.save(run);

        List<InstrumentRun> result = repository.findByStatus(RunStatus.PASSED);

        assertFalse(result.isEmpty());
        assertEquals(RunStatus.PASSED, result.get(0).getStatus());
    }
}
