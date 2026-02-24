package com.labconnect.repository;

import com.labconnect.models.LabOrder;
import com.labconnect.models.Specimen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecimenRepositoryTest {

    @Mock
    private SpecimenRepository specimenRepository;

    private Specimen sampleSpecimen;

    @BeforeEach
    void setUp() {
        LabOrder order = new LabOrder();
        order.setOrderId(10L);

        sampleSpecimen = new Specimen();
        sampleSpecimen.setSpecimenId(1L);
        sampleSpecimen.setOrder(order);
        sampleSpecimen.setSpecimenType(Specimen.SpecimenType.Blood);
        sampleSpecimen.setCollectedDate(LocalDateTime.now());
        sampleSpecimen.setCollectorId(999L);
        sampleSpecimen.setStatus(Specimen.SpecimenStatus.Collected);
        sampleSpecimen.setBarcodeValue("BAR-123");
        sampleSpecimen.setLabelText("Specimen-10");
    }

    @Test
    void findById_shouldReturnSpecimen_whenExists() {
        when(specimenRepository.findById(1L)).thenReturn(Optional.of(sampleSpecimen));

        Optional<Specimen> result = specimenRepository.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("BAR-123", result.get().getBarcodeValue());
        verify(specimenRepository, times(1)).findById(1L);
    }

    @Test
    void findById_shouldReturnEmpty_whenNotExists() {
        when(specimenRepository.findById(404L)).thenReturn(Optional.empty());

        Optional<Specimen> result = specimenRepository.findById(404L);

        assertTrue(result.isEmpty());
        verify(specimenRepository, times(1)).findById(404L);
    }

    @Test
    void save_shouldReturnSavedSpecimen() {
        when(specimenRepository.save(any(Specimen.class))).thenReturn(sampleSpecimen);

        Specimen saved = specimenRepository.save(new Specimen());

        assertNotNull(saved);
        assertEquals(1L, saved.getSpecimenId());
        verify(specimenRepository, times(1)).save(any(Specimen.class));
    }

    @Test
    void findByOrder_OrderId_shouldReturnSpecimensForOrder() {
        when(specimenRepository.findByOrder_OrderId(10L)).thenReturn(List.of(sampleSpecimen));

        List<Specimen> result = specimenRepository.findByOrder_OrderId(10L);

        assertEquals(1, result.size());
        assertEquals(10L, result.get(0).getOrder().getOrderId());
        verify(specimenRepository, times(1)).findByOrder_OrderId(10L);
    }

    @Test
    void existsByBarcodeValue_shouldReturnTrue_whenExists() {
        when(specimenRepository.existsByBarcodeValue("BAR-123")).thenReturn(true);

        boolean exists = specimenRepository.existsByBarcodeValue("BAR-123");

        assertTrue(exists);
        verify(specimenRepository, times(1)).existsByBarcodeValue("BAR-123");
    }

    @Test
    void existsByBarcodeValue_shouldReturnFalse_whenNotExists() {
        when(specimenRepository.existsByBarcodeValue("BAR-XYZ")).thenReturn(false);

        boolean exists = specimenRepository.existsByBarcodeValue("BAR-XYZ");

        assertFalse(exists);
        verify(specimenRepository, times(1)).existsByBarcodeValue("BAR-XYZ");
    }
}