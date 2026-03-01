package com.labconnect.repository;

import com.labconnect.models.LabOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LabOrderRepository extends JpaRepository<LabOrder, Long> {

    // Query by enum field on LabOrder
    List<LabOrder> findByStatus(LabOrder.OrderStatus status);

    // Because patient is a User relation, navigate to the nested PK property userId
    List<LabOrder> findByPatient_UserId(Long userId);

    // Because clinician is a User relation, navigate to the nested PK property userId
    List<LabOrder> findByClinician_UserId(Long userId);

    // OPTIONAL: If you've added a business patientId on User (not the PK),
    // you may also want:
    // List<LabOrder> findByPatient_PatientId(Long patientId);
}