package com.labconnect.service.qualityControl;


import com.labconnect.Enum.QCStatus;
import com.labconnect.models.QualityControl;
import com.labconnect.repository.QualityControlRepository;
import com.labconnect.services.QualityControlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class QualityControlServiceTest {

    @Mock
    private QualityControlRepository repository;

    @InjectMocks
    private QualityControlService service;

    private QualityControl qc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        qc = new QualityControl();
        qc.setQcId(1L);
        qc.setStatus(QCStatus.PASS);
    }

    @Test
    void testCreateQCLog() {
        when(repository.save(qc)).thenReturn(qc);

        QualityControl savedQC = service.createQCLog(qc);

        assertNotNull(savedQC);
        assertEquals(QCStatus.PASS, savedQC.getStatus());
        verify(repository, times(1)).save(qc);
    }

    @Test
    void testUpdateQCStatus() {
        when(repository.findById(1L)).thenReturn(Optional.of(qc));
        when(repository.save(qc)).thenReturn(qc);

        QualityControl updatedQC = service.updateQCStatus(1L, "FAILED");

        assertEquals(QCStatus.FAIL, updatedQC.getStatus());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(qc);
    }
}

