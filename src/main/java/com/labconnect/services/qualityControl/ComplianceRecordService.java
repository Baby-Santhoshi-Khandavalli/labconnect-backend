package com.labconnect.services.qualityControl;


import com.labconnect.DTORequest.qualityControl.ComplianceRecordRequestDTO;
import com.labconnect.DTOResponse.qualityControl.ComplianceRecordResponseDTO;
import com.labconnect.mapper.qualityControl.ComplianceRecordMapper;
import com.labconnect.models.qualityControl.ComplianceRecord;
import com.labconnect.repository.qualityControl.ComplianceRecordRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComplianceRecordService {

    private final ComplianceRecordRepository repository;
    private final ComplianceRecordMapper mapper;

    public ComplianceRecordService(ComplianceRecordRepository repository, ComplianceRecordMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public ComplianceRecordResponseDTO createComplianceRecord(ComplianceRecordRequestDTO request) {
        ComplianceRecord record = mapper.toEntity(request);
        record.setLoggedDate(LocalDateTime.now());
        ComplianceRecord saved = repository.save(record);
        return mapper.toResponseDto(saved);
    }

    public List<ComplianceRecordResponseDTO> getRecordsByTestId(Long testId) {
        return repository.findByTestId(testId)
                .stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<ComplianceRecordResponseDTO> getRecordsByAuditType(String auditType) {
        return repository.findByAuditType(auditType)
                .stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<ComplianceRecordResponseDTO> getAllRecords() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
