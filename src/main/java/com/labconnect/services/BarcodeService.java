package com.labconnect.services;

import com.labconnect.repository.SpecimenRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BarcodeService {

    private final SpecimenRepository specimenRepository;

    public BarcodeService(SpecimenRepository specimenRepository) {
        this.specimenRepository = specimenRepository;
    }

    public String generateUniqueBarcode() {
        String code;
        do {
            code = UUID.randomUUID().toString();
        } while (specimenRepository.existsByBarcodeValue(code));
        return code;
    }
}