package com.labconnect.repository.orderSpecimen;

import com.labconnect.models.orderSpecimen.Specimen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpecimenRepository extends JpaRepository<Specimen, Long> {
    List<Specimen> findByOrder_OrderId(Long orderId);
    boolean existsByBarcodeValue(String barcodeValue);
}