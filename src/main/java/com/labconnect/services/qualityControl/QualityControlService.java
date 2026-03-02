package com.labconnect.services.qualityControl;

import com.labconnect.models.QualityControl;
import com.labconnect.repository.QualityControlRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QualityControlService {
    private final QualityControlRepository repository;

    public QualityControlService(QualityControlRepository repository) {
        this.repository = repository;
    }

    public List<QualityControl> getAllQCLogs() {
        return repository.findAll();
    }

    public QualityControl createQCLog(QualityControl qc) {
        return repository.save(qc);
    }

    public QualityControl updateQCStatus(Long id, String status) {
        QualityControl qc = repository.findById(id).orElseThrow();
        qc.setStatus(Enum.valueOf(com.labconnect.Enum.QCStatus.class, status));
        return repository.save(qc);
    }
}
