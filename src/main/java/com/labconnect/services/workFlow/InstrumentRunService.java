package com.labconnect.services.workFlow;

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

    public InstrumentRun createRun(InstrumentRun run) {
        return repository.save(run);
    }

    public InstrumentRun updateRunStatus(Long id, String status) {
        InstrumentRun run = repository.findById(id).orElseThrow();
        run.setStatus(Enum.valueOf(com.labconnect.Enum.RunStatus.class, status));
        return repository.save(run);
    }
}
