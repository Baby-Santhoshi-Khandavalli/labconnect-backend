package com.labconnect.repository;

import com.labconnect.models.Specimen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpecimenRepository extends JpaRepository<Specimen, Long> {
    List<Specimen> findByOrder_OrderId(Long orderId);
    boolean existsByBarcodeValue(String barcodeValue);
}