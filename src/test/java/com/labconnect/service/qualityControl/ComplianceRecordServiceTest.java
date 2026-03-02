package com.labconnect.service.qualityControl;

import com.labconnect.DTORequest.qualityControl.ComplianceRecordRequestDTO;
import com.labconnect.DTOResponse.qualityControl.ComplianceRecordResponseDTO;
import com.labconnect.mapper.qualityControl.ComplianceRecordMapper;
import com.labconnect.models.qualityControl.ComplianceRecord;
import com.labconnect.repository.qualityControl.ComplianceRecordRepository;
import com.labconnect.services.qualityControl.ComplianceRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ComplianceRecordServiceTest {

    @Mock
    private ComplianceRecordRepository repository;

    @Mock
    private ComplianceRecordMapper mapper;

    @InjectMocks
    private ComplianceRecordService service;

    private ComplianceRecord entity;
    private ComplianceRecordResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        entity = new ComplianceRecord();
        entity.setRecordId(1L);
        entity.setTestId(123L);
        entity.setAuditType("Calibration");
        entity.setNotes("Instrument recalibrated");
        entity.setLoggedDate(LocalDateTime.now());

        responseDTO = new ComplianceRecordResponseDTO();
        responseDTO.setRecordId(1L);
        responseDTO.setTestId(123L);
        responseDTO.setAuditType("Calibration");
        responseDTO.setNotes("Instrument recalibrated");
        responseDTO.setLoggedDate(entity.getLoggedDate());
    }

    @Test
    void testCreateComplianceRecord() {
        ComplianceRecordRequestDTO request = new ComplianceRecordRequestDTO();
        request.setTestId(123L);
        request.setAuditType("Calibration");
        request.setNotes("Instrument recalibrated");

        when(mapper.toEntity(request)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponseDto(entity)).thenReturn(responseDTO);

        ComplianceRecordResponseDTO result = service.createComplianceRecord(request);

        assertNotNull(result);
        assertEquals("Calibration", result.getAuditType());
        verify(repository, times(1)).save(entity);
    }

    @Test
    void testGetAllRecords() {
        when(repository.findAll()).thenReturn(Collections.singletonList(entity));
        when(mapper.toResponseDto(entity)).thenReturn(responseDTO);

        List<ComplianceRecordResponseDTO> results = service.getAllRecords();

        assertEquals(1, results.size());
        assertEquals(123L, results.get(0).getTestId());
        verify(repository, times(1)).findAll();
    }
}
