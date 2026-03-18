package com.labconnect.repository.orderSpecimen;

import com.labconnect.models.Identity.User;
import com.labconnect.models.orderSpecimen.LabOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LabOrderRepository extends JpaRepository<LabOrder, Long> {

    List<LabOrder> findByStatus(LabOrder.OrderStatus status);

    List<LabOrder> findByPatientId(Long patientId);

    // OPTION A (recommended): derived query using nested property path
    List<LabOrder> findByClinicianId_UserId(Long userId);

    // OPTION B (alternative): keep your old name with explicit JPQL
    // @Query("select o from LabOrder o where o.clinicianId.userId = :userId")
    // List<LabOrder> findByClinicianUserId(@Param("userId") Long userId);

    // If you need to find by the User entity itself
    List<LabOrder> findByClinicianId(User clinician);
}