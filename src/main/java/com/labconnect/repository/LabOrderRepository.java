package com.labconnect.repository;


import com.labconnect.models.LabOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LabOrderRepository extends JpaRepository<LabOrder, Long> {
    List<LabOrder> findByStatus(LabOrder.OrderStatus status);
    List<LabOrder> findByPatientId(Long patientId);
    List<LabOrder> findByClinicianId(Long clinicianId);
}