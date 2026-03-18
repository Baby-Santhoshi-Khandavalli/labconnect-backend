package com.labconnect.services.workFlow;

import com.labconnect.Enum.RunStatus;
import com.labconnect.Exception.workFlow.ResourceNotFoundException;
import com.labconnect.models.workFlow.InstrumentRun;
import com.labconnect.repository.workFlow.InstrumentRunRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstrumentRunService {
    private final InstrumentRunRepository repository;

    public InstrumentRunService(InstrumentRunRepository repository) {
        this.repository = repository;
    }

    public List<InstrumentRun> getAllRuns() {
        return repository.findAll();
    }

    public List<InstrumentRun> getRunsByTest(Long testId) {
        return repository.findByTest_TestId(testId);
    }

    public List<InstrumentRun> getRunsByInstrument(String instrumentName) {
        return repository.findByInstrumentName(instrumentName);
    }

    public InstrumentRun createRun(InstrumentRun run) {
        return repository.save(run);
    }

    public InstrumentRun updateRunStatus(Long id, String status) {
        InstrumentRun run = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Run not found with id: " + id));
        run.setStatus(Enum.valueOf(RunStatus.class, status));
        return repository.save(run);
    }
}
