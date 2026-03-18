package com.labconnect.service.qualityControl;


import com.labconnect.Enum.QCStatus;
import com.labconnect.models.qualityControl.QualityControl;
import com.labconnect.repository.qualityControl.QualityControlRepository;
import com.labconnect.services.qualityControl.QualityControlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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

        QualityControl updatedQC = service.updateQCStatus(1L, "FAIL");

        assertEquals(QCStatus.FAIL, updatedQC.getStatus());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(qc);
    }

    @Test
    void testUpdateQCStatus_NotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.updateQCStatus(99L, "PASS"));
        verify(repository, times(1)).findById(99L);
    }

    @Test
    void testUpdateQCStatus_ArgumentCaptor() {
        when(repository.findById(1L)).thenReturn(Optional.of(qc));
        when(repository.save(any(QualityControl.class))).thenAnswer(i -> i.getArguments()[0]);

        service.updateQCStatus(1L, "FAIL");

        ArgumentCaptor<QualityControl> captor = ArgumentCaptor.forClass(QualityControl.class);
        verify(repository).save(captor.capture());
        assertEquals(QCStatus.FAIL, captor.getValue().getStatus());
    }
}


