package com.labconnect.repository.workFlow;


import com.labconnect.Enum.RunStatus;
import com.labconnect.models.workFlow.InstrumentRun;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstrumentRunRepository extends JpaRepository<InstrumentRun, Long> {
    List<InstrumentRun> findByTest_TestId(Long testId);
    List<InstrumentRun> findByInstrumentName(String instrumentName);

    // ✅ Add this if you want to query by status
    List<InstrumentRun> findByStatus(RunStatus status);
}
