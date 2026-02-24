package com.labconnect.service;

import com.labconnect.repository.SpecimenRepository;
import com.labconnect.services.BarcodeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class BarcodeServiceTest {

    @Mock
    SpecimenRepository specimenRepository;

    BarcodeService barcodeService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        barcodeService = new BarcodeService(specimenRepository);
    }

    @Test
    public void generateUniqueBarcode_callsExistsAndReturnsValue() {
        // No collision: first generated UUID is unique
        when(specimenRepository.existsByBarcodeValue(anyString())).thenReturn(false);

        String code = barcodeService.generateUniqueBarcode();

        assertNotNull(code);
        assertFalse(code.isBlank());
        verify(specimenRepository, atLeastOnce()).existsByBarcodeValue(anyString());
    }

    @Test
    public void generateUniqueBarcode_retriesUntilUnique() {
        // Simulate one collision, then unique
        when(specimenRepository.existsByBarcodeValue(anyString()))
                .thenReturn(true)   // 1st attempt collides
                .thenReturn(false); // 2nd attempt is unique

        String code = barcodeService.generateUniqueBarcode();

        assertNotNull(code);
        assertFalse(code.isEmpty());
        verify(specimenRepository, times(2)).existsByBarcodeValue(anyString());
    }
}
