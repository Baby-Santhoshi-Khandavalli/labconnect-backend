package com.labconnect.repository.workFlow;


import com.labconnect.Enum.RunStatus;
import com.labconnect.models.InstrumentRun;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstrumentRunRepository extends JpaRepository<InstrumentRun, Long> {
    List<InstrumentRun> findByStatus(RunStatus status);
    List<InstrumentRun> findByTest_TestId(Long testId);
    List<InstrumentRun> findByInstrumentName(String instrumentName);
}
